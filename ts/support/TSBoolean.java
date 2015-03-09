//@Update 20140128


package ts.support;

/**
 * Represent Boolean values
 * (<a href="http://www.ecma-international.org/ecma-262/5.1/#sec-8.3">ELS
 * 5.1</a>).
 */
public final class TSBoolean extends TSPrimitive
{

  // use the "create" method instead
  private TSBoolean(final boolean value)
  {
    this.value = value;
  }

  private final boolean value;

  /** Create a Number with the given value. */
  public static TSBoolean create(final boolean value)
  {
    return new TSBoolean(value);
  }

  /** Get the value. */
  public double getInternal()
  {
    return value?1:0;
  }

  public boolean getBool(){
    return value;
  }

  /** Convert to a Number, so nothing to do. */
  public TSNumber toNumber()
  {
    return value?TSNumber.create(1):TSNumber.create(0);
  }

  /** Convert Boolean to String
   * (<a href="http://www.ecma-international.org/ecma-262/5.1/#sec-9.8.1">ELS
   * 9.8.1</a>)
   */
  public TSString toStr()
  {
    return TSString.create(value?"true":"false");
  }    
}



