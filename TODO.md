# ActionC Implementation Roadmap

> *"I'll be back."* — T-800, The Terminator (1984)

## Current Status

| Component | Status |
|-----------|--------|
| Language Specification | Complete - see [ACTIONC_SPEC.md](ACTIONC_SPEC.md) |
| Documentation | Complete - see [README.md](README.md) |
| Compiler Implementation | **Tier 5 Complete** — 297 tests passing |

The spec defines 100+ new keywords and features. This roadmap tracks implementation progress.

---

## Tier 1: Quick Wins (High Value, Low Complexity) ✅ COMPLETE

These features require minimal parser/compiler changes but significantly improve usability.

### Comments
- [x] `I'M BATMAN` — Single-line comment (ignore rest of line)
- [x] `GATHER ROUND` / `DISMISSED` — Block comments

**Implementation:** Add comment rules to lexer, discard tokens.

### Missing Comparison Operators
- [x] `IT'S JUST BEEN REVOKED` — Not equal (`!=`)
- [x] `YOU'RE THE DISEASE AND I'M THE CURE` — Less than (`<`)
- [x] `I'M GETTING TOO OLD FOR THIS` — Greater than or equal (`>=`)
- [x] `BENEATH YOU` — Less than or equal (`<=`)

**Implementation:** Add new comparison nodes, generate `IF_ICMPNE`, `IF_ICMPLT`, `IF_ICMPGE`, `IF_ICMPLE` bytecode.

### Logical NOT
- [x] `NEGATIVE` — Logical NOT operator

**Implementation:** Add NOT node, generate `ICONST_1`, `IXOR` bytecode pattern.

### Increment/Decrement
- [x] `ONE MORE TIME` — Increment (`++`)
- [x] `COUNTDOWN` — Decrement (`--`)

**Implementation:** Uses JVM `IINC` instruction for efficient increment/decrement.

---

## Tier 2: Core Features (High Value, Medium Complexity) ✅ COMPLETE

Major language improvements that require new AST nodes and bytecode generation.

### For Loops ✅ COMPLETE
- [x] `LET'S ROCK` — For loop start
- [x] `FROM X TO Y` — Range specification
- [x] `GAME OVER MAN GAME OVER` — For loop end

**Implementation:**
- Added `ForLoopNode` to AST
- Generates counter initialization, condition check, increment, and jump bytecode
- Supports nested loops and variable start/end values

### Break/Continue ✅ COMPLETE
- [x] `GET OUT` — Break from loop
- [x] `KEEP MOVING` — Continue to next iteration

**Implementation:**
- Added `LoopContext` to track current loop labels
- `GET OUT` jumps to loop end, `KEEP MOVING` jumps to continue point
- Works correctly in for loops (continue skips to increment) and while loops

### Switch/Case ✅ COMPLETE
- [x] `CHOOSE YOUR DESTINY` — Switch start
- [x] `WHAT IF I TOLD YOU` — Case clause
- [x] `SAME OLD SAME OLD` — Default clause
- [x] `FINISH HIM` — Switch end

**Implementation:**
- Added `SwitchNode`, `CaseClause` to AST
- Generates `LOOKUPSWITCH` bytecode for efficient case matching
- Supports negative case values and optional default clause

### String Variables ✅ COMPLETE
- [x] `I HAVE COME HERE TO CHEW BUBBLEGUM` — Declare string
- [x] `AND KICK ASS` — Initialize/assign string
- [x] `AND I'M ALL OUT OF BUBBLEGUM` — Empty string literal

**Implementation:**
- Added type system to symbol table (VariableType: INT | STRING)
- Store as `java/lang/String` reference using ALOAD/ASTORE
- Updated `TALK TO THE HAND` to handle string variables

### String Concatenation ✅ COMPLETE
- [x] `AND KICK ASS` — Concatenate strings (multiple `AND KICK ASS` clauses)

**Implementation:** Generates `String.concat()` calls for chained concatenation.

---

## Tier 3: Advanced Features (Medium Value, Higher Complexity) ✅ COMPLETE

Powerful features that require significant compiler changes.

### Arrays ✅ COMPLETE
- [x] `I AIN'T GOT TIME TO BLEED` — Declare array
- [x] `WITH X UGLY MOTHERFUCKERS` — Array size
- [x] `GET IN LINE X AT Y` — Array access
- [x] `PUT X IN LINE Y AT Z` — Array assignment
- [x] `HOW MANY OF THEM` — Array length

**Implementation:**
- Added INT_ARRAY type to symbol table
- Generate `NEWARRAY`, `IALOAD`, `IASTORE`, `ARRAYLENGTH` bytecode

### Float Type ✅ COMPLETE
- [x] `NOW I HAVE A MACHINE GUN` — Declare float
- [x] `HO HO HO` — Initialize float

**Implementation:**
- Added FLOAT type to symbol table
- Generate `FLOAD`, `FSTORE` bytecode
- Updated print to handle float values

### Error Handling ✅ COMPLETE
- [x] `LET'S SEE WHAT YOU'VE GOT` — Try block
- [x] `WELCOME TO THE PARTY PAL` — Throw exception
- [x] `GOTCHA` — Catch block
- [x] `CLEAN UP ON AISLE FIVE` — Finally block (basic support)
- [x] `THAT'S A WRAP` — End try/catch

**Implementation:**
- Generate exception table entries with `visitTryCatchBlock`
- Uses `java/lang/Exception` for catch
- Generates `ATHROW` for throw statements

### Assertions ✅ COMPLETE
- [x] `I AM THE LAW` — Assert condition (with optional message)

**Implementation:** Generate conditional throw of `AssertionError`.

### Bitwise Operators ✅ COMPLETE
- [x] `WINNERS GO HOME AND DATE THE PROM QUEEN` — Bitwise AND
- [x] `DEAD OR ALIVE YOU'RE COMING WITH ME` — Bitwise OR
- [x] `FRIEND OR FOE` — Bitwise XOR
- [x] `MOVE IT` — Left shift
- [x] `FALL BACK` — Right shift

**Implementation:** Generate `IAND`, `IOR`, `IXOR`, `ISHL`, `ISHR` bytecode.

---

## Tier 4: Standard Library (Value-Add Features) ✅ COMPLETE

Built-in functions that extend language capabilities.

### Math Functions ✅ COMPLETE
- [x] `GO AHEAD MAKE MY DAY` — Random number
- [x] `NO MORE HALF MEASURES` — Absolute value
- [x] `GET TO THE ROOT OF` — Square root
- [x] `HIT THE FLOOR` — Floor
- [x] `THROUGH THE ROOF` — Ceiling
- [x] `IT'S ALL IN THE REFLEXES SIN/COS/TAN` — Trig functions

**Implementation:** Generate `INVOKESTATIC` calls to `java/lang/Math` methods.

### String Functions ✅ COMPLETE
- [x] `HOW LONG IS THIS THING` — String length
- [x] `GIVE ME A PIECE OF ... FROM ... TO` — Substring
- [x] `SAY IT LOUDER` — To uppercase
- [x] `KEEP YOUR VOICE DOWN` — To lowercase
- [x] `CUT THE FAT FROM` — Trim
- [x] `YOU TALKING TO ME ABOUT` — Contains

**Implementation:** Generate `INVOKEVIRTUAL` calls to `java/lang/String` methods.

### Time Functions ✅ COMPLETE
- [x] `WHAT TIME IS IT` — Current time (milliseconds)
- [x] `CHILL OUT FOR` — Sleep/delay

**Implementation:** Generate calls to `System.currentTimeMillis()` and `Thread.sleep()`.

---

## Tier 5: File I/O (Extended Features) ✅ COMPLETE

File system operations.

- [x] `WHAT'S IN THE BOX` — Read entire file as string
- [x] `WRITE THAT DOWN ... TO` — Write string to file
- [x] `SEAL THE EXITS` — Delete file
- [x] `HONEY I'M HOME` — File exists check

**Implementation:** Generate calls to `java/nio/file/Files` and `java/io/File`.

---

## Tier 6: Future Features (Long-term Vision)

Advanced features for future versions. These require major architectural changes.

### OOP Features
- [ ] `MY NAME IS MAXIMUS` — Class definition (Gladiator)
- [ ] `STRENGTH AND HONOR` — End class (Gladiator)
- [ ] `IT'S ALIVE` — Constructor
- [ ] `WELCOME TO EARTH` — New instance
- [ ] `LIKE FATHER LIKE SON` — Inheritance
- [ ] `THAT'S CLASSIFIED` — Private access
- [ ] `OPEN TO THE PUBLIC` — Public access

**Challenges:** Requires multiple class file generation, virtual dispatch tables.

### Lambda Functions
- [ ] `CALL ME SNAKE` — Lambda definition
- [ ] `THE NAME'S PLISSKEN` — Function reference

**Challenges:** Requires closure capture and invokedynamic bytecode.

### Async/Concurrency
- [ ] `COVER ME` — Async operation
- [ ] `HOLD THE LINE` — Await result

**Challenges:** Requires thread management and synchronization primitives.

---

## Implementation Notes

### Key Files

| File | Purpose |
|------|---------|
| `src/main/scala/org/arnoldc/ArnoldParser.scala` | Parsing rules and keywords |
| `src/main/scala/org/arnoldc/ast/*.scala` | AST node types |
| `src/main/scala/org/arnoldc/SymbolTable.scala` | Variable tracking and types |
| `src/main/scala/org/arnoldc/VariableType.scala` | Type system |
| `src/main/scala/org/arnoldc/Declaimer.scala` | Text-to-speech (disabled) |
| `src/test/scala/org/arnoldc/*.scala` | Unit tests |

### Testing Strategy
1. Add parser tests for new syntax
2. Add integration tests with example programs
3. Verify bytecode generation with `javap -c`
4. Test edge cases (empty arrays, division by zero, etc.)

### Backwards Compatibility
- All existing ArnoldC programs continue to work
- New keywords do not conflict with existing ones
- Support both `.arnoldc` and `.actionc` file extensions

---

## Progress Tracking

| Tier | Features | Completed | Progress |
|------|----------|-----------|----------|
| 1 | 9 | 9 | 100% |
| 2 | 10 | 10 | 100% |
| 3 | 15 | 15 | 100% |
| 4 | 13 | 13 | 100% |
| 5 | 4 | 4 | 100% |
| 6 | 11 | 0 | 0% |
| **Total** | **62** | **51** | **82%** |

---

## Test Summary

| Test Suite | Tests |
|------------|-------|
| ArithmeticTest | 37 |
| LogicalTest | 47 |
| BranchStatementTest | 9 |
| MethodTest | 21 |
| ForLoopTest | 9 |
| BreakContinueTest | 5 |
| SwitchTest | 6 |
| StringTest | 10 |
| CommentTest | 5 |
| BlockCommentTest | 6 |
| BitwiseTest | 18 |
| AssertTest | 13 |
| FloatTest | 11 |
| ArrayTest | 13 |
| ErrorHandlingTest | 10 |
| MathFunctionTest | 19 |
| StringFunctionTest | 22 |
| TrigTest | 10 |
| TimeTest | 9 |
| FileIOTest | 12 |
| InputTest | 1 |
| FeatureTest | 4 |
| **Total** | **297** |

---

*"Hasta la vista, baby."* — Implementation complete through Tier 5.
