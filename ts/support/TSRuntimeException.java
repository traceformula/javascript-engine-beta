package ts.support;

public class TSRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -7031064902083692628L;
    private TSValue value ;
    public TSRuntimeException(TSValue v) {
        super(v.toStr().getInternal());
        value =v;
    }

    public String toString(){
        return value.toStr().getInternal();
    }
    public TSValue getInternal(){
        return value;
    }
    public void setValue(TSValue v){
        value = v;
    }
}