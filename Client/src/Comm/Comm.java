package Comm;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ListIterator;
import java.util.Map;

import Comm.json.Json;
import Comm.json.JsonArray;
import Comm.json.JsonObject;
import Comm.json.JsonReader;
import Comm.json.JsonValue;

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

    /**
     * Converts a JSON-formatted string into a map.
     *
     * @param JSONrawData JSON-formatted string
     * @return the data given in JSON format as a map.
     */
    public static Map JSONtoMap(String JSONrawData) {
        InputStream stream = new ByteArrayInputStream(JSONrawData.getBytes(StandardCharsets.UTF_8));
        JsonReader jsonReader = Json.createReader(stream);
        jsonReader.close();

        // iterate JSON fields
        JsonArray jsonArray = jsonReader.readArray();
        ListIterator l = jsonArray.listIterator();
        while (l.hasNext()) { // create map
            JsonObject j = (JsonObject) l.next();
            JsonObject ciAttr = j.getJsonObject("ciAttributes");
        }
        return null;
    }
}
