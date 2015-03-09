package ts.support;

/**
 * Class to represent declaration bindings
 * (<a href="http://www.ecma-international.org/ecma-262/5.1/#sec-10.2.1">ELS
 * 10.2.1</a>). Tracks whether a
 * binding is deletable. Also tracks whether a binding is immutable.
 * An immutable binding is one where the association between an identifier
 * and a value may not be modified once it has been established. 
 *
 */
final class TSBinding
{
  private TSValue value;

  private final boolean isDeletable;

  private final boolean isImmutable;

  TSBinding(final TSValue value, final boolean isDeletable,
    final boolean isImmutable)
  {
    this.value = value;
    this.isDeletable = isDeletable;
    this.isImmutable = isImmutable;
  }

  TSValue getValue()
  {
    return value;
  }

  void setValue(final TSValue value)
  {
    this.value = value;
  }

  boolean isDeletable()
  {
    return isDeletable;
  }

  boolean isImmutable()
  {
    return isImmutable;
  }
}


