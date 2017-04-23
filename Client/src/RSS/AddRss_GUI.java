package RSS;

import javax.swing.*;
import java.awt.*;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JButton;


/**
 * Created by Or on 4/23/2017.
 */
public class AddRss_GUI extends JFrame {

    private JPanel contentPane;

    public AddRss_GUI() {
        super("AddRss");
        setLayout(new FlowLayout());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JLayeredPane layeredPane = new JLayeredPane();
        contentPane.add(layeredPane, BorderLayout.CENTER);


        JTextField urlTextField = new JTextField(20);
        layeredPane.add(urlTextField);

        JButton btnAddRss = new JButton("OK");

        btnAddRss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnAddRss.setBounds(117, 217, 89, 23);
        layeredPane.add(btnAddRss);

        JButton btnDeleteRss = new JButton("Abort");
        btnDeleteRss.setBounds(230, 217, 115, 23);
        layeredPane.add(btnDeleteRss);
    }

}
