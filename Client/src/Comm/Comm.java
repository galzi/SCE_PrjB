package Comm;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.json.*;
import javax.swing.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

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

    public static Hashtable<String, String> login(final JFrame frame, Boolean registerFlag) { // TODO on close (x button) exit program
        Hashtable<String, String> loginInformation = new Hashtable<String, String>();
        final Hashtable<String, String>[] registerInformation = new Hashtable[]{null};
        registerFlag = false;

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("Username", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField();
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);

        JButton register = new JButton("Register");
        controls.add(register);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerInformation[0] = register(frame); // relogin
            }
        });
        panel.add(register, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(frame, panel, "login", JOptionPane.QUESTION_MESSAGE); // JOptionPane.OK_CANCEL_OPTION

        if (registerInformation[0] != null) {
            registerFlag = true;
            return registerInformation[0];
        }

        loginInformation.put("user", username.getText());
        loginInformation.put("pass", new String(password.getPassword()));
        return loginInformation;
    }

    public static Hashtable<String, String> register(JFrame frame) { // TODO on close (x button) exit program
        Hashtable<String, String> registerInformation = new Hashtable<String, String>();

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("Username", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        label.add(new JLabel("Confirm Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField();
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        JPasswordField confirmPassword = new JPasswordField();
        controls.add(confirmPassword);
        panel.add(controls, BorderLayout.CENTER);

        while (true) {
            JOptionPane.showMessageDialog(frame, panel, "login", JOptionPane.QUESTION_MESSAGE); // JOptionPane.OK_CANCEL_OPTION

            if (password.getPassword() != confirmPassword.getPassword()) {
                JOptionPane.showInputDialog("Passwords don't match!");
                break;
            }
        }

        // server register, json regex
        registerInformation.put("user", username.getText());
        registerInformation.put("pass", new String(password.getPassword()));
        return registerInformation;
    }

    public static void main(String[] args) {
        Map<String, Object> map = toMap(GetURLContent("http://www.apilayer.net/api/live?access_key=5a9785bc12c18412ea75e910dd525285&format=1"));

        //Main_GUI New_main_GUI =new Main_GUI();//creat a GUI window

        printMap(map, 0);
    }
}
