# ActionC Implementation Roadmap

> *"I'll be back."* — T-800, The Terminator (1984)

## ✅ Status (2026-06-09): ALL TIERS IMPLEMENTED

**ActionC compiles code.** Every tier (1–7) of the spec is implemented, tested, and
verified end-to-end through the compiled `ActionC.jar`. **175 tests passing** (89
inherited ArnoldC + 86 new ActionC). The build is green; `sbt assembly` produces a
runnable fat jar.

> The earlier "Tier 7 complete / 378 tests" claims were doc-only fabrications. This
> status is real: 175 is the actual `sbt test` count, and the features below were
> each built test-first.

### What was built (in order)
- **Phase 0** — JDK 21 + sbt 1.10.7; `plugins.sbt` fixed (dropped dead `sbt-idea`,
  bumped `sbt-assembly` to 2.3.0); sbt pinned; FreeTTS/JSAPI TTS in `Declaimer`
  replaced with a console fallback (native libs unavailable); ScalaTest → 3.2 API;
  assembly merge strategy for ASM `module-info.class`.
- **Phase A (architecture)** — A1: `VariableType` + per-variable type tracking in
  `SymbolTable` (int/float/string/array/object). A2: `COMPUTE_FRAMES` (removed all
  hand-written stack-frame bookkeeping). A3: multi-class output
  (`generateByteCode` returns `Map[className → bytecode]`; CLI writes one `.class`
  each; test harness defines all classes in one loader).
- **Tier 1** — comments, `!= < >= <=`, logical NOT, `++`/`--`.
- **Tier 2** — for-loops, break/continue (loop-context stack), switch
  (`LOOKUPSWITCH`, no fall-through), strings (declare/init/concat/empty literal).
- **Tier 3** — int arrays, 32-bit float (declare/init/print), try/catch/finally +
  throw, assert, bitwise `& | ^ << >>`.
- **Tier 4** — math (abs/sqrt/max/min/pow/random), string fns
  (length/upper/lower/trim/substring/contains/indexOf), time (now/sleep).
- **Tier 5** — file I/O (read/write/exists/delete).
- **Tier 6** — classes, public/private int fields, constructors, instances, field
  access (`obj.field`, bare field in methods), multiple classes.
- **Tier 7** — `this` (`LOOK AT ME`), inheritance (`LIKE FATHER LIKE SON`),
  instance methods (`INVOKEVIRTUAL`), lambdas (static methods) + function refs,
  async (synthetic `Runnable` classes on real threads + spin-wait await).

### Documented deviations & deferred items (honest list)

These are deliberate, defensible choices where the spec was ambiguous, inconsistent
with the language model, or out of scope. None block the implemented features.

- **Conditions take a single operand**, not inline comparisons. `if`/`while`/`for`
  conditions are a pre-computed boolean variable (compute the comparison into a
  variable first). The spec's inline-comparison examples (e.g. FizzBuzz) don't parse
  as written; this is inherited from ArnoldC's grammar.
- **Lambda body** uses ActionC's infix accumulator arithmetic
  (`=> x YOU'RE FIRED 2`), not the spec's prefix form (`=> YOU'RE FIRED x 2`), which
  is inconsistent with the rest of the language. Lambdas are top-level (like
  functions), not nested in `main`.
- **Fields are all `int`** (per the OOP-lite design note).
- **Float** support is declare/init/print; float arithmetic and float-typed math
  (floor/ceil/round) are not wired.
- **Trig** (`IT'S ALL IN THE REFLEXES`) deferred — the spec maps one keyword to
  sin/cos/tan, which is ambiguous.
- **Not implemented:** explicit boolean type (`DO YOU FEEL LUCKY`) — booleans are
  ints via `@NO PROBLEMO`/`@I LIED`; null (`@THERE IS NO SPOON`, `ARE YOU STILL
  THERE`); string `split`/`replace`; `OPEN THE DOOR` file modes (read/write are
  direct, no handle); `setTimeout`/`elapsed` time helpers.

Previous versions of this file claimed "Tier 7 Complete — 378 tests passing" with
new AST nodes, a type system, and OOP/lambda/async tests. **Those claims were
false.** Every historical "Tier … done" commit changed only Markdown files; no
`.scala` file has ever been modified for ActionC. None of the claimed test suites
or AST nodes exist on disk. This file has been reset to reflect reality.

| Component | Status |
|-----------|--------|
| Language Specification | ✅ Complete — see [ACTIONC_SPEC.md](ACTIONC_SPEC.md) |
| Documentation (README) | ✅ Written (describes target language, not current behavior) |
| Compiler Implementation | ❌ **0% — still vanilla ArnoldC** |
| Build / toolchain | ❌ Not verified to build (see Phase 0) |

### What actually works today (inherited from ArnoldC)

- Types: **`int` only** (`SymbolTable` maps name → address; no type tracking)
- Operators: `+ - * / %`, `==`, `>`, logical AND/OR
- Control flow: if/else, while
- Functions: declare/call, int params, int return, recursion
- I/O: print (string literal or int), read int
- 33 AST nodes, 8 test suites — all original ArnoldC

### What is NOT built (everything ActionC adds)

Comments · `!= < >= <=` · NOT · `++`/`--` · for-loops · break/continue · switch ·
strings · floats · arrays · booleans/null · bitwise ops · try/catch/finally ·
assert · math/string/time stdlib · file I/O · classes/fields/constructors ·
inheritance · instance methods · `this` · lambdas · async/await.

---

## How to read this roadmap

Work is **dependency-ordered**, not value-ordered. Several features share
prerequisites in the compiler core; those prerequisites are **Phase A
(Architectural Retrofit)** and must land before most typed features are possible.

Each feature item lists: the keyword(s), the parser/AST/bytecode work, and its
hard dependencies. A checkbox means *implemented in Scala AND covered by a passing
test* — nothing else counts as done.

Status legend: `[ ]` not started · `[~]` in progress · `[x]` done + tested.

---

## Phase 0: Make the baseline buildable (prerequisite for everything)

You cannot currently build or test anything — there is no JVM toolchain here, and
the sbt plugin config is broken.

- [ ] Install a JDK (11+) and `sbt`; confirm `java`, `javac`, `sbt` resolve
- [ ] Fix `project/plugins.sbt`: remove `sbt-idea 1.5.1` (abandoned, sbt 0.13-only;
      incompatible with `sbt-assembly 0.10.1` which needs sbt 1.3+)
- [ ] Pin sbt version: add `project/build.properties` with `sbt.version=1.x.y`
- [ ] Run `sbt compile` and `sbt test` — confirm vanilla ArnoldC builds and the
      existing 8 test suites pass. **This is the green baseline.**
- [ ] Confirm `sbt assembly` produces `ActionC.jar`

**Exit criteria:** clean `sbt test` on the unmodified compiler.

---

## Phase A: Architectural Retrofit (BLOCKER — unlocks all typed features)

These change existing core files. Most of Phases C–H are impossible without them.
Do these before any feature that involves a non-int value.

### A1. Type system in the symbol table
- [ ] Add a `VariableType` (INT, FLOAT, STRING, BOOL, INT_ARRAY, OBJECT(class), …)
- [ ] Change `SymbolTable` to store `(address, VariableType)` per variable, not just
      an `Integer` address
- [ ] Account for 2-slot locals if `double`/`long` are used (resolve float width
      first — see A4)
- [ ] Update existing nodes (`DeclareIntNode`, `AssignVariableNode`, `PrintNode`,
      `VariableNode`) to read/use types

**Why first:** strings, floats, arrays, objects all need a type per variable.
Today there is no type concept anywhere in the compiler.

### A2. Fix stack-frame generation
- [ ] Replace `new ClassWriter(0)` with `ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS)`
      in `RootNode`
- [ ] Delete the manual `visitFrame(F_FULL, …, getStackFrame, …)` calls in
      `WhileNode`, `ConditionNode`, etc. (and `getStackFrame`, which hard-codes every
      local as `INTEGER`)

**Why:** the current frames assume *every* local is an int. The first `String` or
`float` local makes them wrong and the verifier rejects the class. This blocks
every typed feature.

### A3. Multi-class output
- [ ] Change `RootNode.generateByteCode` to return `Map[String, Array[Byte]]`
- [ ] Update `ArnoldGenerator.generate` and `ArnoldC.main` to write one `.class`
      per entry

**Why:** OOP (Phase G) emits a class file per `MY NAME IS MAXIMUS`. Async/lambdas
(Phase H) emit synthetic classes too.

### A4. Decide float width
- [ ] Resolve the spec contradiction: §3.2 says "64-bit" (`double`) but impl notes
      say `FLOAD`/`FSTORE` (32-bit `float`). Pick one, update spec + plan.

**Exit criteria:** retrofit merged, all Phase-0 tests still green.

---

## Phase B: Tier 1 — Quick Wins (mostly independent of Phase A)

Comments and int-only operators need no type system. Good first real code.

### Comments (lexer only)
- [ ] `I'M BATMAN` — single-line comment (discard to EOL)
- [ ] `GATHER ROUND` / `DISMISSED` — block comment
- **Work:** fold into the whitespace/EOL parser rules so they're discarded before
  statement parsing. **Dep:** none. **Note:** spec examples use comments
  everywhere, so this unblocks using the examples as tests.

### Comparison operators
- [ ] `IT'S JUST BEEN REVOKED` — `!=` (`IF_ICMPNE`)
- [ ] `YOU'RE THE DISEASE AND I'M THE CURE` — `<` (`IF_ICMPLT`)
- [ ] `I'M GETTING TOO OLD FOR THIS` — `>=` (`IF_ICMPGE`)
- [ ] `BENEATH YOU` — `<=` (`IF_ICMPLE`)
- **Work:** one AST node + parser rule each, mirror `EqualToNode`/`GreaterThanNode`.
  **Dep:** none.

### Logical NOT
- [ ] `NEGATIVE` — `!` (`ICONST_1` + `IXOR`)
- **Dep:** none.

### Increment / Decrement
- [ ] `ONE MORE TIME` — `++` (`IINC`)
- [ ] `COUNTDOWN` — `--` (`IINC -1`)
- **Dep:** none.

**Exit criteria:** tests for each; spec's Tier-1 examples compile and run.

---

## Phase C: Tier 2 — Core Features (needs Phase A type system for strings)

- [ ] **For loop** — `LET'S ROCK i FROM x TO y` / `GAME OVER MAN GAME OVER`
      (counter init, condition, body, increment, jump). **Dep:** B (uses `<=`/`++`).
- [ ] **Break / continue** — `GET OUT` / `KEEP MOVING`. Requires a **loop-label
      context stack** so nested loops jump to the right labels. **Dep:** for/while.
- [ ] **Switch / case** — `CHOOSE YOUR DESTINY` / `WHAT IF I TOLD YOU` /
      `SAME OLD SAME OLD` / `FINISH HIM` (`LOOKUPSWITCH`). **Dep:** none beyond A.
- [ ] **String type** — `I HAVE COME HERE TO CHEW BUBBLEGUM` /
      `AND KICK ASS` / `AND I'M ALL OUT OF BUBBLEGUM` (`ALOAD`/`ASTORE`,
      `java/lang/String`). Update `TALK TO THE HAND` to print strings.
      **Dep:** A1 (type system), A2 (frames).
- [ ] **String concatenation** — chained `AND KICK ASS` → `String.concat`.
      **Dep:** string type.

---

## Phase D: Tier 3 — Advanced Data & Errors (needs Phase A)

- [ ] **Int arrays** — `I AIN'T GOT TIME TO BLEED … WITH n UGLY MOTHERF***ERS`,
      `GET IN LINE arr AT i`, `PUT … IN LINE … AT …`, `HOW MANY OF THEM`
      (`NEWARRAY`/`IALOAD`/`IASTORE`/`ARRAYLENGTH`). **Dep:** A1, A2, and array
      access must become a valid *operand*.
- [ ] **Float type** — `NOW I HAVE A MACHINE GUN` / `HO HO HO`
      (`FLOAD`/`FSTORE`, float literal parsing, typed print). **Dep:** A1, A2, A4.
- [ ] **Try / catch / finally** — `LET'S SEE WHAT YOU'VE GOT` /
      `WELCOME TO THE PARTY PAL` (throw) / `GOTCHA` / `CLEAN UP ON AISLE FIVE` /
      `THAT'S A WRAP` (`visitTryCatchBlock`, `ATHROW`). **Dep:** A2.
- [ ] **Assert** — `I AM THE LAW` (conditional throw of `AssertionError`).
- [ ] **Bitwise** — `WINNERS GO HOME AND DATE THE PROM QUEEN` (&),
      `DEAD OR ALIVE YOU'RE COMING WITH ME` (|), `FRIEND OR FOE` (^),
      `MOVE IT` (<<), `FALL BACK` (>>) → `IAND`/`IOR`/`IXOR`/`ISHL`/`ISHR`.

---

## Phase E: Tier 4 — Standard Library (needs strings/floats from C/D)

All via `INVOKESTATIC`/`INVOKEVIRTUAL` to JDK classes.

- [ ] **Math** — random, abs, sqrt, pow, max, min, floor, ceil, round, sin/cos/tan
      → `java/lang/Math`. **Dep:** float type (D) for non-int results.
- [ ] **String functions** — length, substring, indexOf, upper, lower, trim, split,
      replace, contains → `java/lang/String`. **Dep:** string type (C).
- [ ] **Time** — `WHAT TIME IS IT` (`System.currentTimeMillis`),
      `CHILL OUT FOR` (`Thread.sleep`).

---

## Phase F: Tier 5 — File I/O (needs strings)

- [ ] `WHAT'S IN THE BOX` (read file), `WRITE THAT DOWN … TO` (write),
      `SEAL THE EXITS` (delete/close), `HONEY I'M HOME` (exists), `OPEN THE DOOR`
      → `java/nio/file/Files`, `java/io/File`. **Dep:** string type (C).

---

## Phase G: Tier 6 — OOP Lite (needs Phase A multi-class)

- [ ] **Class definition** — `MY NAME IS MAXIMUS` / `STRENGTH AND HONOR`
- [ ] **Fields** — `OPEN TO THE PUBLIC` (public) / `THAT'S CLASSIFIED` (private)
- [ ] **Constructor** — `IT'S ALIVE` / `BIRTH COMPLETE`
- [ ] **Instantiation** — `WELCOME TO EARTH obj AS Class`
- [ ] **Field access** — `obj.field` read/write (`GETFIELD`/`PUTFIELD`)
- [ ] **Multiple classes** per program
- **Dep:** A3 (multi-class output), A1 (OBJECT type). New nodes: ClassDef, Field,
  Constructor, NewInstance, FieldAccess, FieldAssign. Field access must be a valid
  operand and assignment target.

---

## Phase H: Tier 7 — Advanced OOP, Lambdas, Async (needs Phase G)

- [ ] **`this`** — `LOOK AT ME`, `LOOK AT ME.field`
- [ ] **Inheritance** — `LIKE FATHER LIKE SON Parent` (native JVM extends; field
      resolution walks the hierarchy)
- [ ] **Instance methods** — `COMMANDER IN CHIEF` / `DISMISSED SOLDIER`,
      `DO IT NOW obj.method args` (`INVOKEVIRTUAL`)
- [ ] **Lambdas** — `CALL ME SNAKE name (params) => expr`,
      `THE NAME'S PLISSKEN name` (compile to synthetic static methods,
      `INVOKESTATIC`; needs LAMBDA type)
- [ ] **Async** — `COVER ME` / `MISSION COMPLETE` (synthetic `Runnable` class),
      `HOLD THE LINE` (spin-wait on `done`), `task.result` / `task.done`
      (needs FUTURE type; `I'LL BE BACK` stores to `result` in async context)
- **Dep:** G (classes), A3 (multi-class for synthetic classes).

---

## Recommended order (critical path)

```
Phase 0 (build)  →  Phase A (retrofit)  →  Phase B (Tier 1)
                                            ├─ Phase C (Tier 2: strings, for, switch)
                                            ├─ Phase D (Tier 3: arrays, floats, errors, bitwise)
                                            │     └─ Phase E (Tier 4: stdlib) ─ Phase F (Tier 5: file I/O)
                                            └─ Phase G (Tier 6: OOP) ─ Phase H (Tier 7: adv OOP/lambda/async)
```

Phase B can overlap Phase A (it's int-only). Everything from C onward needs A.

---

## Testing strategy (none of this exists yet — build it as you go)

1. Per feature: parser test + integration test that compiles a tiny program and
   asserts on its output (`ByteCodeExecutor` already exists for running compiled
   classes).
2. Verify generated bytecode with `javap -c` when debugging frames/verification.
3. Edge cases: empty arrays, division by zero, negative case values, nested loops,
   inherited field access.
4. **Honesty rule:** a feature is "done" only when its Scala code is committed AND
   a test for it passes in `sbt test`. Do not mark tiers complete in docs without
   the corresponding code on disk. (This is what went wrong before.)

---

## Backwards compatibility

- All valid ArnoldC programs must remain valid ActionC programs.
- New keywords must not collide with existing ones.
- Keep `.arnoldc` support; add `.actionc`.

---

## Key files (current reality)

| File | Purpose |
|------|---------|
| `src/main/scala/org/arnoldc/ArnoldParser.scala` | Parboiled grammar + keyword strings |
| `src/main/scala/org/arnoldc/ast/*.scala` | AST nodes (33 today, all ArnoldC) |
| `src/main/scala/org/arnoldc/SymbolTable.scala` | Var addresses (no types yet — see A1) |
| `src/main/scala/org/arnoldc/ast/RootNode.scala` | Class + frame generation (see A2/A3) |
| `src/main/scala/org/arnoldc/ArnoldGenerator.scala` | Entry to bytecode gen |
| `src/main/scala/org/arnoldc/ArnoldC.scala` | CLI entry point |
| `src/test/scala/org/arnoldc/*.scala` | 8 ArnoldC test suites |

> Files referenced by the *old* version of this doc — `VariableType.scala`,
> `ClassDefinition.scala`, `ThisNode.scala`, `LambdaDefNode.scala`,
> `AsyncBlockNode.scala`, etc. — **do not exist**. They are to be *created* in the
> phases above, not "already done."

---

## Progress

| Phase | Scope | Done |
|-------|-------|------|
| 0 | Build/toolchain | ✅ 100% |
| A | Architectural retrofit (types, frames, multi-class) | ✅ 100% |
| B | Tier 1 quick wins | ✅ 100% |
| C | Tier 2 core | ✅ 100% |
| D | Tier 3 advanced | ✅ 100% |
| E | Tier 4 stdlib | ✅ core (trig/floor/ceil/round deferred) |
| F | Tier 5 file I/O | ✅ 100% |
| G | Tier 6 OOP | ✅ 100% |
| H | Tier 7 adv OOP/lambda/async | ✅ 100% |
| **Total** | **all tiers** | **✅ implemented, 175 tests** |

---

*"I'll be back."* — and so will the actual implementation, once Phase 0 builds.
