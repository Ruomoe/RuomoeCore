package cc.canyi.core.exception;

public class RuomoeCoreInvokeException extends RuntimeException{
    public RuomoeCoreInvokeException(String message) {super(message);}
    public RuomoeCoreInvokeException() {super("Invoke RuomoeCore's method exception");}

}
