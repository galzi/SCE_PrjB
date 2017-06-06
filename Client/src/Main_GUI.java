import Comm.Comm;
import Comm.HttpUrlConnection;
import ExchangeRates.ExchangeRates_GUI;
import Memos.Mamo_GUI;
import OnThisDay.OnThisDay_GUI;
import PictureAlbum.PictureAlbum_GUI;
import RSS.RSS_GUI;
import TodoList.ToDoList_GUI;
import WorldClocks.WorldClocks_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Map;

public class Main_GUI extends JFrame {
    // public static final String HOSTNAME = "http://localhost/app/Server/";
    private JButton[] btn = new JButton[7];
    private String[] str = {"To Do List", "Memos", "Exchange Rates", "Picture Album", "On This Day", "RSS", "World Clocks"};

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
                Mamo_GUI mm = new Mamo_GUI();
                mm.setVisible(true);
            }
        });

        this.btn[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExchangeRates_GUI er = new ExchangeRates_GUI();
                er.setVisible(true);
            }
        });

        this.btn[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PictureAlbum_GUI pa = new PictureAlbum_GUI();
                pa.setVisible(true);
            }
        });

        this.btn[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnThisDay_GUI otd_main = new OnThisDay_GUI();
                otd_main.setVisible(true);
            }
        });

        this.btn[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RSS_GUI rss = new RSS_GUI();
                rss.setVisible(true);
            }
        });

        this.btn[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WorldClocks_GUI wc = new WorldClocks_GUI();
                wc.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Boolean register = false;
        Hashtable<String, String> info = Comm.login(new JFrame(), register);
        if (register) {
            try {
                HttpUrlConnection.sendPost(HttpUrlConnection.serverHost + "register.php", "username=" + info.get("user") + "&password=" + info.get("pass"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // check result - json regex, username already exists?
        } else {
            try {
                Map<String, Object> map = Comm.toMap(HttpUrlConnection.performLogin(info.get("user"), info.get("pass")));
                if ((map.get("loginStatus").toString()).equals("FAILURE")) {
                    System.exit(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new Main_GUI();
    }
}
