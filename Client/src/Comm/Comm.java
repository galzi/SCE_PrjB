package Comm;

import java.io.*;
import java.util.*;
import javax.json.*;
import java.net.*;

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

    public static void printMap(Map<String, Object> m, int depth) {
        for (Map.Entry entry : m.entrySet()) {
            for (int i = 0; i <= depth; i++) {
                System.out.print("\t");
            }
            System.out.print(entry.getKey() + ": ");

            if (entry.getValue() instanceof HashMap) {
                printMap((Map<String, Object>) entry.getValue(), depth + 1);
            } else {
                System.out.println(entry.getValue());
            }
        }
    }

    public static String POST(String Url, Map<String, String> map) throws UnsupportedEncodingException, IOException {
        URL url = null;
        try {
            url = new URL(Url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String,Object> params = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String str = "";
        for (int c; (c = in.read()) >= 0;)
            str += (char) c;
        return str;
    }

    public static void main(String[] args) {
        Map<String, Object> map = toMap(GetURLContent("http://www.apilayer.net/api/live?access_key=5a9785bc12c18412ea75e910dd525285&format=1"));
        printMap(map, 0);

        try {
            String s = POST("http://home.dev/App/index1.php", new HashMap<String, String>() {
                {
                    put("username","frenki the fish");
                    put("password","Aa123456");
                }
            });
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
