package utils;

import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

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

    @Override
    public String toString(){
        JSONObject response = new JSONObject();
        response.put("status", this.code);
        response.put("error", this.error);
        if(this.data == null){
            response.put("data", JSONObject.NULL);
        }else{
            if(data instanceof JSONObject || data instanceof JSONArray || data instanceof String){
                response.put("data", data);
            }else{
                if(data instanceof List){
                    response.put("data", new JSONArray(data.toString()));
                }else{
                    response.put("data", new JSONObject(data).toString());
                }
            }
            response.put("data", this.data);
        }
        return response.toString();
    }
}
