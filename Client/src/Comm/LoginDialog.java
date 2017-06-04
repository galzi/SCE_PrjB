package Comm;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class LoginDialog {
    public LoginDialog() {

    }

    public Hashtable<String, String> login(JFrame frame) {
        Hashtable<String, String> loginInformation = new Hashtable<String, String>();

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("E-Mail", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField();
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(frame, panel, "login", JOptionPane.QUESTION_MESSAGE); // JOptionPane.OK_CANCEL_OPTION

        loginInformation.put("user", username.getText());
        loginInformation.put("pass", new String(password.getPassword()));
        return loginInformation;
    }

    public static void main(String[] args) {
        new LoginDialog();
    }
}
