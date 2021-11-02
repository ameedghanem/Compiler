# Compiler
This is a compiler for the Mini Java programing language. Follow [this link](http://www.cambridge.org/resources/052182060X/MCIIJ2e/grammar.htm) to see the language syntax. 
The [project](https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/project.html) is the final task in the [Compilation Course](https://www.cs.tau.ac.il/~msagiv/courses/wcc20.html) at **Tel Aviv University**. It was done in triples and it was built in the following steps:
 - <a href=https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/parsing.html target="_blank">Lexing</a>
 - <a href=https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/parsing.html target="_blank">Parsing</a>
 - <a href=https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/semantic.html target="_blank">Semantic & Static analysis</a>
 - <a href=https://www.cs.tau.ac.il/research/yotam.feldman/courses/wcc20/codegen.html target="_blank">Code Generation</a>

## Installation
    $ git clone https://github.com/ameedghanem/Compiler.git
      ...
    $ cd Compiler

## Usage
    $ ant
    $ java -jar mjavac.jar parse compile inputFileName.java outputFileName.ll
    $ lli outputFileName.ll
