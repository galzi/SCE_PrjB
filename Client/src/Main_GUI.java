import ExchangeRates.ExchangeRates_GUI;
import Memos.Mamo_GUI;
import OnThisDay.OnThisDay_GUI;
import PictureAlbum.PictureAlbum_GUI;
import RSS.Rss_GUI;
import TodoList.ToDoList_GUI;
import WorldClocks.WorldClocks_GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main_GUI extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main_GUI frame = new Main_GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Main_GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 493, 378);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JLayeredPane layeredPane = new JLayeredPane();
        contentPane.add(layeredPane, BorderLayout.CENTER);

        JButton btnNewButton = new JButton("To Do List");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                btnNewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        ToDoList_GUI tdl=new ToDoList_GUI();
                    }
                });

            }
        });
        btnNewButton.setBounds(164, 178, 117, 23);
        layeredPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Memos");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                btnNewButton_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        Mamo_GUI m=new Mamo_GUI();
                    }
                });

            }
        });
        btnNewButton_1.setBounds(164, 144, 117, 23);
        layeredPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("World Clocks");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                btnNewButton_2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        WorldClocks_GUI wc=new WorldClocks_GUI();
                    }
                });

            }
        });
        btnNewButton_2.setBounds(164, 110, 117, 23);
        layeredPane.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("RSS");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                btnNewButton_3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Rss_GUI addRss  = Rss_GUI.getInstance();//singleton
                    }
                });

            }
        });
        btnNewButton_3.setBounds(164, 212, 117, 23);
        layeredPane.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton(" On This Day");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                btnNewButton_4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        OnThisDay_GUI otd=new OnThisDay_GUI();
                    }
                });

            }
        });
        btnNewButton_4.setBounds(164, 246, 117, 23);
        layeredPane.add(btnNewButton_4);

        JButton btnNewButton_5 = new JButton("Picture Album");
        btnNewButton_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                btnNewButton_5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        PictureAlbum_GUI pa=new PictureAlbum_GUI();
                    }
                });

            }
        });
        btnNewButton_5.setBounds(164, 76, 117, 23);
        layeredPane.add(btnNewButton_5);

        JButton btnNewButton_6 = new JButton("Exchange Rates");
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                btnNewButton_6.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        ExchangeRates_GUI nm=new ExchangeRates_GUI();
                    }
                });

            }
        });
        btnNewButton_6.setBounds(164, 38, 117, 23);
        layeredPane.add(btnNewButton_6);
    }
}
