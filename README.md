# Compiler
This is a compiler for the Mini Java programing language. Follow [this link](http://www.cambridge.org/resources/052182060X/MCIIJ2e/grammar.htm) to read this language syntax. 
The [project](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/project.html) was in triples and it was built in the following steps:
 - [Lexing](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/parsing.html)
 - [Parsing](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/parsing.html)
 - [Semantic & Static analysis](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/semantic.html)
 - [Code Generation](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/codegen.html)

## Usage
    $ ant
    $ java -jar mjavac.jar parse compile inputProg.java out.ll
    $ lli out.ll
