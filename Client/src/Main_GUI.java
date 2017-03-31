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


/**
 * Created by Mor on 30/03/2017.
 */
public class Main_GUI extends JFrame {


    private JButton exchangeRatesButton;
    private JButton worldClocksButton;
    private JButton toDoListButton;
    private JButton RSSButton;
    private JButton pictureAlbumButton;
    private JButton onThisDayButton;
    private JButton memosButton;


    public Main_GUI(){
        super("Select a App");
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(250,400);


        add(exchangeRatesButton);
        add(worldClocksButton);
        add(toDoListButton);
        add(RSSButton);
        add(pictureAlbumButton);
        add(onThisDayButton);
        add(memosButton);

        onThisDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnThisDay_GUI otd_main=new OnThisDay_GUI();
                otd_main.setVisible(true);
            }
        });
        exchangeRatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExchangeRates_GUI er =new ExchangeRates_GUI();
                er.setVisible(true);
            }
        });
        memosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mamo_GUI mm =new Mamo_GUI();
                mm.setVisible(true);
            }
        });
        pictureAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PictureAlbum_GUI pa =new PictureAlbum_GUI();
                pa.setVisible(true);
            }
        });
        RSSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RSS_GUI rss =new RSS_GUI();
                rss.setVisible(true);
            }
        });
        toDoListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDoList_GUI tdl =new ToDoList_GUI();
                tdl.setVisible(true);
            }
        });
        worldClocksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WorldClocks_GUI wc =new WorldClocks_GUI();
                wc.setVisible(true);
            }
        });
    }


}
