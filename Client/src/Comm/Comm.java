package Comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Comm {
    public static String GetURLContent(String Url) {
        try {
            URL url = new URL(Url);
            URLConnection conn = url.openConnection();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            return br.readLine();
        } catch (MalformedURLException e) {
            return null;
            // e.printStackTrace();
        } catch (IOException e) {
            return null;
            // e.printStackTrace();
        }
        // return null;
    }
}
