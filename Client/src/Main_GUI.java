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

public class Main_GUI extends JFrame {
    private JButton[] btn = new JButton[7];
    private String[] str = {"To Do List", "Memos", "Exchange Rates", "Picture Album", "On This Day", "RSS", "World Clocks"};

    public Main_GUI() {
        super("Select a App");
        this.setLayout(new FlowLayout());
        this.setSize(250, 400);

        for (int i = 0; i < btn.length; i++) {
            btn[i] = new JButton(str[i]);
            this.add(btn[i]);
        }

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        btn[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDoList_GUI tdl = new ToDoList_GUI();
                tdl.setVisible(true);
            }
        });

        btn[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mamo_GUI mm = new Mamo_GUI();
                mm.setVisible(true);
            }
        });

        btn[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExchangeRates_GUI er = new ExchangeRates_GUI();
                er.setVisible(true);
            }
        });

        btn[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PictureAlbum_GUI pa = new PictureAlbum_GUI();
                pa.setVisible(true);
            }
        });

        btn[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnThisDay_GUI otd_main = new OnThisDay_GUI();
                otd_main.setVisible(true);
            }
        });

        btn[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RSS_GUI rss = new RSS_GUI();
                rss.setVisible(true);
            }
        });

        btn[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WorldClocks_GUI wc = new WorldClocks_GUI();
                wc.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new Main_GUI();
    }
}
