// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: src/jflex/Scanner.jflex

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


// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
class Lexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0, 0
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\u10ff\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\0\1\1\1\3\22\0\1\1"+
    "\1\4\4\0\1\5\1\0\1\6\1\7\1\10\1\11"+
    "\1\12\1\13\1\14\1\15\1\16\11\17\1\0\1\20"+
    "\1\21\1\22\3\0\22\23\1\24\7\23\1\25\1\0"+
    "\1\26\1\0\1\23\1\0\1\27\1\30\1\31\1\32"+
    "\1\33\1\34\1\35\1\36\1\37\2\23\1\40\1\41"+
    "\1\42\1\43\1\44\1\23\1\45\1\46\1\47\1\50"+
    "\1\51\1\52\1\53\1\54\1\23\1\55\1\0\1\56"+
    "\u0182\0";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[512];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\2\1\1\2\1\0\1\3\1\4\1\5\1\6"+
    "\1\7\1\10\1\11\1\0\2\12\1\13\1\14\1\15"+
    "\2\16\1\17\1\20\16\16\1\21\1\22\1\23\1\0"+
    "\1\1\7\16\1\24\13\16\2\0\7\16\1\25\2\16"+
    "\1\26\7\16\1\0\4\16\1\27\3\16\1\30\3\16"+
    "\1\31\1\32\1\33\4\16\1\34\1\16\1\35\4\16"+
    "\1\36\1\37\3\16\1\40\1\41\1\42\1\43\1\0"+
    "\1\44\1\45\12\0\1\46";

  private static int [] zzUnpackAction() {
    int [] result = new int[130];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\57\0\136\0\57\0\215\0\57\0\57\0\57"+
    "\0\57\0\57\0\57\0\57\0\274\0\57\0\353\0\57"+
    "\0\57\0\57\0\u011a\0\u0149\0\57\0\57\0\u0178\0\u01a7"+
    "\0\u01d6\0\u0205\0\u0234\0\u0263\0\u0292\0\u02c1\0\u02f0\0\u031f"+
    "\0\u034e\0\u037d\0\u03ac\0\u03db\0\57\0\57\0\57\0\u040a"+
    "\0\u0439\0\u0468\0\u0497\0\u04c6\0\u04f5\0\u0524\0\u0553\0\u0582"+
    "\0\u011a\0\u05b1\0\u05e0\0\u060f\0\u063e\0\u066d\0\u069c\0\u06cb"+
    "\0\u06fa\0\u0729\0\u0758\0\u0787\0\u07b6\0\u07e5\0\u0814\0\u0843"+
    "\0\u0872\0\u08a1\0\u08d0\0\u08ff\0\u092e\0\u011a\0\u095d\0\u098c"+
    "\0\u011a\0\u09bb\0\u09ea\0\u0a19\0\u0a48\0\u0a77\0\u0aa6\0\u0ad5"+
    "\0\u0b04\0\u0b33\0\u0b62\0\u0b91\0\u0bc0\0\u011a\0\u0bef\0\u0c1e"+
    "\0\u0c4d\0\u011a\0\u0c7c\0\u0cab\0\u0cda\0\u011a\0\u011a\0\u011a"+
    "\0\u0d09\0\u0d38\0\u0d67\0\u0d96\0\u011a\0\u0dc5\0\u011a\0\u0df4"+
    "\0\u0e23\0\u0e52\0\u0e81\0\u011a\0\u011a\0\u0eb0\0\u0edf\0\u0f0e"+
    "\0\u011a\0\u011a\0\u011a\0\u011a\0\u0f3d\0\u011a\0\u011a\0\u0f6c"+
    "\0\u0f9b\0\u0fca\0\u0ff9\0\u1028\0\u1057\0\u1086\0\u10b5\0\u10e4"+
    "\0\u1113\0\57";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[130];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\0\2\2\1\3\1\4\1\5\1\6\1\7\1\10"+
    "\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20"+
    "\1\21\1\22\1\23\1\24\1\25\1\26\1\23\1\27"+
    "\1\30\1\23\1\31\1\32\2\23\1\33\1\34\1\35"+
    "\1\36\1\23\1\37\1\40\1\41\1\42\1\23\1\43"+
    "\1\44\2\23\1\45\1\46\61\0\1\2\61\0\1\47"+
    "\61\0\1\50\4\0\1\51\57\0\2\17\55\0\2\23"+
    "\3\0\2\23\2\0\26\23\20\0\2\23\3\0\2\23"+
    "\2\0\20\23\1\52\4\23\1\53\20\0\2\23\3\0"+
    "\2\23\2\0\14\23\1\54\11\23\20\0\2\23\3\0"+
    "\2\23\2\0\11\23\1\55\14\23\20\0\2\23\3\0"+
    "\2\23\2\0\11\23\1\56\12\23\1\57\1\23\20\0"+
    "\2\23\3\0\2\23\2\0\1\60\25\23\20\0\2\23"+
    "\3\0\2\23\2\0\5\23\1\61\5\23\1\62\12\23"+
    "\20\0\2\23\3\0\2\23\2\0\4\23\1\63\21\23"+
    "\20\0\2\23\3\0\2\23\2\0\1\64\25\23\20\0"+
    "\2\23\3\0\2\23\2\0\4\23\1\65\21\23\20\0"+
    "\2\23\3\0\2\23\2\0\21\23\1\66\4\23\20\0"+
    "\2\23\3\0\2\23\2\0\4\23\1\67\21\23\20\0"+
    "\2\23\3\0\2\23\2\0\20\23\1\70\5\23\20\0"+
    "\2\23\3\0\2\23\2\0\7\23\1\71\6\23\1\72"+
    "\7\23\20\0\2\23\3\0\2\23\2\0\14\23\1\73"+
    "\11\23\20\0\2\23\3\0\2\23\2\0\7\23\1\74"+
    "\16\23\2\0\10\75\1\76\46\75\2\51\1\2\1\3"+
    "\53\51\16\0\2\23\3\0\2\23\2\0\16\23\1\77"+
    "\7\23\20\0\2\23\3\0\2\23\2\0\17\23\1\100"+
    "\6\23\20\0\2\23\3\0\2\23\2\0\14\23\1\101"+
    "\11\23\20\0\2\23\3\0\2\23\2\0\1\102\25\23"+
    "\20\0\2\23\3\0\2\23\2\0\17\23\1\103\6\23"+
    "\20\0\2\23\3\0\2\23\2\0\20\23\1\104\5\23"+
    "\20\0\2\23\3\0\2\23\2\0\11\23\1\105\14\23"+
    "\20\0\2\23\3\0\2\23\2\0\20\23\1\106\5\23"+
    "\20\0\2\23\3\0\2\23\2\0\13\23\1\107\12\23"+
    "\20\0\2\23\3\0\2\23\2\0\10\23\1\110\15\23"+
    "\20\0\2\23\3\0\2\23\2\0\23\23\1\111\2\23"+
    "\20\0\2\23\3\0\2\23\2\0\1\23\1\112\24\23"+
    "\20\0\2\23\3\0\2\23\2\0\20\23\1\113\5\23"+
    "\20\0\2\23\3\0\2\23\2\0\1\114\25\23\20\0"+
    "\2\23\3\0\2\23\2\0\10\23\1\115\15\23\20\0"+
    "\2\23\3\0\2\23\2\0\21\23\1\116\4\23\20\0"+
    "\2\23\3\0\2\23\2\0\10\23\1\117\15\23\20\0"+
    "\2\23\3\0\2\23\2\0\10\23\1\120\15\23\2\0"+
    "\10\75\1\121\46\75\10\0\1\76\4\0\1\2\57\0"+
    "\2\23\3\0\2\23\2\0\10\23\1\122\15\23\20\0"+
    "\2\23\3\0\2\23\2\0\20\23\1\123\5\23\20\0"+
    "\2\23\3\0\2\23\2\0\11\23\1\124\14\23\20\0"+
    "\2\23\3\0\2\23\2\0\17\23\1\125\6\23\20\0"+
    "\2\23\3\0\2\23\2\0\4\23\1\126\21\23\20\0"+
    "\2\23\3\0\2\23\2\0\4\23\1\127\21\23\20\0"+
    "\2\23\3\0\2\23\2\0\17\23\1\130\6\23\20\0"+
    "\2\23\3\0\2\23\2\0\6\23\1\131\17\23\20\0"+
    "\2\23\3\0\2\23\2\0\13\23\1\132\12\23\20\0"+
    "\2\23\3\0\2\23\2\0\11\23\1\133\14\23\20\0"+
    "\2\23\3\0\2\23\2\0\21\23\1\134\4\23\20\0"+
    "\2\23\3\0\2\23\2\0\20\23\1\135\5\23\20\0"+
    "\2\23\3\0\2\23\2\0\17\23\1\136\6\23\20\0"+
    "\2\23\3\0\2\23\2\0\4\23\1\137\21\23\20\0"+
    "\2\23\3\0\2\23\2\0\3\23\1\140\22\23\20\0"+
    "\2\23\3\0\2\23\2\0\11\23\1\141\14\23\2\0"+
    "\10\75\1\121\4\75\1\2\41\75\16\0\2\23\3\0"+
    "\2\23\2\0\13\23\1\142\12\23\20\0\2\23\3\0"+
    "\2\23\2\0\4\23\1\143\21\23\20\0\2\23\3\0"+
    "\2\23\2\0\4\23\1\144\21\23\20\0\2\23\3\0"+
    "\2\23\2\0\17\23\1\145\6\23\20\0\2\23\3\0"+
    "\2\23\2\0\13\23\1\146\12\23\20\0\2\23\3\0"+
    "\2\23\2\0\4\23\1\147\21\23\20\0\2\23\3\0"+
    "\2\23\2\0\20\23\1\150\5\23\20\0\2\23\3\0"+
    "\2\23\2\0\10\23\1\151\15\23\20\0\2\23\3\0"+
    "\2\23\2\0\16\23\1\152\7\23\20\0\2\23\3\0"+
    "\2\23\2\0\10\23\1\153\15\23\20\0\2\23\3\0"+
    "\2\23\2\0\4\23\1\154\21\23\20\0\2\23\3\0"+
    "\2\23\2\0\6\23\1\155\17\23\20\0\2\23\3\0"+
    "\2\23\2\0\12\23\1\156\13\23\20\0\2\23\3\0"+
    "\2\23\2\0\1\157\25\23\20\0\2\23\3\0\2\23"+
    "\2\0\3\23\1\160\22\23\20\0\2\23\3\0\2\23"+
    "\2\0\7\23\1\161\16\23\20\0\2\23\3\0\2\23"+
    "\2\0\2\23\1\162\23\23\20\0\2\23\3\0\2\23"+
    "\2\0\13\23\1\163\12\23\20\0\2\23\3\0\2\23"+
    "\2\0\2\23\1\164\23\23\16\0\1\165\1\0\2\23"+
    "\3\0\2\23\2\0\26\23\20\0\2\23\3\0\2\23"+
    "\2\0\13\23\1\166\12\23\20\0\2\23\3\0\2\23"+
    "\2\0\17\23\1\167\6\23\45\0\1\170\63\0\1\171"+
    "\55\0\1\172\23\0\1\173\106\0\1\174\57\0\1\175"+
    "\50\0\1\176\61\0\1\177\63\0\1\200\47\0\1\201"+
    "\60\0\1\202\14\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4418];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\1\1\1\11\1\0\7\11\1\0\1\11"+
    "\1\1\3\11\2\1\2\11\16\1\3\11\1\0\24\1"+
    "\2\0\22\1\1\0\43\1\1\0\2\1\12\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[130];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;

  /* user code: */
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


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
  yyclose();    }
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  @Override  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
            switch (zzLexicalState) {
            case YYINITIAL: {
              return symbol(sym.EOF);
            }  // fall though
            case 131: break;
            default:
          { return new java_cup.runtime.Symbol(sym.EOF); }
        }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { /* just skip what was found, do nothing */
            }
            // fall through
          case 39: break;
          case 2:
            { return symbol(sym.EXCLAMATION);
            }
            // fall through
          case 40: break;
          case 3:
            { return symbol(sym.LPAREN);
            }
            // fall through
          case 41: break;
          case 4:
            { return symbol(sym.RPAREN);
            }
            // fall through
          case 42: break;
          case 5:
            { return symbol(sym.TIMES);
            }
            // fall through
          case 43: break;
          case 6:
            { return symbol(sym.PLUS);
            }
            // fall through
          case 44: break;
          case 7:
            { return symbol(sym.COMMA);
            }
            // fall through
          case 45: break;
          case 8:
            { return symbol(sym.MINUS);
            }
            // fall through
          case 46: break;
          case 9:
            { return symbol(sym.PERIOD);
            }
            // fall through
          case 47: break;
          case 10:
            { return symbol(sym.NUMBER, new Integer(yytext()));
            }
            // fall through
          case 48: break;
          case 11:
            { return symbol(sym.SEMICOLON);
            }
            // fall through
          case 49: break;
          case 12:
            { return symbol(sym.LESSTHAN);
            }
            // fall through
          case 50: break;
          case 13:
            { return symbol(sym.EQUALS);
            }
            // fall through
          case 51: break;
          case 14:
            { return symbol(sym.IDENTIFIER, yytext());
            }
            // fall through
          case 52: break;
          case 15:
            { return symbol(sym.LEFTBRACKET);
            }
            // fall through
          case 53: break;
          case 16:
            { return symbol(sym.RIGHTBRACKET);
            }
            // fall through
          case 54: break;
          case 17:
            { return symbol(sym.LCURLYBRACKET);
            }
            // fall through
          case 55: break;
          case 18:
            { return symbol(sym.RCURLYBRACKET);
            }
            // fall through
          case 56: break;
          case 19:
            { return symbol(sym.AND);
            }
            // fall through
          case 57: break;
          case 20:
            { return symbol(sym.IF);
            }
            // fall through
          case 58: break;
          case 21:
            { return symbol(sym.INT);
            }
            // fall through
          case 59: break;
          case 22:
            { return symbol(sym.NEW);
            }
            // fall through
          case 60: break;
          case 23:
            { return symbol(sym.ELSE);
            }
            // fall through
          case 61: break;
          case 24:
            { return symbol(sym.MAIN);
            }
            // fall through
          case 62: break;
          case 25:
            { return symbol(sym.THIS);
            }
            // fall through
          case 63: break;
          case 26:
            { return symbol(sym.TRUE);
            }
            // fall through
          case 64: break;
          case 27:
            { return symbol(sym.VOID);
            }
            // fall through
          case 65: break;
          case 28:
            { return symbol(sym.CLASS);
            }
            // fall through
          case 66: break;
          case 29:
            { return symbol(sym.FALSE);
            }
            // fall through
          case 67: break;
          case 30:
            { return symbol(sym.WHILE);
            }
            // fall through
          case 68: break;
          case 31:
            { return symbol(sym.STRING);
            }
            // fall through
          case 69: break;
          case 32:
            { return symbol(sym.LENGTH);
            }
            // fall through
          case 70: break;
          case 33:
            { return symbol(sym.PUBLIC);
            }
            // fall through
          case 71: break;
          case 34:
            { return symbol(sym.RETURN);
            }
            // fall through
          case 72: break;
          case 35:
            { return symbol(sym.STATIC);
            }
            // fall through
          case 73: break;
          case 36:
            { return symbol(sym.BOOLEAN);
            }
            // fall through
          case 74: break;
          case 37:
            { return symbol(sym.EXTENDS);
            }
            // fall through
          case 75: break;
          case 38:
            { return symbol(sym.PRINTLN);
            }
            // fall through
          case 76: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
