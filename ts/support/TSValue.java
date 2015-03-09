
package ts.support;

import ts.Message;

/**
 * The super class for all Tscript values.
 */
public abstract class TSValue
{
  //
  // References: i.e. getValue and putValue (section 8.7)
  //

  /** Get the value. This method is only overridden in TSReference.
   *  Otherwise just return "this".
   */
  public TSValue getValue()
  {
    return this;
  }

  /** Assign to the value. This method is only overridden in TSReference.
   *  Otherwise just report an error.
   */
  public void putValue(TSValue value)
  {
    Message.bug("putValue called for non-Reference type");
  }

  //
  // conversions (section 9)
  //

  /** Convert to Primitive. Override only in TSObject and TSReference.
   *  Otherwise just return "this". Note: type hint is not implemented.
   */
  TSPrimitive toPrimitive()
  {
    return (TSPrimitive) this;
  }

  abstract public TSNumber toNumber();

  /** Convert to String. Override for all primitive types and TSReference.
   *  It can't be called toString because of Object.toString.
   */
  public TSString toStr()
  {
    TSPrimitive prim = this.toPrimitive();
    return prim.toStr();
  }


  //
  // binary operators (sections 11.5-11.11)
  //

  /** Perform a multiply. "this" is the left operand and the right
   *  operand is given by the parameter. Both operands are converted
   *  to Number before the multiply.
   */
  public final TSNumber multiply(final TSValue right)
  {
    TSNumber leftValue = this.toNumber();
    TSNumber rightValue = right.toNumber();
    return TSNumber.create(leftValue.getInternal() * rightValue.getInternal());
  }
  
  /** Perform an addition. "this" is the left operand and the right
   *  operand is given by the parameter. Both operands are converted
   *  to Number before the addition.
   */
  public final TSPrimitive add(final TSValue right)
  {
    TSPrimitive leftValue = this.toPrimitive();
    TSPrimitive rightValue = right.toPrimitive();

    if(leftValue instanceof TSString || rightValue instanceof TSString)
      return TSString.create(leftValue.toStr().getInternal() 
        + rightValue.toStr().getInternal());
    return TSNumber.create(leftValue.toNumber().getInternal() +
      rightValue.toNumber().getInternal());
  }

  //@Update 20150129
  /** Perform a multiply. "this" is the left operand and the right
   *  operand is given by the parameter. Both operands are converted
   *  to Number before the multiply.
   */
  public final TSNumber divide(final TSValue right)
  {
    TSNumber leftValue = this.toNumber();
    TSNumber rightValue = right.toNumber();
    return TSNumber.create(leftValue.getInternal() / rightValue.getInternal());
  }
  /** Perform an substraction. "this" is the left operand and the right
   *  operand is given by the parameter. Both operands are converted
   *  to Number before the addition.
   */
  public final TSPrimitive sub(final TSValue right)
  {
    TSPrimitive leftValue = this.toPrimitive();
    TSPrimitive rightValue = right.toPrimitive();
    return TSNumber.create(leftValue.toNumber().getInternal() -
      rightValue.toNumber().getInternal());
  }
  /** Perform not operation.
   * the value might be premitive or another expression
   */
  public static final TSBoolean not(final TSValue v){
    TSPrimitive pre = v.toPrimitive();
    if(pre.isUndefined()) return TSBoolean.create(true);
    return TSBoolean.create(pre.toNumber().getInternal()!=0?false:true);
  }
  /** Perform negation operation.
   * the value might be premitive or another expression
   */
  public static final TSNumber neg(final TSValue v){
    TSPrimitive pre = v.toPrimitive();
    return TSNumber.create(-pre.toNumber().getInternal());
  }
  /** Perform checking equality.
   * the value might be premitive or another expression
   */
  public TSBoolean eq(final TSValue right){
    TSPrimitive leftValue = this.toPrimitive();
    TSPrimitive rightValue = right.toPrimitive();
    if(leftValue instanceof TSString && rightValue instanceof TSString){
      return TSBoolean.create(leftValue.toStr().equals(rightValue.toStr()));
    }
    if((leftValue.isNull() || leftValue.isUndefined()))
    {
      return TSBoolean.create(rightValue.isNull() || rightValue.isUndefined());
    }
    if((rightValue.isNull() || rightValue.isUndefined())){
      return TSBoolean.create(leftValue.isNull() || leftValue.isUndefined());
    }
    return TSBoolean.create(leftValue.toNumber().getInternal() ==
      rightValue.toNumber().getInternal()?true:false);
  }
  /** Perform less than operator.
  * the value might be premitive or another expression
  */
  public TSBoolean lt(final TSValue right){
    TSPrimitive leftValue = this.toPrimitive();
    TSPrimitive rightValue = right.toPrimitive();
    if(leftValue instanceof TSString && rightValue instanceof TSString){
      TSString s1 = leftValue.toStr();
      TSString s2 = rightValue.toStr();
      return TSBoolean.create((s1.getInternal().compareTo(s2.getInternal()) < 0));
    }
    if((leftValue.isNull() || leftValue.isUndefined()) 
      && (rightValue.isNull() || rightValue.isUndefined()))
    {
      return TSBoolean.create(false);
    }
    return TSBoolean.create(leftValue.toNumber().getInternal() <
      rightValue.toNumber().getInternal()?true:false);
  }
  /** Perform greater than operator.
  * the value might be premitive or another expression
  */
  public TSBoolean gt(final TSValue right){
    TSPrimitive leftValue = this.toPrimitive();
    TSPrimitive rightValue = right.toPrimitive();
    if(leftValue instanceof TSString && rightValue instanceof TSString){
      TSString s1 = leftValue.toStr();
      TSString s2 = rightValue.toStr();
      return TSBoolean.create((s1.getInternal().compareTo(s2.getInternal()) > 0));
    }
    if((leftValue.isNull() || leftValue.isUndefined()) 
      && (rightValue.isNull() || rightValue.isUndefined()))
    {
      return TSBoolean.create(false);
    }
    return TSBoolean.create(leftValue.toNumber().getInternal() >
      rightValue.toNumber().getInternal()?true:false);
  }

  /** Perform an assignment. "this" is the left operand and the right
   *  operand is given by the parameter.
   */
  public final TSValue simpleAssignment(final TSValue right)
  {
    TSValue rightValue = right.getValue();
    this.putValue(rightValue);
    return rightValue;
  }

  //
  // test for null and undefined
  //

  /** Is this value Undefined? Override only in TSUndefined and
   *  TSReference.
   */
  public boolean isUndefined()
  {
    return false;
  }

  //@Update 20150128
  /** Is this value null?
   *
   */
  public boolean isNull()
  {
    return false;
  }
}

