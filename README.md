# Compiler
This is a compiler for the Mini Java programing language.
The [project](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/project.html) was in triples and it was built in the following steps:
 - [Lexing](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/parsing.html)
 - [Parsing](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/parsing.html)
 - [Semantic & Static analysis](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/semantic.html)
 - [Code Generation](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/codegen.html)

## Usage
    $ ant\
    $ java -jar mjavac.jar parse compile inputProg.java out.ll\
    $ lli out.ll
