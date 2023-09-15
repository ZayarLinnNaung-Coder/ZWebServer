package org.search_engine.utils;

import java.util.HashMap;
import java.util.Map;

public class ParamUtils {

    public static String getPath(String request){
        String pathWithParams = getPathWithParams(request);
        return pathWithParams.split("\\?")[0];
    }

    public static Map<String, String> getAllParam(String request){
        Map<String, String> paramMap = new HashMap<>();

        String pathWithParams = getPathWithParams(request);
        String paramString = pathWithParams.split("\\?")[1];
        String[] paramArr = paramString.split("&");
        for (String s : paramArr) {
            String[] param = s.split("=");

            // If param does not have data, default to empty string
            if(param.length > 1){
                paramMap.put(param[0], param[1]);
            } else {
                paramMap.put(param[0], "");
            }
        }
        return paramMap;
    }

    public static String getPathWithParams(String request) {
        String[] requestParts = request.split(" ");
        return requestParts[1];
    }
}
