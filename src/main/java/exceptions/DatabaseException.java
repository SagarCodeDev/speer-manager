package exceptions;

public class DatabaseException extends SpeerException{
    public DatabaseException(){
        super();
    }

    public DatabaseException(String msg){
        super(msg);
    }

    public DatabaseException(String msg, Exception error){
        super(msg, error);
    }
}
