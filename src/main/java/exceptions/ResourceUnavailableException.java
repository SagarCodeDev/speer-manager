package exceptions;

public class ResourceUnavailableException extends SpeerException{
    public ResourceUnavailableException(){
        super();
    }

    public ResourceUnavailableException(String msg){
        super(msg);
    }

    public ResourceUnavailableException(String msg, Exception error){
        super(msg, error);
    }
}
