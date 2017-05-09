/* File name: functions.java
 * 
 * Purpose: Provide support to the main java file of the application.
 * 
 * Author: yakir gaziel
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.swing.JTextArea;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class functions {
	protected void accessWikipedia(String searchTerm1,String searchTerm2, JTextArea results,String action) {
		results.setText("");

			Map<String, Object> map = toMap(GetURLContent("http://history.muffinlabs.com/date/" + searchTerm1 + "/" + searchTerm2 + '"'));
			printMap(map, 0,results,action);

	}




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
	 *
	 * @param jsonObj JSON-formatted string
	 * @return the data given in JSON format as a map.
	 */
	private static Map<String, Object> toMap(JsonObject jsonObj) {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = jsonObj.keySet().iterator();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = jsonObj.get(key);

			if (value instanceof javax.json.JsonArray) {
				value = toList((javax.json.JsonArray) value);
			} else if (value instanceof JsonObject) {
				value = toMap((JsonObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	private static List<Object> toList(javax.json.JsonArray array) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.size(); i++) {
			Object value = array.get(i);
			if (value instanceof javax.json.JsonArray) {
				value = toList((javax.json.JsonArray) value);
			} else if (value instanceof JsonObject) {
				value = toMap((JsonObject) value);
			}
			list.add(value);
		}
		return list;
	}

	public static void printMap(Map<String, Object> m, int depth,JTextArea results,String action) {
		String a = "asd";
		for (Map.Entry entry : m.entrySet()) {
			for (int i = 0; i <= depth; i++) {
				System.out.print("\t");
			}


			if (entry.getValue() instanceof Map) {
				printMap((Map<String, Object>) entry.getValue(), depth + 1,results,action);
			}
			if (entry.getKey().equals(action)) {

				results.append(entry.getKey() + ": " + "\n" + " ");
				a = entry.getValue().toString();

				a = a.replaceAll("=", ":");

				StringBuilder sb = new StringBuilder(a);
				sb.deleteCharAt(0);
				sb.deleteCharAt(sb.length() - 1);

				a = sb.toString();
				String b[] = a.split(",");
				for (int i = 0; i < b.length; i++) {
					if (b[i].contains("year")) {
						b[i] = b[i].replace("{", "");
						results.append("\n");
						results.append("\n");
						results.append(b[i]);
						results.append("\n");
					}
					if (b[i].contains("text")) {
						b[i + 1] = b[i + 1].replace("}", "");
						if (!results.equals("Events")) {
							results.append(b[i] + b[i + 1]);
						} else {
							b[i] = b[i].replace("}", "");
							results.append(b[i]);

						}
					}


				}


			}


		}

	}

}
