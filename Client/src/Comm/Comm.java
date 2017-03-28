package Comm;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

//import Comm.json.*;
//import Comm.json.stream.JsonParser;
import javax.json.*;

public class Comm {
    /**
     * Gets a URL, and returns the page's content. Works only on plain text.
     * In case of failure, returns null.
     *
     * @param Url Page's URL, as a string.
     * @return the page's content.
     */
    public static String GetURLContent(String Url) {
        try {
            URL url = new URL(Url);
            URLConnection conn = url.openConnection();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            return br.readLine();
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static Map<String, Object> toMap(String JSONrawData) {
        return toMap(Json.createReader(new StringReader(JSONrawData)).readObject());
    }

    /**
     * Converts a JSON-formatted string into a map.
     * @param jsonObj JSON-formatted string
     * @return the data given in JSON format as a map.
     */
    public static Map<String, Object> toMap(JsonObject jsonObj) {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = jsonObj.keySet().iterator();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = jsonObj.get(key);

            if (value instanceof JsonArray) {
                value = toList((JsonArray) value);
            }

            else if (value instanceof JsonObject) {
                value = toMap((JsonObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    private static List<Object> toList(JsonArray array) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if (value instanceof JsonArray) {
                value = toList((JsonArray) value);
            }

            else if (value instanceof JsonObject) {
                value = toMap((JsonObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static void main(String[] args) {
        Map<String, Object> map = toMap(GetURLContent("http://www.apilayer.net/api/live?access_key=5a9785bc12c18412ea75e910dd525285&format=1"));

        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }
}
