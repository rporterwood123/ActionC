# ActionC

> *"I ain't got time to bleed."* — Blain, Predator (1987)

**The 80s/90s Action Movie Programming Language**

ActionC is an esoteric programming language based on iconic one-liners from action movies. It extends the original [ArnoldC](https://github.com/lhartikk/ArnoldC) with quotes from Die Hard, Aliens, Lethal Weapon, Robocop, The Matrix, and many more classics.

**297 tests passing** | **Tier 5 Complete**

---

## Motivation

While ArnoldC brilliantly explored the semantics of Arnold Schwarzenegger's utterances, the true depth of 80s and 90s action cinema remained untapped. ActionC expands the vocabulary to the entire action movie universe—because sometimes you need more than one hero to save the day.

**ActionC is a superset of ArnoldC** — all existing ArnoldC programs work unchanged.

---

## Hello World

```actionc
IT'S SHOWTIME
    TALK TO THE HAND "HELLO BOYS I'M BACK"
YOU HAVE BEEN TERMINATED
```

---

## Quick Start

```bash
wget http://lhartikk.github.io/ActionC.jar
echo -e "IT'S SHOWTIME\nTALK TO THE HAND \"hello world\"\nYOU HAVE BEEN TERMINATED" > hello.actionc
java -jar ActionC.jar hello.actionc
java hello
```

---

## What's New in ActionC

### Control Flow
| Feature | Keyword | Movie |
|---------|---------|-------|
| **For Loops** | `LET'S ROCK` / `GAME OVER MAN GAME OVER` | Aliens |
| **Break** | `GET OUT` | Various |
| **Continue** | `KEEP MOVING` | Various |
| **Switch/Case** | `CHOOSE YOUR DESTINY` / `FINISH HIM` | Mortal Kombat |

### Data Types
| Feature | Keyword | Movie |
|---------|---------|-------|
| **Strings** | `I HAVE COME HERE TO CHEW BUBBLEGUM` | They Live |
| **String Concat** | `AND KICK ASS` (chained) | They Live |
| **Arrays** | `I AIN'T GOT TIME TO BLEED` | Predator |
| **Floats** | `NOW I HAVE A MACHINE GUN` / `HO HO HO` | Die Hard |

### Operators
| Feature | Keyword | Movie |
|---------|---------|-------|
| **Not Equal (!=)** | `IT'S JUST BEEN REVOKED` | Lethal Weapon 2 |
| **Less Than (<)** | `YOU'RE THE DISEASE AND I'M THE CURE` | Cobra |
| **Greater or Equal (>=)** | `I'M GETTING TOO OLD FOR THIS` | Lethal Weapon |
| **Less or Equal (<=)** | `BENEATH YOU` | Blade |
| **Logical NOT** | `NEGATIVE` | Predator 2 |
| **Bitwise AND** | `WINNERS GO HOME AND DATE THE PROM QUEEN` | The Rock |
| **Bitwise OR** | `DEAD OR ALIVE YOU'RE COMING WITH ME` | Robocop |
| **Bitwise XOR** | `FRIEND OR FOE` | Various |
| **Left Shift** | `MOVE IT` | Various |
| **Right Shift** | `FALL BACK` | Various |

### Comments
| Feature | Keyword | Movie |
|---------|---------|-------|
| **Single-line** | `I'M BATMAN` | Batman (1989) |
| **Block comments** | `GATHER ROUND` / `DISMISSED` | Various |

### Math Functions
| Feature | Keyword | Movie |
|---------|---------|-------|
| **Random** | `GO AHEAD MAKE MY DAY` | Dirty Harry |
| **Absolute Value** | `NO MORE HALF MEASURES` | Breaking Bad |
| **Square Root** | `GET TO THE ROOT OF` | Pun |
| **Floor** | `HIT THE FLOOR` | Various |
| **Ceiling** | `THROUGH THE ROOF` | Various |
| **Sin/Cos/Tan** | `IT'S ALL IN THE REFLEXES` | Big Trouble in Little China |

### String Functions
| Feature | Keyword | Movie |
|---------|---------|-------|
| **Length** | `HOW LONG IS THIS THING` | Various |
| **Substring** | `GIVE ME A PIECE OF ... FROM ... TO` | Various |
| **Uppercase** | `SAY IT LOUDER` | Various |
| **Lowercase** | `KEEP YOUR VOICE DOWN` | Various |
| **Trim** | `CUT THE FAT FROM` | Various |
| **Contains** | `YOU TALKING TO ME ABOUT` | Taxi Driver |

### Time Functions
| Feature | Keyword | Movie |
|---------|---------|-------|
| **Current Time** | `WHAT TIME IS IT` | Various |
| **Sleep** | `CHILL OUT FOR` | Mr. Freeze - Batman |

### File I/O
| Feature | Keyword | Movie |
|---------|---------|-------|
| **File Exists** | `HONEY I'M HOME` | The Shining |
| **Read File** | `WHAT'S IN THE BOX` | Se7en |
| **Write File** | `WRITE THAT DOWN ... TO` | Various |
| **Delete File** | `SEAL THE EXITS` | Various |

### Error Handling
| Feature | Keyword | Movie |
|---------|---------|-------|
| **Try** | `LET'S SEE WHAT YOU'VE GOT` | Various |
| **Throw** | `WELCOME TO THE PARTY PAL` | Die Hard |
| **Catch** | `GOTCHA` | Various |
| **Finally** | `CLEAN UP ON AISLE FIVE` | Various |
| **Assert** | `I AM THE LAW` | Judge Dredd |

### Utility
| Feature | Keyword | Movie |
|---------|---------|-------|
| **Increment (++)** | `ONE MORE TIME` | Various |
| **Decrement (--)** | `COUNTDOWN` | Various |

See [ACTIONC_SPEC.md](ACTIONC_SPEC.md) for the complete language specification.

---

## Examples

### For Loop with Break

```actionc
IT'S SHOWTIME
    LET'S ROCK i FROM 1 TO 10
        BECAUSE I'M GOING TO SAY PLEASE i YOU ARE NOT YOU YOU ARE ME 5
            TALK TO THE HAND "Found 5!"
            GET OUT
        YOU HAVE NO RESPECT FOR LOGIC
        TALK TO THE HAND i
    GAME OVER MAN GAME OVER
YOU HAVE BEEN TERMINATED
```

### String Manipulation

```actionc
IT'S SHOWTIME
    I HAVE COME HERE TO CHEW BUBBLEGUM greeting
    AND KICK ASS "  hello world  "

    I'M BATMAN Trim and uppercase
    I HAVE COME HERE TO CHEW BUBBLEGUM result
    AND KICK ASS SAY IT LOUDER CUT THE FAT FROM greeting

    TALK TO THE HAND result
    I'M BATMAN Output: HELLO WORLD

    I'M BATMAN Get length
    HEY CHRISTMAS TREE len
    YOU SET US UP HOW LONG IS THIS THING result
    TALK TO THE HAND len
YOU HAVE BEEN TERMINATED
```

### Arrays

```actionc
IT'S SHOWTIME
    I'M BATMAN Declare array of 5 integers
    I AIN'T GOT TIME TO BLEED scores WITH 5 UGLY MOTHERFUCKERS

    I'M BATMAN Set elements
    PUT 100 IN LINE scores AT 0
    PUT 95 IN LINE scores AT 1
    PUT 87 IN LINE scores AT 2

    I'M BATMAN Print array length
    TALK TO THE HAND HOW MANY OF THEM scores

    I'M BATMAN Access element
    TALK TO THE HAND GET IN LINE scores AT 0
YOU HAVE BEEN TERMINATED
```

### Math Functions

```actionc
IT'S SHOWTIME
    I'M BATMAN Absolute value
    HEY CHRISTMAS TREE absVal
    YOU SET US UP NO MORE HALF MEASURES -42
    TALK TO THE HAND absVal
    I'M BATMAN Output: 42

    I'M BATMAN Square root
    HEY CHRISTMAS TREE sqrtVal
    YOU SET US UP GET TO THE ROOT OF 16
    TALK TO THE HAND sqrtVal
    I'M BATMAN Output: 4

    I'M BATMAN Trig (returns value * 1000 for precision)
    HEY CHRISTMAS TREE cosVal
    YOU SET US UP IT'S ALL IN THE REFLEXES COS 0
    TALK TO THE HAND cosVal
    I'M BATMAN Output: 1000 (cos(0) = 1.0)
YOU HAVE BEEN TERMINATED
```

### File I/O

```actionc
IT'S SHOWTIME
    I'M BATMAN Write to file
    WRITE THAT DOWN "Hello from ActionC!" TO "test.txt"

    I'M BATMAN Check if file exists
    HEY CHRISTMAS TREE exists
    YOU SET US UP HONEY I'M HOME "test.txt"
    TALK TO THE HAND exists
    I'M BATMAN Output: 1

    I'M BATMAN Read file contents
    I HAVE COME HERE TO CHEW BUBBLEGUM content
    AND KICK ASS WHAT'S IN THE BOX "test.txt"
    TALK TO THE HAND content

    I'M BATMAN Delete file
    SEAL THE EXITS "test.txt"
YOU HAVE BEEN TERMINATED
```

### Error Handling

```actionc
IT'S SHOWTIME
    LET'S SEE WHAT YOU'VE GOT
        TALK TO THE HAND "In try block"
        WELCOME TO THE PARTY PAL "Oops!"
        TALK TO THE HAND "This won't print"
    GOTCHA
        TALK TO THE HAND "Caught exception!"
    THAT'S A WRAP
YOU HAVE BEEN TERMINATED
```

### Block Comments

```actionc
IT'S SHOWTIME
    GATHER ROUND
        This is a multi-line comment.
        It can span as many lines as needed.
        Perfect for documentation!
    DISMISSED

    TALK TO THE HAND "Hello World"
YOU HAVE BEEN TERMINATED
```

---

## Keywords by Movie

### Terminator / Terminator 2 (Arnold Schwarzenegger)

| Keyword | Purpose |
|---------|---------|
| `@I LIED` | False |
| `@NO PROBLEMO` | True |
| `I'LL BE BACK` | Return |
| `HASTA LA VISTA, BABY` | End Method |
| `I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE` | Method Arguments |
| `GET TO THE CHOPPER` | Begin Assignment |
| `YOU SET US UP` | Set Initial Value |

### Die Hard (John McClane)

| Keyword | Purpose |
|---------|---------|
| `NOW I HAVE A MACHINE GUN` | Declare Float |
| `HO HO HO` | Initialize Float |
| `WELCOME TO THE PARTY PAL` | Throw Exception |

### Aliens (Hudson, Hicks, Ripley)

| Keyword | Purpose |
|---------|---------|
| `LET'S ROCK` | For Loop Start |
| `GAME OVER MAN GAME OVER` | For Loop End |

### Predator

| Keyword | Purpose |
|---------|---------|
| `STICK AROUND` | While Loop |
| `I AIN'T GOT TIME TO BLEED` | Declare Array |
| `NEGATIVE` | Logical NOT |

### Lethal Weapon (Riggs & Murtaugh)

| Keyword | Purpose |
|---------|---------|
| `IT'S JUST BEEN REVOKED` | Not Equal (!=) |
| `I'M GETTING TOO OLD FOR THIS` | Greater Than or Equal (>=) |

### Robocop

| Keyword | Purpose |
|---------|---------|
| `DEAD OR ALIVE YOU'RE COMING WITH ME` | Bitwise OR |

### The Matrix

| Keyword | Purpose |
|---------|---------|
| `@THERE IS NO SPOON` | Null Value |
| `WHAT IF I TOLD YOU` | Switch Case |

### Dirty Harry

| Keyword | Purpose |
|---------|---------|
| `GO AHEAD MAKE MY DAY` | Random() |

### Batman (1989)

| Keyword | Purpose |
|---------|---------|
| `I'M BATMAN` | Single-line Comment |

### They Live

| Keyword | Purpose |
|---------|---------|
| `I HAVE COME HERE TO CHEW BUBBLEGUM` | Declare String |
| `AND KICK ASS` | Initialize/Concat String |
| `AND I'M ALL OUT OF BUBBLEGUM` | Empty String |

### Cobra

| Keyword | Purpose |
|---------|---------|
| `YOU'RE THE DISEASE AND I'M THE CURE` | Less Than (<) |

### Judge Dredd

| Keyword | Purpose |
|---------|---------|
| `I AM THE LAW` | Assert |

### The Rock

| Keyword | Purpose |
|---------|---------|
| `WINNERS GO HOME AND DATE THE PROM QUEEN` | Bitwise AND |

### Big Trouble in Little China

| Keyword | Purpose |
|---------|---------|
| `IT'S ALL IN THE REFLEXES` | Trig Functions (SIN/COS/TAN) |

### Mortal Kombat

| Keyword | Purpose |
|---------|---------|
| `CHOOSE YOUR DESTINY` | Switch Start |
| `FINISH HIM` | Switch End |

### Se7en

| Keyword | Purpose |
|---------|---------|
| `WHAT'S IN THE BOX` | Read File |

### The Shining

| Keyword | Purpose |
|---------|---------|
| `HONEY I'M HOME` | File Exists |

### Gladiator (Future OOP)

| Keyword | Purpose |
|---------|---------|
| `MY NAME IS MAXIMUS` | Class Definition |
| `STRENGTH AND HONOR` | End Class |

---

## Backwards Compatibility

ActionC is fully backwards compatible with ArnoldC. All existing `.arnoldc` programs will run unchanged. The compiler accepts both `.arnoldc` and `.actionc` file extensions.

---

## Documentation

- **[ACTIONC_SPEC.md](ACTIONC_SPEC.md)** — Complete language specification with grammar
- **[TODO.md](TODO.md)** — Implementation roadmap and progress
- **[EVALUATION.md](EVALUATION.md)** — Analysis of original ArnoldC limitations

---

## Contributing

Found a perfect action movie quote for a missing feature? Open an issue or PR!

Requirements for new quotes:
1. Must be from an action movie (preferably 80s/90s era)
2. Must thematically match the programming concept
3. Bonus points if it's instantly recognizable

---

## Credits

- Original ArnoldC by [Lauri Hartikka](https://github.com/lhartikk)
- ActionC expansion inspired by the greatest era of action cinema

---

*"Hasta la vista, baby."*
