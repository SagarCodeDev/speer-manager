package exceptions;

public class SpeerException extends Exception{
    public SpeerException(){
        super();
    }

    public SpeerException(String msg){
        super(msg);
    }

    public SpeerException(String msg, Exception error){
        super(msg, error);
    }
}
