//@Update 20150129
package ts.support;

/**
 * Represent Boolean values
 * (<a href="http://www.ecma-international.org/ecma-262/5.1/#sec-8.2">ELS
 * 5.1</a>).
 */
public final class TSNull extends TSPrimitive
{

  // use the "create" method instead
  public static final TSNull value = new TSNull();

  // hide the constructor
  private TSNull()
  {
  }

  public double getInternal(){
    return 0;
  }

  /** Convert to Number. Undefined gets mapped to NaN. */
  public TSNumber toNumber()
  {
    return TSNumber.create(0);
  }

  /** Convert to String ("undefined"). */
  public TSString toStr()
  {
    return TSString.create("null");
  }

  /** Always returns true. */
  public boolean isNull()
  {
    return true;
  }
}