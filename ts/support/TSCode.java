package ts.support;
import java.util.*;

public interface TSCode {
    public TSValue execute(boolean isConstructorCall, TSValue ths, TSValue[] args, TSLexicalEnvironment lexEnviron);
    //public void execute();
}