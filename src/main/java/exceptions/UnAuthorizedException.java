package exceptions;

public class UnAuthorizedException extends SpeerException{
    public UnAuthorizedException(){
        super();
    }

    public UnAuthorizedException(String msg){
        super(msg);
    }

    public UnAuthorizedException(String msg, Exception error){
        super(msg, error);
    }
}
