//
// an ANTLR parser specification for a Tscript subset
//

grammar Tscript;

@header {
  package ts.parse;
  import ts.Location;
  import ts.tree.*;
  import static ts.parse.TreeBuilder.*;
  import java.util.List;
  import java.util.ArrayList;
}

@members {
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
}

// grammar proper

program
  : sl=statementList EOF
    { semanticValue = $sl.lval; }
  ;

statementList
  returns [ List<Statement> lval ]
  : // empty rule
    { $lval = new ArrayList<Statement>(); }
  | sl=statementList s=statement
    { $sl.lval.add($s.lval);
      $lval = $sl.lval; }
  ;

statement
  returns [ Statement lval ]
  : v=varStatement
    { $lval = $v.lval; }
  | e=expressionStatement
    { $lval = $e.lval; }
  | p=printStatement
    { $lval = $p.lval; }
  | b = block
    { $lval = $b.lval; }
  | es = emptyStatement
  { $lval = $es.lval;  }
  | i = iterationStatement
  { $lval = $i.lval;}
  | is = ifStatement
  {$lval = $is.lval;}
  |br = breakStatement
  {$lval = $br.lval;}
  |cs = continueStatement
  {$lval = $cs.lval;}
  |ls = labelledStatement
  {$lval = $ls.lval;}
  |ts = throwStatement
  {$lval = $ts.lval;}
  |trys = tryStatement
  {$lval = $trys.lval;}
  | rets = returnStatement
  {$lval = $rets.lval;}
  ;

  returnStatement
  returns [Statement lval]
    : RETURN SEMICOLON
    {$lval = buildReturnStatement(loc($start),null);}
    |
    RETURN e=expression SEMICOLON
    {$lval = buildReturnStatement(loc($start),$e.lval);}
  ;
  
tryStatement
  returns [Statement lval]
  : TRY b = block c = catchStatment
  {$lval = buildTryStatement(loc($start),$b.lval,$c.lval,null );}
  | TRY b = block f = finallyStatement
  {$lval = buildTryStatement(loc($start),$b.lval,null,$f.lval );}
  | TRY b = block c = catchStatment f = finallyStatement
  {$lval = buildTryStatement(loc($start),$b.lval,$c.lval,$f.lval );}
  ;
  
catchStatment
  returns [Statement lval]
  : CATCH LPAREN IDENTIFIER RPAREN b = block 
  {$lval = buildCatchStatement(loc($start),$IDENTIFIER.text,$b.lval );}
  ;
  
finallyStatement
  returns [Statement lval]
  : FINALLY b = block 
  {$lval = buildFinallyStatement(loc($start),$b.lval );}
  ;
  
throwStatement
  returns [Statement lval]
  : THROW e = expression SEMICOLON
  {$lval = buildThrowStatement(loc($start),$e.lval );}
  ;
  
labelledStatement
  returns [Statement lval]
  : IDENTIFIER COLON  s = statement
    {$lval = buildLabelStatement(loc($start),$IDENTIFIER.text, $s.lval ); }
;
  
continueStatement
  returns [Statement lval]
  : CONTINUE SEMICOLON
  {$lval = buildContinueStatement(loc($start),null ); }
  | CONTINUE IDENTIFIER SEMICOLON
  {$lval = buildContinueStatement(loc($start),$IDENTIFIER.text ); }
  ; 
  
breakStatement
  returns [Statement lval]
  : BREAK SEMICOLON
  {$lval = buildBreakStatement(loc($start),null ); }
   | BREAK IDENTIFIER SEMICOLON
  {$lval = buildBreakStatement(loc($start),$IDENTIFIER.text ); }
  ;  
  
ifStatement
  returns [Statement lval]
  : IF LPAREN e= expression RPAREN is = statement ELSE es =  statement
  {$lval = buildIfStatement(loc($start),$e.lval,$is.lval,$es.lval );}
  | IF LPAREN e= expression RPAREN is = statement
   {$lval = buildIfStatement(loc($start),$e.lval,$is.lval,null );}
   ;
  
  
iterationStatement
   returns [Statement lval]
   : WHILE LPAREN e = expression RPAREN s = statement
    {$lval = buildWhileStatement(loc($start),$e.lval,$s.lval ); }
     ;
  
emptyStatement
   returns [Statement lval]
   : SEMICOLON
    { $lval = buildEmptyStatement(loc($start) ); }
   ;

block
   returns [Statement lval]
   :
   LBRACE sll = statementList RBRACE
   {$lval = buildBlock(loc($start),$sll.lval);}
   ;
varStatement
  returns [ Statement lval ]
  : VAR v=variableDeclarationList SEMICOLON
    { $lval = $v.lval; }
  ;

variableDeclarationList
  returns [ VarStatement lval ]
  : v=variableDeclaration 
    {$lval = addVarDeclaration($lval, $v.lval); }
  | l=variableDeclarationList COMMA r=variableDeclaration
    { $lval = addVarDeclaration($lval, $l.lval);
      $lval = addVarDeclaration($lval, $r.lval);}
  ;

variableDeclaration
  returns [ VarStatement lval ]
 : IDENTIFIER
  { $lval = (VarStatement) buildVarStatement(loc($start), $IDENTIFIER.text); }
 |IDENTIFIER i=initialiser
  { $lval = (VarStatement) buildVarStatement(loc($start), $IDENTIFIER.text); 
    $lval.addExp(buildBinaryOperator(loc($start), Binop.ASSIGN,
      buildIdentifier(loc($start), $IDENTIFIER.text), $i.lval)) ; 
  }
 ;

initialiser
  returns [ Expression lval ]
  : EQUAL a=assignmentExpression
  { $lval = $a.lval; }
  ;

expressionStatement
  returns [ Statement lval ]
  : e=expression SEMICOLON
    { $lval = buildExpressionStatement(loc($start), $e.lval); }
  ;

printStatement
  returns [ Statement lval ]
  : PRINT e=expression SEMICOLON
    { $lval = buildPrintStatement(loc($start), $e.lval); }
  ;

expression
  returns [ Expression lval ]
  : a=assignmentExpression
    { $lval = $a.lval; }
  ;

assignmentExpression
  returns [ Expression lval ]
  : a=additiveExpression
    { $lval = $a.lval; }
  | co=conditionalExpression
    { $lval = $co.lval; }
  | l=leftHandSideExpression EQUAL r=assignmentExpression
    { checkAssignmentDestination(loc($start), $l.lval);
      $lval = buildBinaryOperator(loc($start), Binop.ASSIGN,
        $l.lval, $r.lval); }
  ;

equalityExpression
  returns [Expression lval]
  :r=relationalExpression
    {$lval = $r.lval; }
  |e=equalityExpression EQUALS r=relationalExpression 
    { $lval = buildBinaryOperator(loc($start), Binop.EQ,
      $e.lval, $r.lval); }
  ;

relationalExpression
  returns [Expression lval]
  :s=shiftExpression
    {$lval = $s.lval;}
  |r=relationalExpression LESS s=shiftExpression
    {$lval = buildBinaryOperator(loc($start), Binop.LT,
      $r.lval, $s.lval); }
  |r=relationalExpression GREATER s=shiftExpression
    {$lval = buildBinaryOperator(loc($start), Binop.GT,
      $r.lval, $s.lval); }
  ;

shiftExpression
  returns [Expression lval]
  :a=additiveExpression
    {$lval = $a.lval; }
  ;

unaryExpression
  returns [Expression lval]
  : EXCLAMATIONMARK v=unaryExpression
    { $lval = buildUnaryOperator(loc($start), Unop.NOT,
      $v.lval); }
  | MINUS v=unaryExpression
    { $lval = buildUnaryOperator(loc($start), Unop.NEG,
      $v.lval); }
  |p=postFixExpression
    { $lval = $p.lval; }
  ;

postFixExpression
  returns [Expression lval]
  :le=leftHandSideExpression
   { $lval = $le.lval; }
  ;

leftHandSideExpression
  returns [ Expression lval ]
  : n = newExpression
  {$lval = $n.lval;}
  |  c = callExpression
  {$lval = $c.lval;}
  ;

callExpression
returns [Expression lval]
: m = memberExpression a = argruments
{$lval = buildCallExpression(loc($start),$m.lval, $a.lval,null);}
|  c = callExpression a = argruments
{$lval = buildCallExpression(loc($start),null, $a.lval,$c.lval);}
;

argruments
returns[List<Expression> lval]
: LPAREN RPAREN
{$lval = new ArrayList<Expression>();}
| LPAREN a = argumentList RPAREN
{$lval = $a.lval;}
;
 
argumentList
returns [List<Expression> lval] 
: a=assignmentExpression
{$lval = new java.util.ArrayList<Expression>();
     $lval.add($a.lval);}
 | al = argumentList COMMA a = assignmentExpression
  {
    if($al.lval==null)
    {
    $lval = new java.util.ArrayList<Expression>();
    }
     $al.lval.add($a.lval);
     $lval =  $al.lval;
 }
  ;

additiveExpression
  returns [ Expression lval ]
  : m=multiplicativeExpression
    { $lval = $m.lval; }
  | l=additiveExpression PLUS r=multiplicativeExpression
    { $lval = buildBinaryOperator(loc($start), Binop.ADD,
        $l.lval, $r.lval); }
  | l=additiveExpression MINUS r=multiplicativeExpression
    { $lval = buildBinaryOperator(loc($start), Binop.SUB,
        $l.lval, $r.lval); }
  ;

multiplicativeExpression
  returns [ Expression lval ]
  : p=primaryExpression
    { $lval = $p.lval; }
  | l=multiplicativeExpression ASTERISK r=unaryExpression
    { $lval = buildBinaryOperator(loc($start), Binop.MULTIPLY,
      $l.lval, $r.lval); }
  | l=multiplicativeExpression SLASH r=unaryExpression
    { $lval = buildBinaryOperator(loc($start), Binop.DIVIDE,
      $l.lval, $r.lval); }
  | u=unaryExpression
    { $lval = $u.lval;}
  ;

bitwiseANDExpression
  returns [ Expression lval ]
  :e=equalityExpression
  { $lval = $e.lval; }
 | l=bitwiseANDExpression BITAND r=equalityExpression
 { $lval = buildBinaryOperator(loc($start), Binop.BITAND,
        $l.lval, $r.lval); }
    ;
    
bitwiseXORExpression
  returns [ Expression lval ]
  :band=bitwiseANDExpression
  { $lval = $band.lval; }
 | l=bitwiseXORExpression BITXOR r=bitwiseANDExpression
 { $lval = buildBinaryOperator(loc($start), Binop.BITXOR,
      $l.lval, $r.lval); }
  ;
    
bitwiseORExpression
   returns [ Expression lval ]
  :bxor=bitwiseXORExpression
  { $lval = $bxor.lval; }
 | l=bitwiseORExpression BITOR r=bitwiseXORExpression
 { $lval = buildBinaryOperator(loc($start), Binop.BITOR,
      $l.lval, $r.lval); }
  ;
    
logicalANDExpression
 returns [ Expression lval ]
 :bor=bitwiseORExpression
 { $lval = $bor.lval; }
 | l=logicalANDExpression LOGICALAND r=bitwiseORExpression
 { $lval = buildBinaryOperator(loc($start), Binop.LOGICAL_AND,
    $l.lval, $r.lval); }
  ;


logicalORExpression
 returns [ Expression lval ]
 :land=logicalANDExpression
 { $lval = $land.lval; }
 | l=logicalORExpression LOGICALOR r=logicalANDExpression
 { $lval = buildBinaryOperator(loc($start), Binop.LOGICAL_OR,
    $l.lval, $r.lval); }
;

conditionalExpression
  returns [ Expression lval ]
  :lor=logicalORExpression
   { $lval = $lor.lval; }
   ;

primaryExpression
  returns [ Expression lval ]
  : IDENTIFIER
    { $lval = buildIdentifier(loc($start), $IDENTIFIER.text); }
  | NUMERIC_LITERAL
    { $lval = buildNumericLiteral(loc($start), $NUMERIC_LITERAL.text); }
  | BOOLEAN_LITERAL
    { $lval = buildBooleanLiteral(loc($start), $BOOLEAN_LITERAL.text); }
  | STRING_LITERAL
    { $lval = buildStringLiteral(loc($start), $STRING_LITERAL.text);}
  | NULL_LITERAL
    { $lval = buildNullLiteral(loc($start));}
  | LPAREN e=expression RPAREN
    { $lval = $e.lval; }
  ;

newExpression
 returns [ Expression lval ]
 : m = memberExpression
 {$lval = $m.lval;}
 | NEW n = newExpression
 {$lval = $n.lval;}
 ;
 
 memberExpression
  returns [ Expression lval ]
  :p=primaryExpression
   { $lval = $p.lval; }
   |f = functionExpression
    { $lval = $f.lval; }
    ;
    
 functionExpression
   returns [ Expression lval ]
  : FUNCTION LPAREN RPAREN LBRACE f = functionBody RBRACE 
  { $lval = buildFunctionExpression(loc($start), null,null, $f.lval); }
  | FUNCTION IDENTIFIER LPAREN RPAREN LBRACE f = functionBody RBRACE 
  { $lval = buildFunctionExpression(loc($start),$IDENTIFIER.text ,null, $f.lval); }
  | FUNCTION LPAREN fpl = formalParameterList RPAREN LBRACE f = functionBody RBRACE 
  { $lval = buildFunctionExpression(loc($start),null ,$fpl.lval, $f.lval); }
  | FUNCTION IDENTIFIER LPAREN fpl = formalParameterList RPAREN LBRACE f = functionBody RBRACE 
  { $lval = buildFunctionExpression(loc($start),$IDENTIFIER.text ,$fpl.lval, $f.lval); }
  ;
  
  formalParameterList
  returns [List<String> lval]
  : IDENTIFIER
  {$lval = new java.util.ArrayList<String>();
     $lval.add($IDENTIFIER.text);}
  | fpl = formalParameterList COMMA IDENTIFIER
  {
    if($fpl.lval==null)
    {
    $lval = new java.util.ArrayList<String>();
    }
     $fpl.lval.add($IDENTIFIER.text);
     $lval =  $fpl.lval;
    }
    
  ;
  
  functionBody 
  returns [Statement lval]
  : s = sourceElements
  {$lval = buildFunctionBody(loc($start),$s.lval);}

  ;
  
  sourceElements
  returns [ List<Statement> lval ]
  :s=sourceElement
    { $lval = new ArrayList<Statement>(); 
      $lval.add($s.lval);
    }
  | se=sourceElements s=sourceElement
    { $se.lval.add($s.lval);
      $lval = $se.lval; }
  ;
      
  sourceElement
  returns [Statement lval]
  : s = statement 
  {$lval = $s.lval;}
  ;


// fragments to support the lexer rules

fragment DIGIT : [0-9];
fragment SignedInteger
  : [0-9]+
  | [+-] [0-9]+
  ;
fragment IdentifierCharacters : [a-zA-Z_$] [a-zA-Z0-9_$]*;

fragment SpaceTokens : SpaceChars | LineTerminator | EndOfLineComment;

fragment SpaceChars : ' ' | '\t' | '\f';

fragment EndOfLineComment : '//' ( ~[\n\r] )* (LineTerminator | EOF);

fragment LineTerminator : '\r' '\n' | '\r' | '\n';

fragment DecimalLiteral
  : DecimalIntegerLiteral '.' DIGIT* ExponentPart?
 | '.' DIGIT+ ExponentPart?
 | DecimalIntegerLiteral ExponentPart?
 ;

fragment DecimalIntegerLiteral : ([1-9] DIGIT*) | [0] ;

fragment ExponentPart : [eE] SignedInteger;

fragment HexIntegerLiteral
 : '0X' HexDigit+
 | '0x' HexDigit+
 ;

fragment HexDigit : [0123456789abcdefAABCDEF] ;
fragment DoubleStringCharacter : ~[\"\n\r] ;
fragment SingleStringCharacter : ~[\'\n\r] ;
// lexer rules
//   keywords must appear before IDENTIFIER

// cannot have a leading 0 unless the literal is just 0
//NUMERIC_LITERAL : ([1-9] DIGIT*) | [0];
//NUMERIC_LITERAL : DECIMAL_LITERAL | HEXINTEGER_LITERAL;
//DECIMAL_LITERAL : [0-9]*([0-9]([uU](ll?+LL?)+(ll?+LL?)?[uU]?)+(\.[0-9]*)?([eE][+-]?[0-9]+)[fFlL]);
//HEXINTEGER_LITERAL : (?:[1-9]\d+|0|0[oO]?[0-7]+|0[xX][\da-fA-F]+|0[bB][01]+)[lL]?;
//NUMERIC_LITERAL : DECIMAL_LITERAL | HEXINTEGER_LITERAL;
//DECIMAL_LITERAL : [0-9]*([0-9]('.'[0-9]*)?([eE][+-]?[0-9]+));
//HEXINTEGER_LITERAL : (?:[1-9]\d+|0|0[oO]?[0-7]+|0[xX][\da-fA-F]+|0[bB][01]+)?;

NUMERIC_LITERAL
 : DecimalLiteral
 | HexIntegerLiteral
 ;

STRING_LITERAL : [\"]( ~[\"\n\r] )*[\"] | [\']( ~[\'\n\r] )*[\'];
BOOLEAN_LITERAL : TRUE|FALSE;
NULL_LITERAL : NULL;

LPAREN : [(];
RPAREN : [)];
SEMICOLON : [;];
COLON : [:];
EQUAL : [=];
PLUS : [+];
MINUS : [-];
ASTERISK : [*];
SLASH : [/];
EXCLAMATIONMARK : [!];
EQUALS : [=][=];
GREATER : [>];
LESS : [<];
LBRACE : [{];
RBRACE :[}];
COMMA :[,];
BITAND : [&];
BITXOR : [^];
BITOR  : [|];
LOGICALAND : [&&];
LOGICALOR : [||];
CONDQUESTION : [?];
POFIX : [+][+];

// keywords start here
PRINT : 'print';
VAR : 'var';
DO : 'do';
WHILE : 'while';
FOR : 'for';
IF : 'if';
ELSE : 'else';
IN : 'in';
BREAK : 'break';
CONTINUE : 'continue';
THROW : 'throw';
TRY : 'try';
CATCH : 'catch';
FINALLY : 'finally';
NEW : 'new';
RETURN : 'return';
FUNCTION : 'function';
TRUE : 'true';
FALSE : 'false';
NULL : 'null';

IDENTIFIER : IdentifierCharacters;

// skip whitespace and comments

WhiteSpace : SpaceTokens+ -> skip;

