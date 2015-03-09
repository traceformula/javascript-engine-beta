
package ts.parse;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;

/** Implement error recovery that just throws a ANTLR
 *  ParseCancellationException without printing an error
 *  message first. Used to exit parse at first syntax error.
 */
public class BailWithNoMessageErrorStrategy extends BailErrorStrategy
{
  /** Implemented to do nothing. Syntax error will be reported from
   *  the system's main method (i.e. ts.Main.main).
   */
  public void reportError(Parser recognizer, RecognitionException e)
  {
    // do nothing
  }
}

