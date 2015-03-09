#
# check if parser needs to be rebuilt
#

TscriptParser.java: Tscript.g
	bash buildParser.sh

clean:
	/bin/rm -f TscriptParser.java Tscript.tokens TscriptLexer.java TscriptLexer.tokens TscriptListener.java TscriptBaseListener.java

