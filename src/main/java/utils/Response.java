package utils;

public class Response<T> {
    int code;
    String error;
    T data;

    private Response(int code, String error, T data){
        this.code = code;
        this.error = error;
        this.data = data;
    }

    public static <T> Response<T> ok(T data){return new Response<T>(200, null, data);}

    public static  Response error(String error){return new Response(400, error, null);}
}
