# CLAUDE.md

Guidance for working in this repository — and for writing ActionC programs anywhere.

ActionC is an esoteric language (a superset of [ArnoldC](https://github.com/lhartikk/ArnoldC))
whose keywords are action-movie one-liners. The compiler is written in Scala, parses
with parboiled, emits JVM bytecode with ASM, and produces runnable `.class` files.

This file has two parts:

- **Part 1 — Working on the compiler.** For an agent hacking the Scala/ASM internals
  of *this* repo.
- **Part 2 — Writing ActionC programs.** Portable; self-contained enough to copy into
  any project that compiles `.actionc` source.

---

## Toolchain

The build needs **JDK 21** and **sbt**. On this machine they are not on the default
`PATH`; export them first:

```bash
export JAVA_HOME=/home/pwood/tools/jdk-21.0.11+10
export PATH="$JAVA_HOME/bin:/home/pwood/tools/sbt/bin:$PATH"
```

(On other machines, any JDK 21 + sbt 1.x works. `build.sbt` pins Scala 2.12.18.)

## Commands

```bash
sbt test                                          # run the suite — 175 tests, ~2s
sbt assembly                                      # build target/scala-2.12/ActionC.jar
java -jar target/scala-2.12/ActionC.jar prog.actionc   # compile prog.actionc -> prog.class
java -jar target/scala-2.12/ActionC.jar -run prog.actionc   # compile AND run
java prog                                         # run a class compiled separately
```

`sbt test` is fast (compilation dominates). Tests run **serially**
(`Test / parallelExecution := false`) because file-I/O and async suites touch shared
state — keep it that way.

---

# Part 1 — Working on the compiler

## Status (ground truth, verified 2026-06-09)

All language tiers are **implemented and tested**: comments, the full
comparison/bitwise/logical operator set, for/break/continue/switch, strings, floats,
int arrays, error handling, the math/string/time/file stdlib, OOP with inheritance and
instance methods, lambdas + function references, and async. Float **arithmetic**
(`+ − × ÷ %` with int→float promotion), `floor`/`ceil`/`round`, and numeric↔string
conversions landed in the Tier 1 numerics pass. **`sbt test` →
187 passing, 0 failing, 31 suites.** `sbt assembly` produces a runnable jar.

The implementation roadmap (`TODO.md`) has been removed now that all tiers are done;
treat the verified test run as ground truth, and this file as the live guide.

## Architecture: the pipeline

Source flows through four stages, all under package `org.arnoldc` (the package name
was never renamed from ArnoldC — keep it):

```
.actionc text
   │
   ▼  ArnoldParser.scala         parboiled PEG grammar; keyword strings are vals
AST (org.arnoldc/ast/*.scala)    55 case-class nodes; each knows how to emit itself
   │
   ▼  node.generate(mv, symbolTable)   walks the tree, calling ASM MethodVisitor
JVM bytecode                     RootNode.generateByteCode -> Map[className -> bytes]
   │
   ▼  ArnoldC.main                writes one .class per entry
prog.class (+ one .class per ActionC class / lambda / async block)
```

Core files (`src/main/scala/org/arnoldc/`):

| File | Role |
|------|------|
| `ArnoldC.scala` | CLI entry. Reads source, drives generation, writes `.class` files, handles `-run`/`-declaim`. |
| `ArnoldParser.scala` | The grammar (~535 lines). Keyword strings as `val`s up top; `def Xxx: Rule…` rules below. `Statement` and `Root` are the top-level rules. |
| `ArnoldGenerator.scala` | Thin glue: parse → `rootNode.generateByteCode(filename)`. |
| `SymbolTable.scala` | Per-method variable slots + per-variable `VariableType` (int/float/string/array/object). |
| `VariableType.scala` | The type tags used during codegen. |
| `Executor.scala` | `-run`: loads and invokes the compiled main class. |
| `Declaimer.scala` | `-declaim`: speaks the program. Uses a **console fallback** (native TTS libs unavailable) — don't reintroduce FreeTTS/JSAPI. |
| `ast/*.scala` | One file per construct (`OopNodes`, `LambdaNodes`, `AsyncNodes`, `MathNodes`, `FileNodes`, `StringFunctionNodes`, …). Each node is a `case class` with a `generate` method. |

Bytecode uses ASM with **`COMPUTE_FRAMES`** — never hand-write stack-map frames or
`visitMaxs` bookkeeping; let ASM compute them. Multi-class constructs (classes,
lambdas-as-static-methods, async-blocks-as-`Runnable`s) are why `generateByteCode`
returns a *map* of classes, not a single byte array.

## The recipe: adding a keyword / language feature

This is the repeated task in this repo. Work **test-first** (TDD), mirroring how every
tier was built:

1. **Write the failing test.** Add a suite (or case) under `src/test/scala/org/arnoldc/`
   extending `ArnoldGeneratorTest`. Build a source string and assert on `getOutput(code)`
   — tests compile bytecode in-memory and capture stdout. See `CommentTest.scala` for
   the pattern.
2. **Add the keyword string** as a `val` near the top of `ArnoldParser.scala`
   (e.g. `val Break = "GET OUT"`). Keywords are exact phrases; mind apostrophes/commas.
3. **Wire the grammar rule.** Add a `def …Statement` (or expression) rule and reference
   it from the `Statement` rule (line ~200) — or from `Operand`/`Expression` for a value
   form. parboiled combinators: `~` sequence, `|` choice, `~>` capture-string,
   `~~>` build-AST-node. Conclude statement rules with `EOL`.
4. **Create the AST node** in `ast/` (or extend an existing grouped file). It's a
   `case class … extends StatementNode` / `ExpressionNode` with
   `def generate(mv: MethodVisitor, symbolTable: SymbolTable)`. Emit instructions via
   `mv.visit*`. `NotEqualNode.scala` is a clean, small reference.
5. **Run `sbt test`** until green. Add the keyword to `README.md`'s tables and, if it
   changes program-author behavior, to Part 2 below.

## Conventions & gotchas (codebase)

- Package stays `org.arnoldc`; assembly `mainClass` is `org.arnoldc.ArnoldC`.
- The fat-jar merge strategy **discards `module-info.class`** (the ASM jars each ship
  one) — see `build.sbt`. Don't remove that or `sbt assembly` breaks.
- Conditions (`if`/`while`/`for`) consume a **single pre-computed operand**, not an
  inline comparison — this is a grammar constraint inherited from ArnoldC, not a bug.
  See Part 2; don't "fix" it without changing the grammar deliberately.
- Fields are **int-only** by design (OOP-lite). Lambdas compile to **static methods**;
  async blocks to synthetic **`Runnable`** classes on real threads with spin-wait await.
- Keep `Test / parallelExecution := false`.

## Deliberately deferred (don't assume these exist)

Trig (`IT'S ALL IN THE REFLEXES` — ambiguous one-keyword→sin/cos/tan mapping), an
explicit boolean type, null/`@THERE IS NO SPOON`, string `split`/`replace`, file
modes/handles, and `setTimeout`/`elapsed`. The author-facing subset is restated in
Part 2 below.

Float arithmetic and `floor`/`ceil`/`round` are **no longer deferred** — see the Tier 1
numerics work. `floor`/`ceil`/`round` take a float and return an int (they double as
float→int truncation); `SPELL IT OUT` stringifies an int or float; `DO THE MATH` parses
a string to an int. Mixed int/float arithmetic promotes the int side via `I2F`
(`TypeInference.scala`).

---

# Part 2 — Writing ActionC programs

Portable reference for authoring `.actionc` source. Full keyword tables live in
[`README.md`](README.md); [`ACTIONC_SPEC.md`](ACTIONC_SPEC.md) has the grammar. This
section is the working subset plus the gotchas that separate programs that *run* from
spec examples that don't parse.

## Skeleton, compile, run

```actionc
IT'S SHOWTIME
    TALK TO THE HAND "HELLO BOYS I'M BACK"
YOU HAVE BEEN TERMINATED
```

```bash
java -jar ActionC.jar -run hello.actionc      # compile + run in one step
# or: java -jar ActionC.jar hello.actionc  &&  java hello
```

Both `.actionc` and `.arnoldc` extensions are accepted; all ArnoldC programs run
unchanged.

## Essential idioms

**Declare & init an int** (declaration and initial value are two lines):

```actionc
HEY CHRISTMAS TREE x
YOU SET US UP 21
```

**Reassign** a variable — an assignment *block*, not a single statement. Open with
`GET TO THE CHOPPER`, seed with `HERE IS MY INVITATION`, apply infix operators, close
with `ENOUGH TALK`:

```actionc
GET TO THE CHOPPER x
HERE IS MY INVITATION x
YOU'RE FIRED 2            I'M BATMAN  x = x * 2
ENOUGH TALK
```

`GET UP` = +, `GET DOWN` = −, `YOU'RE FIRED` = ×, `HE HAD TO SPLIT` = ÷,
`I LET HIM GO` = %.

**Print:** `TALK TO THE HAND <int-var | "string literal">`.

**Comments:** `I'M BATMAN …` (rest of line); `GATHER ROUND` / `DISMISSED` (block).

## Conditions need a PRE-COMPUTED boolean — the #1 gotcha

`if`/`while`/`for` take **one already-computed operand**, never an inline comparison.
Compute the comparison into a variable first, then branch on it. The spec's
inline-comparison examples (the FizzBuzz one) **do not parse as written.**

```actionc
I'M BATMAN  WRONG — does not compile:
I'M BATMAN  BECAUSE I'M GOING TO SAY PLEASE x YOU ARE NOT YOU YOU ARE ME 5 ...

I'M BATMAN  RIGHT — compute the boolean, then branch on it:
HEY CHRISTMAS TREE isFive
YOU SET US UP x
YOU ARE NOT YOU YOU ARE ME 5      I'M BATMAN  isFive = (x == 5)
BECAUSE I'M GOING TO SAY PLEASE isFive
    TALK TO THE HAND "five!"
YOU HAVE NO RESPECT FOR LOGIC
```

(`BECAUSE I'M GOING TO SAY PLEASE` = if, `BULLSHIT` = else,
`YOU HAVE NO RESPECT FOR LOGIC` = endif.)

Booleans are ints: `@NO PROBLEMO` = true, `@I LIED` = false.

## What's available

Loops (`LET'S ROCK … FROM … TO … / GAME OVER MAN GAME OVER`, `STICK AROUND … CHILL`,
`GET OUT` break, `KEEP MOVING` continue), switch (`CHOOSE YOUR DESTINY … FINISH HIM`,
no fall-through), strings (declare/concat/length/upper/lower/trim/substring/contains/
indexOf), int arrays, try/catch/finally + throw + assert, the math/string/time/file
stdlib, classes with constructors and inheritance, instance methods + `this`
(`LOOK AT ME`), lambdas + function refs, and async (`COVER ME … MISSION COMPLETE`,
`HOLD THE LINE` await). See `README.md` for the full examples per feature.

## Author-facing gotchas

- **Conditions take a pre-computed boolean** (above) — the big one.
- **Object fields are int-only.** No string/float/object fields.
- **Lambdas** are top-level (declared like functions, not nested in `IT'S SHOWTIME`),
  and their body uses **infix** arithmetic: `CALL ME SNAKE double (x) => x YOU'RE FIRED 2`.
- **Floats** support full arithmetic now (`GET UP`/`GET DOWN`/`YOU'RE FIRED`/`HE HAD TO
  SPLIT`/`I LET HIM GO` in an assignment block). Mixing an int into a float expression
  promotes the int automatically; a pure-int expression assigned into a float variable
  is coerced. `HIT THE FLOOR` / `THROUGH THE ROOF` / `ROUND THEM UP` take a float and
  return an int (also the way to truncate float→int).
- **Convert numbers and strings:** `SPELL IT OUT <n>` turns an int or float into a
  string (for printing/concatenation); `DO THE MATH <str>` parses a string to an int.
- **Not implemented:** explicit boolean type, null, string `split`/`replace`, trig,
  file modes/handles. If you reach for one and it won't parse, that's why.
