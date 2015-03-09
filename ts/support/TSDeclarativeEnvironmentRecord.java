
package ts.support;

import ts.Message;

import java.util.Map;
import java.util.HashMap;

/**
 * The class for Tscript declarative environment record
 * (<a href="http://www.ecma-international.org/ecma-262/5.1/#sec-10.2.1.1">ELS
 * 10.2.1.1</a>).
 */
final class TSDeclarativeEnvironmentRecord extends TSEnvironmentRecord
{
  private final Map<TSString, TSBinding> map;

  /** Create a declarative environment record by creating a HashMap to
   *  connect names to their bindings (value and attributes).
   *
   * @see TSBinding
   */
  TSDeclarativeEnvironmentRecord()
  {
     map = new HashMap<TSString,TSBinding>(10);
  }

  /** Does the environment have a binding for the given name? */
  boolean hasBinding(final TSString name)
  {
    return map.containsKey(name);
  }

  /** Create a mutable binding for a name. Note that there is no value
   *  in the binding at this point.
   */
  void createMutableBinding(final TSString name, final boolean isDeletable)
  {
    assert (map.get(name) == null) : "binding already exists";
    map.put(name, new TSBinding(TSUndefined.value, isDeletable, false));
  }

  /** Connect a value to a mutable binding for a name. Note that the binding
   *  must already exist.
   */
  void setMutableBinding(final TSString name, final TSValue value)
  {
    TSBinding binding = map.get(name);

    assert (binding != null) : "missing binding";
    if (binding.isImmutable())
    {
      Message.executionError("binding is immutable");
    }
    binding.setValue(value);
  }

  /** Get the value from a binding for a name. */
  TSValue getBindingValue(final TSString name)
  {
    TSBinding binding = map.get(name);

    assert (binding != null) : "missing binding";

    TSValue value = binding.getValue();
    return value;
  }

  /** Try to delete a binding for a name.
   *
   *  @return success (1) or failure (0). TODO: the return type should be
   *  TSBoolean.
   */
  TSNumber deleteBinding(final TSString name)
  {
    assert false : "not implemented";
    return null;
  }

  /** Always returns "undefined" because there is never a "this" value
   *  for an environment.
   */
  TSValue implicitThisValue()
  {
    return TSUndefined.value;
  }

  /** Create an immutable binding for a name. Note there is no value
   *  at this point. Assumes an immutable binding is not deletable.
   */
  void createImmutableBinding(final TSString name)
  {
    assert (map.get(name) == null) : "binding already exists";
    map.put(name, new TSBinding(TSUndefined.value, false, true));
  }

  /** Put the initial (and final) value into a immutable binding for
   *  a name.
   */
  void initializeImmutableBinding(final TSString name, final TSValue value)
  {
    TSBinding binding = map.get(name);
    assert (binding != null) : "missing binding";
    assert (binding.isImmutable()) : "binding is not immutable";
    TSValue oldValue = binding.getValue();
    assert (oldValue instanceof TSUndefined) : "binding is initialized";
    binding.setValue(value);
  }
}

