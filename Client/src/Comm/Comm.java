package Comm;

import java.io.*;
import java.util.*;
import javax.json.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Comm {
    /**
     * Gets a URL, and returns the page's content. Works only on plain text.
     * In case of failure, returns null.
     *
     * @param Url Page's URL, as a string.
     * @return the page's content.
     */
    public static String GetURLContent(String Url) {
        String content = "";
        try {
            URL url = new URL(Url);
            URLConnection conn = url.openConnection();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String line;

            while ((line = br.readLine()) != null) {
                content += line;
            }
            return content;
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
    private static Map<String, Object> toMap(JsonObject jsonObj) {
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

    public static void printHashMap(HashMap map) {
        printHashMap(map, 0);
    }

    public static void printHashMap(HashMap map, int depth) {
        for (Object entry : map.entrySet()) {
            if (entry.getClass().getName().equals("HashMap")) {
                printHashMap((HashMap) entry, depth + 1);
            } else {
                System.out.println(((HashMap) entry).getKey() + ": ");
            }
            System.out.println();
            printHashMap(entry.getValue());
        }

        /*
        if (map instanceof HashMap) {
            printHashMap(map, depth + 1);
        } else {
            for (int i = 0; i <= depth; i++) {
                System.out.println("\t");
            }
            System.out.println(map);
        }
         */
    }

    public static void main(String[] args) {
        System.out.println(GetURLContent("http://www.apilayer.net/api/live?access_key=5a9785bc12c18412ea75e910dd525285&format=1"));
        Map<String, Object> map = toMap(GetURLContent("http://www.apilayer.net/api/live?access_key=5a9785bc12c18412ea75e910dd525285&format=1"));

        for (Map.Entry entry : map.entrySet()) {
            /*if (entry instanceof List) {
                for ()
                System.out.println();
            }*/
            System.out.println(entry.getKey() + ": " + entry.getValue());

            //System.out.println(entry.getKey() + ", ");
            //printHashMap(entry.getValue());
            System.out.println(entry.getValue().getClass().getName());
            if (entry.getValue() instanceof HashMap) {
                main();
            }
        }
    }
}
