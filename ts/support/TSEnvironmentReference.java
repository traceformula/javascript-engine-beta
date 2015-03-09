package ts.support;

import ts.Message;

/**
 * The class for Tscript References for lexical environment references
 * (<a href="http://www.ecma-international.org/ecma-262/5.1/#sec-8.7">ELS
 * 8.7</a>).
 *
 */
final class TSEnvironmentReference extends TSReference
{
  private TSEnvironmentRecord base;

  /** Create a Reference for a name in an environment. */
  TSEnvironmentReference(final TSString name, final TSEnvironmentRecord base)
  {
    super(name);
    this.base = base;
  }

  /** Is the reference not resolvable? That is, is the name not defined
   *  in the environment?
   */
  boolean isUnresolvableReference()
  {
    // base is always defined for environment reference
    // TODO: well it should be. but we don't support property references yet.
    //       so right now the base is null if the identifier is not defined.
    // TSLexicalEnvironment.getIdentifierReference creates a property descriptor
    // with an undefined base if the identifier cannot be resolved.
    //return false;
    //return (base == null);
    //@Update 20140128
    return base.hasBinding(this.getReferencedName());
    //(base.getBindingValue(this.getReferencedName()) == TSUndefined.value);
  }

  /** Environment references cannot be property references so this always
   *  returns false.
   */
  boolean isPropertyReference()
  {
    return false;
  }

  //
  // operations on References in expressions
  //   must be public because they override public methods
  // TODO: for now base is null to indicate the identifier is not declared,
  //       which we treat now as an error

  /** Get the value from the Reference. Issues an error and
   *  returns null if the name is not defined.
   *  Update: Now we added undefined value;
   */
  public TSValue getValue()
  {
    //@Update 20150128
    if(this.getReferencedName().equals("undefined")) return TSUndefined.value;
    if (base == null || !base.hasBinding(this.getReferencedName()))
    {

        throw new TSReferenceException(TSString.create("undefined identifier: "
          + this.getReferencedName().getInternal()));
    }
    return base.getBindingValue(this.getReferencedName());
  }

  /** Assign a value to the name specified by the Reference. */
  /**
  *   Update: now we added undefined value
  */
  public void putValue(final TSValue value)
  {
    //@Update 20150128
    if(base == null || !base.hasBinding(this.getReferencedName()))
    {

      
        throw new TSReferenceException(TSString.create("undefined identifier: "
          + this.getReferencedName().getInternal()));
      
    }
 
    base.setMutableBinding(this.getReferencedName(), value);
    return;
  }

}

