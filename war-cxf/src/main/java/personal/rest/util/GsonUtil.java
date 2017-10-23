package personal.rest.util;

import com.google.gson.Gson;

/**
 * Created by Hyun Woo Son on 10/20/17.
 */
public class GsonUtil {


    private static GsonUtil gsonUtil;
    private Gson gson;

    private GsonUtil(){
            gson = new Gson();

    }

    public static  GsonUtil getInstance(){
        if(gsonUtil == null){
            gsonUtil = new GsonUtil();
        }
        return gsonUtil;
    }


    public String toJson(Object toJson){
        return gson.toJson(toJson);
    }


}
