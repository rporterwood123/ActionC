# ActionC

> *"I ain't got time to bleed."* — Blain, Predator (1987)

**The 80s/90s Action Movie Programming Language**

ActionC is an esoteric programming language based on iconic one-liners from action movies. It extends the original [ArnoldC](https://github.com/lhartikk/ArnoldC) with quotes from Die Hard, Aliens, Lethal Weapon, Robocop, The Matrix, and many more classics.

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

| Feature | Keyword | Movie |
|---------|---------|-------|
| **Arrays** | `I AIN'T GOT TIME TO BLEED` | Predator |
| **For Loops** | `LET'S ROCK` / `GAME OVER MAN GAME OVER` | Aliens |
| **Strings** | `I HAVE COME HERE TO CHEW BUBBLEGUM` | They Live |
| **Not Equal (!=)** | `IT'S JUST BEEN REVOKED` | Lethal Weapon 2 |
| **Less Than (<)** | `YOU'RE THE DISEASE AND I'M THE CURE` | Cobra |
| **Comments** | `I'M BATMAN` | Batman (1989) |
| **Try/Catch** | `LET'S SEE WHAT YOU'VE GOT` / `GOTCHA` | Various |
| **Random** | `GO AHEAD MAKE MY DAY` | Dirty Harry |
| **Null** | `@THERE IS NO SPOON` | The Matrix |
| **Break** | `GET OUT` | Various |

See [ACTIONC_SPEC.md](ACTIONC_SPEC.md) for the complete language specification.

---

## Keywords by Movie

### Terminator / Terminator 2 (Arnold Schwarzenegger)

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`@I LIED`](http://www.youtube.com/watch?v=_wk-jT9rn-8) | False | |
| [`@NO PROBLEMO`](http://www.youtube.com/watch?v=CtNb1dnEaSQ) | True | |
| [`I'LL BE BACK`](http://www.youtube.com/watch?v=-YEG9DgRHhA) | Return | |
| [`HASTA LA VISTA, BABY`](http://www.youtube.com/watch?v=Hhm7aWp8gvc) | End Method | |
| [`I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE`](http://www.youtube.com/watch?v=FWmH9ylqYYQ) | Method Arguments | |
| [`GET TO THE CHOPPER`](http://www.youtube.com/watch?v=-9-Te-DPbSE) | Begin Assignment | |
| [`YOU SET US UP`](http://www.youtube.com/watch?v=lwqzA6F7nws) | Set Initial Value | |

### Predator

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`STICK AROUND`](http://www.youtube.com/watch?v=wDztrw_0N8M) | While Loop | |
| [`WHAT THE FUCK DID I DO WRONG`](http://www.youtube.com/watch?v=oGcRTJK43OM) | Parse Error | |
| `I AIN'T GOT TIME TO BLEED` | Declare Array | [Clip](https://www.youtube.com/watch?v=PrMlHn1R_hU) |
| `WHAT ARE YOU` | Type Check | [Clip](https://www.youtube.com/watch?v=qlicWUDf5MM) |

### Commando

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`LET OFF SOME STEAM BENNET`](http://www.youtube.com/watch?v=19R2fDXCzcM) | Greater Than (>) | |

### Total Recall

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`CONSIDER THAT A DIVORCE`](http://www.youtube.com/watch?v=RYtQMhnBtTw) | Logical OR | |
| [`GET YOUR ASS TO MARS`](http://www.youtube.com/watch?v=HkkibBYm2WI) | Assign From Method Call | |

### The Running Man

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`HERE IS MY INVITATION`](http://www.youtube.com/watch?v=RrPXRkJ_P90) | Set Value | |

### Twins

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`KNOCK KNOCK`](http://www.youtube.com/watch?v=ZQ_Q2b_aXjk) | Logical AND | |
| [`BECAUSE I'M GOING TO SAY PLEASE`](http://www.youtube.com/watch?v=MiB7GLyvvJQ) | If Statement | |

### Kindergarten Cop

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`IT'S SHOWTIME`](http://www.youtube.com/watch?v=TKTL2EDTFSo) | Begin Main | |
| [`LISTEN TO ME VERY CAREFULLY`](http://www.youtube.com/watch?v=uCwrOpnyXeo) | Declare Method | |

### Last Action Hero

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`YOU ARE NOT YOU YOU ARE ME`](http://www.youtube.com/watch?v=A1-wUV0-_JY) | Equal To (==) | |
| [`BULLSHIT`](http://www.youtube.com/watch?v=c4psKYpfnYs) | Else | |
| [`YOU HAVE NO RESPECT FOR LOGIC`](http://youtu.be/uGstM8QMCjQ?t=1m23s) | End If | |

### Sixth Day

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`HEY CHRISTMAS TREE`](http://www.youtube.com/watch?v=PZwwqjcEDUQ) | Declare Integer | |
| [`ENOUGH TALK`](http://www.youtube.com/watch?v=rk9WHasIZk0) | End Assignment | |

### Jingle All The Way

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`YOU HAVE BEEN TERMINATED`](http://www.youtube.com/watch?v=iy_BBBGBpqA) | End Main | |

### Eraser

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`YOU'RE FIRED`](http://www.youtube.com/watch?v=lf3Kyv_iaNs) | Multiplication (*) | |
| `GET DOWN` | Subtraction (-) | [Clip](http://www.youtube.com/watch?v=7Ox0Ehq-FRQ) |
| `GET UP` | Addition (+) | |

### End of Days

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`HE HAD TO SPLIT`](http://www.youtube.com/watch?v=9VHtuqXZQeo) | Division (/) | |

### Collateral Damage

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`I LET HIM GO`](http://www.youtube.com/watch?v=ybJWKZB0Erk&feature=youtu.be&t=6m59s) | Modulo (%) | |

### True Lies

| Keyword | Purpose | Clip |
|---------|---------|------|
| [`GIVE THESE PEOPLE AIR`](http://www.youtube.com/watch?v=WANa9Oku-JM) | Non-Void Method | |
| [`DO IT NOW`](http://www.youtube.com/watch?v=HGhP3p6lI3U) | Call Method | |
| [`TALK TO THE HAND`](http://www.youtube.com/watch?v=dQ6m8ztEzfA) | Print | |
| [`CHILL`](http://www.youtube.com/watch?v=R39e30FL37U) | End While | |
| [`I WANT TO ASK YOU A BUNCH OF QUESTIONS AND I WANT TO HAVE THEM ANSWERED IMMEDIATELY`](https://www.youtube.com/watch?v=1mC9eOqsyTg) | Read Integer | |

---

### Die Hard (John McClane)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `NOW I HAVE A MACHINE GUN` | Declare Float | [Clip](https://www.youtube.com/watch?v=vD94dVu8lqQ) |
| `HO HO HO` | Initialize Float | |
| `WELCOME TO THE PARTY PAL` | Throw Exception | [Clip](https://www.youtube.com/watch?v=vD94dVu8lqQ) |
| `YIPPEE KI YAY` | Success Message | [Clip](https://www.youtube.com/watch?v=OTyw6cq86kY) |

### Aliens (Hudson, Hicks, Ripley)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `LET'S ROCK` | For Loop Start | [Clip](https://www.youtube.com/watch?v=dsx2vdn7gpY) |
| `GAME OVER MAN GAME OVER` | For Loop End | [Clip](https://www.youtube.com/watch?v=dsx2vdn7gpY) |
| `STAY FROSTY` | Debug Mode | |

### Lethal Weapon (Riggs & Murtaugh)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `IT'S JUST BEEN REVOKED` | Not Equal (!=) | [Clip](https://www.youtube.com/watch?v=kwC_IaY3BmY) |
| `I'M GETTING TOO OLD FOR THIS` | Greater Than or Equal (>=) | [Clip](https://www.youtube.com/watch?v=Q37xJtuQ24w) |

### Robocop (Murphy)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `DEAD OR ALIVE YOU'RE COMING WITH ME` | Bitwise OR | [Clip](https://www.youtube.com/watch?v=WVXGC896Jdw) |
| `YOUR MOVE CREEP` | Await Input | [Clip](https://www.youtube.com/watch?v=3PbdyJ_ybSI) |
| `FREEZE` | Debugger Breakpoint | |

### The Matrix (Neo, Morpheus)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `@THERE IS NO SPOON` | Null Value | [Clip](https://www.youtube.com/watch?v=uAXtO5dMqEI) |
| `WHAT IF I TOLD YOU` | Switch Case | [Clip](https://www.youtube.com/watch?v=nb0YoRMXIY0) |
| `FOLLOW THE WHITE RABBIT` | Import/Include | |

### Dirty Harry / Sudden Impact (Harry Callahan)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `GO AHEAD MAKE MY DAY` | Random() | [Clip](https://www.youtube.com/watch?v=8Xjr2hnOHiM) |
| `DO YOU FEEL LUCKY` | Declare Boolean | [Clip](https://www.youtube.com/watch?v=8Xjr2hnOHiM) |
| `WELL DO YA PUNK` | Initialize Boolean | |

### Batman (1989) (Bruce Wayne)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `I'M BATMAN` | Comment (single line) | [Clip](https://www.youtube.com/watch?v=7tdpTMzYsXw) |

### They Live (Nada)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `I HAVE COME HERE TO CHEW BUBBLEGUM` | Declare String | [Clip](https://www.youtube.com/watch?v=Du5YK5FnyF4) |
| `AND KICK ASS` | Initialize/Concat String | [Clip](https://www.youtube.com/watch?v=Du5YK5FnyF4) |
| `AND I'M ALL OUT OF BUBBLEGUM` | Empty String | |

### Cobra (Marion Cobretti)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `YOU'RE THE DISEASE AND I'M THE CURE` | Less Than (<) | [Clip](https://www.youtube.com/watch?v=aRL0Xt0rzzQ) |

### Judge Dredd

| Keyword | Purpose | Clip |
|---------|---------|------|
| `I AM THE LAW` | Assert | [Clip](https://www.youtube.com/watch?v=wvJiYrRcfQo) |

### Escape from New York (Snake Plissken)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `CALL ME SNAKE` | Lambda Function | [Clip](https://www.youtube.com/watch?v=R3zdYUG2_RA) |
| `THE NAME'S PLISSKEN` | Function Reference | |

### The Rock

| Keyword | Purpose | Clip |
|---------|---------|------|
| `WINNERS GO HOME AND DATE THE PROM QUEEN` | Bitwise AND | [Clip](https://www.youtube.com/watch?v=98wVFsIt-MQ) |

### Independence Day (President Whitmore, Capt. Hiller)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `HELLO BOYS I'M BACK` | Hello World Message | [Clip](https://www.youtube.com/watch?v=NyOTaHRBTXc) |
| `WELCOME TO EARTH` | New Instance | [Clip](https://www.youtube.com/watch?v=OfPWpEKhgfk) |

### Big Trouble in Little China (Jack Burton)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `IT'S ALL IN THE REFLEXES` | Trig Functions (sin/cos/tan) | [Clip](https://www.youtube.com/watch?v=UcIH7xvd_4A) |

### Mortal Kombat

| Keyword | Purpose | Clip |
|---------|---------|------|
| `CHOOSE YOUR DESTINY` | Switch Start | [Clip](https://www.youtube.com/watch?v=EAwWPadFsOA) |
| `FINISH HIM` | Switch End | [Clip](https://www.youtube.com/watch?v=_hHDxlm66dE) |

### Speed (Jack Traven)

| Keyword | Purpose | Clip |
|---------|---------|------|
| `THE CLOCK IS TICKING` | Timer Start | |

---

## Example: FizzBuzz

```actionc
IT'S SHOWTIME
    LET'S ROCK i FROM 1 TO 100
        HEY CHRISTMAS TREE mod15
        YOU SET US UP i
        GET TO THE CHOPPER mod15
        HERE IS MY INVITATION mod15
        I LET HIM GO 15
        ENOUGH TALK

        BECAUSE I'M GOING TO SAY PLEASE mod15 YOU ARE NOT YOU YOU ARE ME 0
            TALK TO THE HAND "FizzBuzz"
        BULLSHIT
            HEY CHRISTMAS TREE mod3
            YOU SET US UP i
            GET TO THE CHOPPER mod3
            HERE IS MY INVITATION mod3
            I LET HIM GO 3
            ENOUGH TALK

            BECAUSE I'M GOING TO SAY PLEASE mod3 YOU ARE NOT YOU YOU ARE ME 0
                TALK TO THE HAND "Fizz"
            BULLSHIT
                HEY CHRISTMAS TREE mod5
                YOU SET US UP i
                GET TO THE CHOPPER mod5
                HERE IS MY INVITATION mod5
                I LET HIM GO 5
                ENOUGH TALK

                BECAUSE I'M GOING TO SAY PLEASE mod5 YOU ARE NOT YOU YOU ARE ME 0
                    TALK TO THE HAND "Buzz"
                BULLSHIT
                    TALK TO THE HAND i
                YOU HAVE NO RESPECT FOR LOGIC
            YOU HAVE NO RESPECT FOR LOGIC
        YOU HAVE NO RESPECT FOR LOGIC
    GAME OVER MAN GAME OVER
YOU HAVE BEEN TERMINATED
```

---

## Example: Arrays (NEW in ActionC)

```actionc
IT'S SHOWTIME
    I'M BATMAN Declare an array of 5 scores
    I AIN'T GOT TIME TO BLEED scores WITH 5 UGLY MOTHERF***ERS

    I'M BATMAN Initialize first element
    GET IN LINE scores AT 0
    HERE IS MY INVITATION 100
    ENOUGH TALK

    I'M BATMAN Print array length
    HEY CHRISTMAS TREE len
    YOU SET US UP HOW MANY OF THEM scores
    TALK TO THE HAND len
YOU HAVE BEEN TERMINATED
```

---

## Example: Error Handling (NEW in ActionC)

```actionc
IT'S SHOWTIME
    LET'S SEE WHAT YOU'VE GOT
        HEY CHRISTMAS TREE divisor
        YOU SET US UP 0

        BECAUSE I'M GOING TO SAY PLEASE divisor YOU ARE NOT YOU YOU ARE ME 0
            WELCOME TO THE PARTY PAL "Cannot divide by zero!"
        YOU HAVE NO RESPECT FOR LOGIC
    GOTCHA error
        TALK TO THE HAND "Error caught:"
        TALK TO THE HAND error
    THAT'S A WRAP
YOU HAVE BEEN TERMINATED
```

---

## Backwards Compatibility

ActionC is fully backwards compatible with ArnoldC. All existing `.arnoldc` programs will run unchanged. The compiler accepts both `.arnoldc` and `.actionc` file extensions.

---

## Documentation

- **[ACTIONC_SPEC.md](ACTIONC_SPEC.md)** — Complete language specification with grammar
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
