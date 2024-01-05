package exceptions;

public class NotAllowedException extends SpeerException{
    public NotAllowedException(){
        super();
    }

    public NotAllowedException(String msg){
        super(msg);
    }

    public NotAllowedException(String msg, Exception error){
        super(msg, error);
    }
}
