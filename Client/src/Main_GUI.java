import Comm.Comm;
import Comm.HttpUrlConnection;
import ExchangeRates.ExchangeRates_GUI;
import OnThisDay.OnThisDay_GUI;
import RSS.RSS_GUI;
import TodoList.ToDoList_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Map;

public class Main_GUI extends JFrame {
    private JButton[] btn = new JButton[4];
    private String[] str = {"To Do List", "Exchange Rates", "On This Day", "RSS"};

    public Main_GUI() {
        super("Select a App");
        // this.setLayout(new FlowLayout());
        this.setSize(250, 400);

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        for (int i = 0; i < this.btn.length; i++) {
            this.btn[i] = new JButton(this.str[i]);
            this.btn[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            this.btn[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, this.btn[i].getMinimumSize().height));
            contentPane.add(this.btn[i]);
        }

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.btn[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDoList_GUI tdl = new ToDoList_GUI();
                tdl.setVisible(true);
            }
        });

        this.btn[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExchangeRates_GUI er = new ExchangeRates_GUI();
                er.setVisible(true);
            }
        });

        this.btn[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnThisDay_GUI otd_main = new OnThisDay_GUI();
                otd_main.setVisible(true);
            }
        });

        this.btn[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RSS_GUI rss = RSS_GUI.getInstance();
                rss.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Hashtable<String, String> info = Comm.login(frame);
        Map<String, Object> map;
        if (info == null) {
            try {
                do {
                    info = Comm.register(frame);
                    map = Comm.toMap(HttpUrlConnection.sendPost(HttpUrlConnection.serverHost + "register", "username=" + info.get("user") + "&password=" + info.get("pass")));
                    JOptionPane.showMessageDialog(frame, map.get("MSG").toString(), "Error", JOptionPane.ERROR_MESSAGE);
                } while ((!(map.get("Code").toString()).equals("0")));

            } catch (Exception e) {
                e.printStackTrace();
            }
            // check result - json regex, username already exists?
        } else {
            try {
                while (true) {
                    map = Comm.toMap(HttpUrlConnection.performLogin(info.get("user"), info.get("pass")));
                    if ((map.get("loginStatus").toString()).equals("FAILURE")) {
                        JOptionPane.showMessageDialog(frame, "Wrong credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                        info = Comm.login(frame);
                    } else {
                        break;
                    }

                    // System.exit(1);
                    // limit attempts
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new Main_GUI();
    }
}
