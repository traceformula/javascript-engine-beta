package ts.support;

public class TSUncaughtException extends TSRuntimeException {

    public TSUncaughtException(TSValue v) {
        super(v);
        setValue(v);
    }

    public String toString(){
        return "Uncaught " + getInternal().toStr().getInternal();
    }
    
}