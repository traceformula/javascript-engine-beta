
package ts.support;

/**
 * The super class for Tscript References
 * (<a href="http://www.ecma-international.org/ecma-262/5.1/#sec-8.7">ELS
 * 8.7</a>).
 * <p>
 * The derived classes are:
 * <ul>
 * <li> TSEnvironmentReference
 * <li> TSpropertyReference - not implemented yet.
 * </ul>
 *
 */
abstract class TSReference extends TSValue
{
  private final TSString name;

  TSReference(final TSString name)
  {
    this.name = name;
  }

  TSString getReferencedName()
  {
    return name;
  }

  /** Is it a property reference? */
  abstract boolean isPropertyReference();

  /** Is it a unresolvable reference (not defined)? */
  abstract boolean isUnresolvableReference();

  //
  // the following are methods that are inherited from TSValue
  // and just require that getValue be called
  //

  /** Get value from reference and convert it to primitive type.<br>
   * (not public as not used outside of package)<br>
   * (type hint not supported)
   */
  final TSPrimitive toPrimitive()
  {
    return this.getValue().toPrimitive();
  }

  /** Get value from reference and convert it to number type. */
  public final TSNumber toNumber()
  {
    return this.getValue().toNumber();
  }

  /** Get value from reference and convert it to string type. */
  public final TSString toStr()
  {
    return this.getValue().toStr();
  }

  /** Get value from reference and see if it is undefined. */
  public final boolean isUndefined()
  {
    return this.getValue().isUndefined();
  }
}


