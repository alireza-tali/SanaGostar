package vavkhan.com.sanagostar.utils;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Class for converting Object to Json or reverse
 * and get a list from a json object
 */

public class GsonHelper {

    /**
     * Convert an object to Json String
     *
     * @param model - Object for converint to Json String
     * @param <T>   - Object Class
     * @return - Convertin Object to Json String
     */
    public static <T> String convertModelToString(T model) {
        Gson gson = new Gson();
        String result = gson.toJson(model);
        return result;
    }

    /**
     * Convert a Json String to an object
     *
     * @param json - Json String for converting to an object
     * @param type - Type of object class
     * @param <T>  - Class Object
     * @return
     */
    public static <T> T ConvertStringToModel(String json, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(json, type);
        return result;
    }

    /**
     * Convert an Object to JsonObject
     *
     * @param model - Object for converting to JsonObject
     * @param <T>   - Object Class
     * @return - Result as JSONObject
     */
    @Nullable
    public static <T> JSONObject convertToJson(T model) {
        Gson gson = new Gson();
        String json = gson.toJson(model, model.getClass());
        try {
            JSONObject obj = new JSONObject(json);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert a Json String to List of Object
     *
     * @param json - Json String for Converting to list
     * @param type - Class Type
     * @param key  - The Key of List in JsonObject
     * @param <T>  - Object Class
     * @return - ArrayList Of Object
     */
    public static <T> List<T> convertStringToList(String json, Class<T> type, String key) {
        List<T> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray(key);
            if (array != null && array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    if (type.getSuperclass().isInstance(String.class)){
                        list.add((T)array.getString(i));
                    }else{
                        T item = GsonHelper.ConvertStringToModel(array.getJSONObject(i).toString(), type);
                        list.add(item);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * For geting a value from json String
     * @param json  Source json String
     * @param key   Key for search in json
     * @return      Return key value as string
     */
    public static String getValueFromJsonString(String json, String key) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(json);
        return jo.get(key).getAsString();
    }

    /**
     * Convert JsonArray To List of Model
     * @param jasonString JsonArray As String

     * @param <T> List Type
     * @return List Result
     */
    public static <T> List<T> convertJsonArrayToList(String jasonString,Class<T> type){
        List<T> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jasonString);
            if (array != null && array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    if (type.getSuperclass().isInstance(String.class)){
                        list.add((T)array.getString(i));
                    }else{
                        T item = GsonHelper.ConvertStringToModel(array.getJSONObject(i).toString(), type);
                        list.add(item);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
