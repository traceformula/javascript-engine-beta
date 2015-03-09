// Generated from Tscript.g by ANTLR 4.4

  package ts.parse;
  import ts.Location;
  import ts.tree.*;
  import static ts.parse.TreeBuilder.*;
  import java.util.List;
  import java.util.ArrayList;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TscriptLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NUMERIC_LITERAL=1, STRING_LITERAL=2, BOOLEAN_LITERAL=3, NULL_LITERAL=4, 
		LPAREN=5, RPAREN=6, SEMICOLON=7, COLON=8, EQUAL=9, PLUS=10, MINUS=11, 
		ASTERISK=12, SLASH=13, EXCLAMATIONMARK=14, EQUALS=15, GREATER=16, LESS=17, 
		LBRACE=18, RBRACE=19, COMMA=20, BITAND=21, BITXOR=22, BITOR=23, LOGICALAND=24, 
		LOGICALOR=25, CONDQUESTION=26, POFIX=27, PRINT=28, VAR=29, DO=30, WHILE=31, 
		FOR=32, IF=33, ELSE=34, IN=35, BREAK=36, CONTINUE=37, THROW=38, TRY=39, 
		CATCH=40, FINALLY=41, NEW=42, RETURN=43, FUNCTION=44, TRUE=45, FALSE=46, 
		NULL=47, IDENTIFIER=48, WhiteSpace=49;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'", 
		"'\\u001F'", "' '", "'!'", "'\"'", "'#'", "'$'", "'%'", "'&'", "'''", 
		"'('", "')'", "'*'", "'+'", "','", "'-'", "'.'", "'/'", "'0'", "'1'"
	};
	public static final String[] ruleNames = {
		"DIGIT", "SignedInteger", "IdentifierCharacters", "SpaceTokens", "SpaceChars", 
		"EndOfLineComment", "LineTerminator", "DecimalLiteral", "DecimalIntegerLiteral", 
		"ExponentPart", "HexIntegerLiteral", "HexDigit", "DoubleStringCharacter", 
		"SingleStringCharacter", "NUMERIC_LITERAL", "STRING_LITERAL", "BOOLEAN_LITERAL", 
		"NULL_LITERAL", "LPAREN", "RPAREN", "SEMICOLON", "COLON", "EQUAL", "PLUS", 
		"MINUS", "ASTERISK", "SLASH", "EXCLAMATIONMARK", "EQUALS", "GREATER", 
		"LESS", "LBRACE", "RBRACE", "COMMA", "BITAND", "BITXOR", "BITOR", "LOGICALAND", 
		"LOGICALOR", "CONDQUESTION", "POFIX", "PRINT", "VAR", "DO", "WHILE", "FOR", 
		"IF", "ELSE", "IN", "BREAK", "CONTINUE", "THROW", "TRY", "CATCH", "FINALLY", 
		"NEW", "RETURN", "FUNCTION", "TRUE", "FALSE", "NULL", "IDENTIFIER", "WhiteSpace"
	};


	  // grab location info (filename/line/column) from token
	  // in order to stick into AST nodes for later error reporting
	  public Location loc(final Token token)
	  {
	    return new Location(getSourceName(), token.getLine(),
	      token.getCharPositionInLine());
	  }

	  // a program is a list of statements
	  // i.e. root of AST is stored here
	  // set by the action for the start symbol
	  private List<Statement> semanticValue;
	  public List<Statement> getSemanticValue()
	  {
	    return semanticValue;
	  }


	public TscriptLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Tscript.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\63\u01b1\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\3\2\3\2\3\3\6\3\u0085\n\3\r\3\16\3\u0086\3\3\3\3\6"+
		"\3\u008b\n\3\r\3\16\3\u008c\5\3\u008f\n\3\3\4\3\4\7\4\u0093\n\4\f\4\16"+
		"\4\u0096\13\4\3\5\3\5\3\5\5\5\u009b\n\5\3\6\3\6\3\7\3\7\3\7\3\7\7\7\u00a3"+
		"\n\7\f\7\16\7\u00a6\13\7\3\7\3\7\5\7\u00aa\n\7\3\b\3\b\3\b\5\b\u00af\n"+
		"\b\3\t\3\t\3\t\7\t\u00b4\n\t\f\t\16\t\u00b7\13\t\3\t\5\t\u00ba\n\t\3\t"+
		"\3\t\6\t\u00be\n\t\r\t\16\t\u00bf\3\t\5\t\u00c3\n\t\3\t\3\t\5\t\u00c7"+
		"\n\t\5\t\u00c9\n\t\3\n\3\n\7\n\u00cd\n\n\f\n\16\n\u00d0\13\n\3\n\5\n\u00d3"+
		"\n\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\6\f\u00dc\n\f\r\f\16\f\u00dd\3\f\3"+
		"\f\3\f\3\f\6\f\u00e4\n\f\r\f\16\f\u00e5\5\f\u00e8\n\f\3\r\3\r\3\16\3\16"+
		"\3\17\3\17\3\20\3\20\5\20\u00f2\n\20\3\21\3\21\7\21\u00f6\n\21\f\21\16"+
		"\21\u00f9\13\21\3\21\3\21\3\21\7\21\u00fe\n\21\f\21\16\21\u0101\13\21"+
		"\3\21\5\21\u0104\n\21\3\22\3\22\5\22\u0108\n\22\3\23\3\23\3\24\3\24\3"+
		"\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3"+
		"\34\3\34\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#"+
		"\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3*\3+\3+\3+\3+\3+\3+\3,\3"+
		",\3,\3,\3-\3-\3-\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3\60\3\60\3\60\3\61\3\61"+
		"\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\66"+
		"\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\38\38\38\38\38\38\38\38"+
		"\39\39\39\39\3:\3:\3:\3:\3:\3:\3:\3;\3;\3;\3;\3;\3;\3;\3;\3;\3<\3<\3<"+
		"\3<\3<\3=\3=\3=\3=\3=\3=\3>\3>\3>\3>\3>\3?\3?\3@\6@\u01ac\n@\r@\16@\u01ad"+
		"\3@\3@\2\2A\3\2\5\2\7\2\t\2\13\2\r\2\17\2\21\2\23\2\25\2\27\2\31\2\33"+
		"\2\35\2\37\3!\4#\5%\6\'\7)\b+\t-\n/\13\61\f\63\r\65\16\67\179\20;\21="+
		"\22?\23A\24C\25E\26G\27I\30K\31M\32O\33Q\34S\35U\36W\37Y [!]\"_#a$c%e"+
		"&g\'i(k)m*o+q,s-u.w/y\60{\61}\62\177\63\3\2#\3\2\62;\4\2--//\6\2&&C\\"+
		"aac|\7\2&&\62;C\\aac|\5\2\13\13\16\16\"\"\4\2\f\f\17\17\3\2\63;\3\2\62"+
		"\62\4\2GGgg\5\2\62;CHch\5\2\f\f\17\17$$\5\2\f\f\17\17))\3\2$$\3\2))\3"+
		"\2**\3\2++\3\2==\3\2<<\3\2??\3\2--\3\2//\3\2,,\3\2\61\61\3\2##\3\2@@\3"+
		"\2>>\3\2}}\3\2\177\177\3\2..\3\2((\3\2``\3\2~~\3\2AA\u01bd\2\37\3\2\2"+
		"\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2"+
		"\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2"+
		"\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2"+
		"\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q"+
		"\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2"+
		"\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2"+
		"\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w"+
		"\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\3\u0081\3\2\2"+
		"\2\5\u008e\3\2\2\2\7\u0090\3\2\2\2\t\u009a\3\2\2\2\13\u009c\3\2\2\2\r"+
		"\u009e\3\2\2\2\17\u00ae\3\2\2\2\21\u00c8\3\2\2\2\23\u00d2\3\2\2\2\25\u00d4"+
		"\3\2\2\2\27\u00e7\3\2\2\2\31\u00e9\3\2\2\2\33\u00eb\3\2\2\2\35\u00ed\3"+
		"\2\2\2\37\u00f1\3\2\2\2!\u0103\3\2\2\2#\u0107\3\2\2\2%\u0109\3\2\2\2\'"+
		"\u010b\3\2\2\2)\u010d\3\2\2\2+\u010f\3\2\2\2-\u0111\3\2\2\2/\u0113\3\2"+
		"\2\2\61\u0115\3\2\2\2\63\u0117\3\2\2\2\65\u0119\3\2\2\2\67\u011b\3\2\2"+
		"\29\u011d\3\2\2\2;\u011f\3\2\2\2=\u0122\3\2\2\2?\u0124\3\2\2\2A\u0126"+
		"\3\2\2\2C\u0128\3\2\2\2E\u012a\3\2\2\2G\u012c\3\2\2\2I\u012e\3\2\2\2K"+
		"\u0130\3\2\2\2M\u0132\3\2\2\2O\u0134\3\2\2\2Q\u0136\3\2\2\2S\u0138\3\2"+
		"\2\2U\u013b\3\2\2\2W\u0141\3\2\2\2Y\u0145\3\2\2\2[\u0148\3\2\2\2]\u014e"+
		"\3\2\2\2_\u0152\3\2\2\2a\u0155\3\2\2\2c\u015a\3\2\2\2e\u015d\3\2\2\2g"+
		"\u0163\3\2\2\2i\u016c\3\2\2\2k\u0172\3\2\2\2m\u0176\3\2\2\2o\u017c\3\2"+
		"\2\2q\u0184\3\2\2\2s\u0188\3\2\2\2u\u018f\3\2\2\2w\u0198\3\2\2\2y\u019d"+
		"\3\2\2\2{\u01a3\3\2\2\2}\u01a8\3\2\2\2\177\u01ab\3\2\2\2\u0081\u0082\t"+
		"\2\2\2\u0082\4\3\2\2\2\u0083\u0085\t\2\2\2\u0084\u0083\3\2\2\2\u0085\u0086"+
		"\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u008f\3\2\2\2\u0088"+
		"\u008a\t\3\2\2\u0089\u008b\t\2\2\2\u008a\u0089\3\2\2\2\u008b\u008c\3\2"+
		"\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008f\3\2\2\2\u008e"+
		"\u0084\3\2\2\2\u008e\u0088\3\2\2\2\u008f\6\3\2\2\2\u0090\u0094\t\4\2\2"+
		"\u0091\u0093\t\5\2\2\u0092\u0091\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0092"+
		"\3\2\2\2\u0094\u0095\3\2\2\2\u0095\b\3\2\2\2\u0096\u0094\3\2\2\2\u0097"+
		"\u009b\5\13\6\2\u0098\u009b\5\17\b\2\u0099\u009b\5\r\7\2\u009a\u0097\3"+
		"\2\2\2\u009a\u0098\3\2\2\2\u009a\u0099\3\2\2\2\u009b\n\3\2\2\2\u009c\u009d"+
		"\t\6\2\2\u009d\f\3\2\2\2\u009e\u009f\7\61\2\2\u009f\u00a0\7\61\2\2\u00a0"+
		"\u00a4\3\2\2\2\u00a1\u00a3\n\7\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a6\3\2"+
		"\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a9\3\2\2\2\u00a6"+
		"\u00a4\3\2\2\2\u00a7\u00aa\5\17\b\2\u00a8\u00aa\7\2\2\3\u00a9\u00a7\3"+
		"\2\2\2\u00a9\u00a8\3\2\2\2\u00aa\16\3\2\2\2\u00ab\u00ac\7\17\2\2\u00ac"+
		"\u00af\7\f\2\2\u00ad\u00af\t\7\2\2\u00ae\u00ab\3\2\2\2\u00ae\u00ad\3\2"+
		"\2\2\u00af\20\3\2\2\2\u00b0\u00b1\5\23\n\2\u00b1\u00b5\7\60\2\2\u00b2"+
		"\u00b4\5\3\2\2\u00b3\u00b2\3\2\2\2\u00b4\u00b7\3\2\2\2\u00b5\u00b3\3\2"+
		"\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b9\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b8"+
		"\u00ba\5\25\13\2\u00b9\u00b8\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00c9\3"+
		"\2\2\2\u00bb\u00bd\7\60\2\2\u00bc\u00be\5\3\2\2\u00bd\u00bc\3\2\2\2\u00be"+
		"\u00bf\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c2\3\2"+
		"\2\2\u00c1\u00c3\5\25\13\2\u00c2\u00c1\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3"+
		"\u00c9\3\2\2\2\u00c4\u00c6\5\23\n\2\u00c5\u00c7\5\25\13\2\u00c6\u00c5"+
		"\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c9\3\2\2\2\u00c8\u00b0\3\2\2\2\u00c8"+
		"\u00bb\3\2\2\2\u00c8\u00c4\3\2\2\2\u00c9\22\3\2\2\2\u00ca\u00ce\t\b\2"+
		"\2\u00cb\u00cd\5\3\2\2\u00cc\u00cb\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc"+
		"\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d3\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d1"+
		"\u00d3\t\t\2\2\u00d2\u00ca\3\2\2\2\u00d2\u00d1\3\2\2\2\u00d3\24\3\2\2"+
		"\2\u00d4\u00d5\t\n\2\2\u00d5\u00d6\5\5\3\2\u00d6\26\3\2\2\2\u00d7\u00d8"+
		"\7\62\2\2\u00d8\u00d9\7Z\2\2\u00d9\u00db\3\2\2\2\u00da\u00dc\5\31\r\2"+
		"\u00db\u00da\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de"+
		"\3\2\2\2\u00de\u00e8\3\2\2\2\u00df\u00e0\7\62\2\2\u00e0\u00e1\7z\2\2\u00e1"+
		"\u00e3\3\2\2\2\u00e2\u00e4\5\31\r\2\u00e3\u00e2\3\2\2\2\u00e4\u00e5\3"+
		"\2\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e8\3\2\2\2\u00e7"+
		"\u00d7\3\2\2\2\u00e7\u00df\3\2\2\2\u00e8\30\3\2\2\2\u00e9\u00ea\t\13\2"+
		"\2\u00ea\32\3\2\2\2\u00eb\u00ec\n\f\2\2\u00ec\34\3\2\2\2\u00ed\u00ee\n"+
		"\r\2\2\u00ee\36\3\2\2\2\u00ef\u00f2\5\21\t\2\u00f0\u00f2\5\27\f\2\u00f1"+
		"\u00ef\3\2\2\2\u00f1\u00f0\3\2\2\2\u00f2 \3\2\2\2\u00f3\u00f7\t\16\2\2"+
		"\u00f4\u00f6\n\f\2\2\u00f5\u00f4\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5"+
		"\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00fa\3\2\2\2\u00f9\u00f7\3\2\2\2\u00fa"+
		"\u0104\t\16\2\2\u00fb\u00ff\t\17\2\2\u00fc\u00fe\n\r\2\2\u00fd\u00fc\3"+
		"\2\2\2\u00fe\u0101\3\2\2\2\u00ff\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100"+
		"\u0102\3\2\2\2\u0101\u00ff\3\2\2\2\u0102\u0104\t\17\2\2\u0103\u00f3\3"+
		"\2\2\2\u0103\u00fb\3\2\2\2\u0104\"\3\2\2\2\u0105\u0108\5w<\2\u0106\u0108"+
		"\5y=\2\u0107\u0105\3\2\2\2\u0107\u0106\3\2\2\2\u0108$\3\2\2\2\u0109\u010a"+
		"\5{>\2\u010a&\3\2\2\2\u010b\u010c\t\20\2\2\u010c(\3\2\2\2\u010d\u010e"+
		"\t\21\2\2\u010e*\3\2\2\2\u010f\u0110\t\22\2\2\u0110,\3\2\2\2\u0111\u0112"+
		"\t\23\2\2\u0112.\3\2\2\2\u0113\u0114\t\24\2\2\u0114\60\3\2\2\2\u0115\u0116"+
		"\t\25\2\2\u0116\62\3\2\2\2\u0117\u0118\t\26\2\2\u0118\64\3\2\2\2\u0119"+
		"\u011a\t\27\2\2\u011a\66\3\2\2\2\u011b\u011c\t\30\2\2\u011c8\3\2\2\2\u011d"+
		"\u011e\t\31\2\2\u011e:\3\2\2\2\u011f\u0120\t\24\2\2\u0120\u0121\t\24\2"+
		"\2\u0121<\3\2\2\2\u0122\u0123\t\32\2\2\u0123>\3\2\2\2\u0124\u0125\t\33"+
		"\2\2\u0125@\3\2\2\2\u0126\u0127\t\34\2\2\u0127B\3\2\2\2\u0128\u0129\t"+
		"\35\2\2\u0129D\3\2\2\2\u012a\u012b\t\36\2\2\u012bF\3\2\2\2\u012c\u012d"+
		"\t\37\2\2\u012dH\3\2\2\2\u012e\u012f\t \2\2\u012fJ\3\2\2\2\u0130\u0131"+
		"\t!\2\2\u0131L\3\2\2\2\u0132\u0133\t\37\2\2\u0133N\3\2\2\2\u0134\u0135"+
		"\t!\2\2\u0135P\3\2\2\2\u0136\u0137\t\"\2\2\u0137R\3\2\2\2\u0138\u0139"+
		"\t\25\2\2\u0139\u013a\t\25\2\2\u013aT\3\2\2\2\u013b\u013c\7r\2\2\u013c"+
		"\u013d\7t\2\2\u013d\u013e\7k\2\2\u013e\u013f\7p\2\2\u013f\u0140\7v\2\2"+
		"\u0140V\3\2\2\2\u0141\u0142\7x\2\2\u0142\u0143\7c\2\2\u0143\u0144\7t\2"+
		"\2\u0144X\3\2\2\2\u0145\u0146\7f\2\2\u0146\u0147\7q\2\2\u0147Z\3\2\2\2"+
		"\u0148\u0149\7y\2\2\u0149\u014a\7j\2\2\u014a\u014b\7k\2\2\u014b\u014c"+
		"\7n\2\2\u014c\u014d\7g\2\2\u014d\\\3\2\2\2\u014e\u014f\7h\2\2\u014f\u0150"+
		"\7q\2\2\u0150\u0151\7t\2\2\u0151^\3\2\2\2\u0152\u0153\7k\2\2\u0153\u0154"+
		"\7h\2\2\u0154`\3\2\2\2\u0155\u0156\7g\2\2\u0156\u0157\7n\2\2\u0157\u0158"+
		"\7u\2\2\u0158\u0159\7g\2\2\u0159b\3\2\2\2\u015a\u015b\7k\2\2\u015b\u015c"+
		"\7p\2\2\u015cd\3\2\2\2\u015d\u015e\7d\2\2\u015e\u015f\7t\2\2\u015f\u0160"+
		"\7g\2\2\u0160\u0161\7c\2\2\u0161\u0162\7m\2\2\u0162f\3\2\2\2\u0163\u0164"+
		"\7e\2\2\u0164\u0165\7q\2\2\u0165\u0166\7p\2\2\u0166\u0167\7v\2\2\u0167"+
		"\u0168\7k\2\2\u0168\u0169\7p\2\2\u0169\u016a\7w\2\2\u016a\u016b\7g\2\2"+
		"\u016bh\3\2\2\2\u016c\u016d\7v\2\2\u016d\u016e\7j\2\2\u016e\u016f\7t\2"+
		"\2\u016f\u0170\7q\2\2\u0170\u0171\7y\2\2\u0171j\3\2\2\2\u0172\u0173\7"+
		"v\2\2\u0173\u0174\7t\2\2\u0174\u0175\7{\2\2\u0175l\3\2\2\2\u0176\u0177"+
		"\7e\2\2\u0177\u0178\7c\2\2\u0178\u0179\7v\2\2\u0179\u017a\7e\2\2\u017a"+
		"\u017b\7j\2\2\u017bn\3\2\2\2\u017c\u017d\7h\2\2\u017d\u017e\7k\2\2\u017e"+
		"\u017f\7p\2\2\u017f\u0180\7c\2\2\u0180\u0181\7n\2\2\u0181\u0182\7n\2\2"+
		"\u0182\u0183\7{\2\2\u0183p\3\2\2\2\u0184\u0185\7p\2\2\u0185\u0186\7g\2"+
		"\2\u0186\u0187\7y\2\2\u0187r\3\2\2\2\u0188\u0189\7t\2\2\u0189\u018a\7"+
		"g\2\2\u018a\u018b\7v\2\2\u018b\u018c\7w\2\2\u018c\u018d\7t\2\2\u018d\u018e"+
		"\7p\2\2\u018et\3\2\2\2\u018f\u0190\7h\2\2\u0190\u0191\7w\2\2\u0191\u0192"+
		"\7p\2\2\u0192\u0193\7e\2\2\u0193\u0194\7v\2\2\u0194\u0195\7k\2\2\u0195"+
		"\u0196\7q\2\2\u0196\u0197\7p\2\2\u0197v\3\2\2\2\u0198\u0199\7v\2\2\u0199"+
		"\u019a\7t\2\2\u019a\u019b\7w\2\2\u019b\u019c\7g\2\2\u019cx\3\2\2\2\u019d"+
		"\u019e\7h\2\2\u019e\u019f\7c\2\2\u019f\u01a0\7n\2\2\u01a0\u01a1\7u\2\2"+
		"\u01a1\u01a2\7g\2\2\u01a2z\3\2\2\2\u01a3\u01a4\7p\2\2\u01a4\u01a5\7w\2"+
		"\2\u01a5\u01a6\7n\2\2\u01a6\u01a7\7n\2\2\u01a7|\3\2\2\2\u01a8\u01a9\5"+
		"\7\4\2\u01a9~\3\2\2\2\u01aa\u01ac\5\t\5\2\u01ab\u01aa\3\2\2\2\u01ac\u01ad"+
		"\3\2\2\2\u01ad\u01ab\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01af\3\2\2\2\u01af"+
		"\u01b0\b@\2\2\u01b0\u0080\3\2\2\2\34\2\u0086\u008c\u008e\u0094\u009a\u00a4"+
		"\u00a9\u00ae\u00b5\u00b9\u00bf\u00c2\u00c6\u00c8\u00ce\u00d2\u00dd\u00e5"+
		"\u00e7\u00f1\u00f7\u00ff\u0103\u0107\u01ad\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}