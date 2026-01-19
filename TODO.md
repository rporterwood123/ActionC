# ActionC Implementation Roadmap

> *"I'll be back."* — T-800, The Terminator (1984)

## Current Status

| Component | Status |
|-----------|--------|
| Language Specification | Complete - see [ACTIONC_SPEC.md](ACTIONC_SPEC.md) |
| Documentation | Complete - see [README.md](README.md) |
| Compiler Implementation | **Tier 2 Complete** — 154 tests passing |

The spec defines 100+ new keywords and features. This roadmap tracks implementation progress.

---

## Tier 1: Quick Wins (High Value, Low Complexity) ✅ COMPLETE

These features require minimal parser/compiler changes but significantly improve usability.

### Comments
- [x] `I'M BATMAN` — Single-line comment (ignore rest of line)
- [ ] `GATHER ROUND` / `DISMISSED` — Block comments

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

## Tier 3: Advanced Features (Medium Value, Higher Complexity)

Powerful features that require significant compiler changes.

### Arrays
- [ ] `I AIN'T GOT TIME TO BLEED` — Declare array
- [ ] `WITH X UGLY MOTHERF***ERS` — Array size
- [ ] `GET IN LINE X AT Y` — Array access
- [ ] `HOW MANY OF THEM` — Array length

**Implementation:**
- Add array type to symbol table
- Generate `NEWARRAY`, `IALOAD`, `IASTORE`, `ARRAYLENGTH` bytecode
- Handle bounds checking

### Float Type
- [ ] `NOW I HAVE A MACHINE GUN` — Declare float
- [ ] `HO HO HO` — Initialize float

**Implementation:**
- Add float type to symbol table
- Generate `FLOAD`, `FSTORE`, `FADD`, `FSUB`, etc. bytecode
- Handle int-to-float conversion

### Error Handling
- [ ] `LET'S SEE WHAT YOU'VE GOT` — Try block
- [ ] `WELCOME TO THE PARTY PAL` — Throw exception
- [ ] `GOTCHA` — Catch block
- [ ] `CLEAN UP ON AISLE FIVE` — Finally block
- [ ] `THAT'S A WRAP` — End try/catch

**Implementation:**
- Generate exception table entries
- Create custom exception class or use `RuntimeException`
- Handle stack frame management in catch blocks

### Assertions
- [ ] `I AM THE LAW` — Assert condition

**Implementation:** Generate conditional throw of `AssertionError`.

### Bitwise Operators
- [ ] `WINNERS GO HOME AND DATE THE PROM QUEEN` — Bitwise AND
- [ ] `DEAD OR ALIVE YOU'RE COMING WITH ME` — Bitwise OR
- [ ] `FRIEND OR FOE` — Bitwise XOR
- [ ] `MOVE IT` — Left shift
- [ ] `FALL BACK` — Right shift

**Implementation:** Generate `IAND`, `IOR`, `IXOR`, `ISHL`, `ISHR` bytecode.

---

## Tier 4: Standard Library (Value-Add Features)

Built-in functions that extend language capabilities.

### Math Functions
- [ ] `GO AHEAD MAKE MY DAY` — Random number
- [ ] `NO MORE HALF MEASURES` — Absolute value
- [ ] `GET TO THE ROOT OF` — Square root
- [ ] `HIT THE FLOOR` — Floor
- [ ] `THROUGH THE ROOF` — Ceiling
- [ ] `IT'S ALL IN THE REFLEXES` — Trig functions

**Implementation:** Generate `INVOKESTATIC` calls to `java/lang/Math` methods.

### String Functions
- [ ] `HOW LONG IS THIS THING` — String length
- [ ] `GIVE ME A PIECE OF` — Substring
- [ ] `SAY IT LOUDER` — To uppercase
- [ ] `KEEP YOUR VOICE DOWN` — To lowercase
- [ ] `CUT THE FAT FROM` — Trim
- [ ] `YOU TALKING TO ME ABOUT` — Contains

**Implementation:** Generate `INVOKEVIRTUAL` calls to `java/lang/String` methods.

### Time Functions
- [ ] `WHAT TIME IS IT` — Current time
- [ ] `CHILL OUT FOR` — Sleep/delay

**Implementation:** Generate calls to `System.currentTimeMillis()` and `Thread.sleep()`.

---

## Tier 5: File I/O (Extended Features)

File system operations.

- [ ] `OPEN THE DOOR` — Open file
- [ ] `WHAT'S IN THE BOX` — Read file
- [ ] `WRITE THAT DOWN` — Write to file
- [ ] `SEAL THE EXITS` — Close file
- [ ] `HONEY I'M HOME` — File exists check

**Implementation:** Generate calls to `java/io/File`, `FileReader`, `FileWriter`, etc.

---

## Tier 6: Future Features (Long-term Vision)

Advanced features for future versions.

### OOP Features
- [ ] `I'M BATMAN` — Class definition
- [ ] `IT'S ALIVE` — Constructor
- [ ] `WELCOME TO EARTH` — New instance
- [ ] `LIKE FATHER LIKE SON` — Inheritance
- [ ] `THAT'S CLASSIFIED` — Private access
- [ ] `OPEN TO THE PUBLIC` — Public access

### Lambda Functions
- [ ] `CALL ME SNAKE` — Lambda definition
- [ ] `THE NAME'S PLISSKEN` — Function reference

### Async/Concurrency
- [ ] `COVER ME` — Async operation
- [ ] `HOLD THE LINE` — Await result

---

## Implementation Notes

### Key Files to Modify

| File | Purpose |
|------|---------|
| `src/main/scala/org/arnoldc/ArnoldParser.scala` | Add new parsing rules |
| `src/main/scala/org/arnoldc/ast/*.scala` | Add new AST node types |
| `src/main/scala/org/arnoldc/*.scala` | Code generation |
| `src/test/scala/org/arnoldc/*.scala` | Unit tests |

### Testing Strategy
1. Add parser tests for new syntax
2. Add integration tests with example programs
3. Verify bytecode generation with `javap -c`
4. Test edge cases (empty arrays, division by zero, etc.)

### Backwards Compatibility
- All existing ArnoldC programs must continue to work
- New keywords must not conflict with existing ones
- Support both `.arnoldc` and `.actionc` file extensions

---

## Progress Tracking

| Tier | Features | Completed | Progress |
|------|----------|-----------|----------|
| 1 | 8 | 8 | 100% |
| 2 | 10 | 10 | 100% |
| 3 | 14 | 0 | 0% |
| 4 | 12 | 0 | 0% |
| 5 | 5 | 0 | 0% |
| 6 | 10 | 0 | 0% |
| **Total** | **59** | **18** | **31%** |

---

*"I'll be back."* — To implement more features.
