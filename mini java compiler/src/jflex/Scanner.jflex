/***************************/
/* Based on a template by Oren Ish-Shalom */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;



/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/

/*****************************************************/
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup

/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */
/*****************************************************************************/


%{
  /*********************************************************************************/
  /* Create a new java_cup.runtime.Symbol with information about the current token */
  /*********************************************************************************/
  private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
  private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

  /*******************************************/
  /* Enable line number extraction from main */
  /*******************************************/
  public int getLine()    { return yyline + 1; }
  public int getCharPos() { return yycolumn;   }
%}


/***********************/
/* MACRO DECALARATIONS */
/***********************/

/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n

/* White space is a line terminator, space, tab, or line feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]

/* A literal integer is is a number beginning with a number between
   one and nine followed by zero or more numbers between zero and nine
   or just a zero.  */
Dec_int_lit = 0 | [1-9][0-9]*

/* Identifiers */
Identifier = [a-zA-Z_]+[a-zA-Z0-9_]*



/* Comment */

InputCharacter        = [^\r\n]
EndOfLineComment      = "//" {InputCharacter}* {LineTerminator}?
TraditionalComment      = "/*" [^*] ~"*/" | "/*" "*"+ "/"


COMMENT= {EndOfLineComment} | {TraditionalComment}

%%
/* ------------------------Lexical Rules Section---------------------- */

<YYINITIAL> {
    /* separators */
    "("                   { return symbol(sym.LPAREN); }
    ")"                   { return symbol(sym.RPAREN); }
    "{"                   { return symbol(sym.LCURLYBRACKET); }
    "}"                   { return symbol(sym.RCURLYBRACKET); }
    "["                   { return symbol(sym.LEFTBRACKET); }
    "]"                   { return symbol(sym.RIGHTBRACKET); }
    ","                   { return symbol(sym.COMMA); }
    "boolean"             { return symbol(sym.BOOLEAN); }
    "class"               { return symbol(sym.CLASS); }
    "else"                { return symbol(sym.ELSE); }
    "extends"             { return symbol(sym.EXTENDS); }
    "false"               { return symbol(sym.FALSE); }
    "if"                  { return symbol(sym.IF); }
    "int"                 { return symbol(sym.INT); }
    "length"              {  return symbol(sym.LENGTH); }
    "main"                { return symbol(sym.MAIN); }
    "new"                 { return symbol(sym.NEW); }
    "public"              { return symbol(sym.PUBLIC); }
    "return"              { return symbol(sym.RETURN); }
    "static"              { return symbol(sym.STATIC); }
    "String"              { return symbol(sym.STRING);}
    "System.out.println"  { return symbol(sym.PRINTLN); }
    "this"                { return symbol(sym.THIS); }
    "true"                { return symbol(sym.TRUE); }
    "void"                { return symbol(sym.VOID); }
    "while"               { return symbol(sym.WHILE); }
    "+"                   { return symbol(sym.PLUS); }
    "."                   { return symbol(sym.PERIOD); }
    "-"                   { return symbol(sym.MINUS); }
    "*"                   { return symbol(sym.TIMES); }
    ";"                   { return symbol(sym.SEMICOLON); }
    "="                   { return symbol(sym.EQUALS); }
    "<"                   { return symbol(sym.LESSTHAN); }
    "!"                   {  return symbol(sym.EXCLAMATION); }
    "&&"                  { return symbol(sym.AND); }



    /* identifiers */
    {Identifier}            { return symbol(sym.IDENTIFIER, yytext()); }

    // /* numbers */
    {Dec_int_lit}           { return symbol(sym.NUMBER, new Integer(yytext())); }

    /* whitespaces */
    {WhiteSpace}            { /* just skip what was found, do nothing */ }

    /* comment */
    {COMMENT}               { /* just skip what was found, do nothing */ }


    <<EOF>>                 { return symbol(sym.EOF); }



}

/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
/*[^]                    { throw new Error("Syntax error at line " + getLine() +  " of input."); }*/
