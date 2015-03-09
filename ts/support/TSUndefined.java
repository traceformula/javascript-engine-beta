
package ts.support;

/**
 * Represents the single Undefined value
 * (<a href="http://www.ecma-international.org/ecma-262/5.1/#sec-8.1">ELS
 * 8.1).
 */
public final class TSUndefined extends TSPrimitive
{
  /** Single value for this singleton class. */
  public static final TSUndefined value = new TSUndefined();

  // hide the constructor
  private TSUndefined()
  {
  }

  /** Convert to Number. Undefined gets mapped to NaN. */
  public TSNumber toNumber()
  {
    return TSNumber.create(Double.NaN);
  }

  public TSBoolean toBoolean(){
    return TSBoolean.create(false);
  }
  
  /** Convert to String ("undefined"). */
  public TSString toStr()
  {
    return TSString.create("undefined");
  }

  /** Always returns true. */
  public boolean isUndefined()
  {
    return true;
  }
}

