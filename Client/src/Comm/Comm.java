package Comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class Comm {
    /**
     * Gets a URL, and returns the page's content. Works only on plain text.
     * In case of failure, returns null.
     * @param Url
     *  Page's URL, as a string.
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
     * @param JSONrawData
     *  JSON-formatted string
     * @return the data given in JSON format as a map.
     */
    public static Map JSONtoMap(String JSONrawData) {
        JsonReader jsonReader = Json.createReader(JSONrawData);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        // iterate JSON fields
    }
}
