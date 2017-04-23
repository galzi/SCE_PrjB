package RSS;

import javafx.scene.layout.Border;

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

    private static AddRss_GUI instance = null;

    public AddRss_GUI() {
        super("AddRss");
        setLayout(new BorderLayout());
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 70, 300, 150);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout());

        JLabel urlLabel = new JLabel("Url feed");
        urlLabel.setPreferredSize(new Dimension(50,20));
        textPanel.add(urlLabel);


        JTextField urlTextField = new JTextField("http://www.");
        urlTextField.setPreferredSize(new Dimension(270,30));
        textPanel.add(urlTextField);
        add(textPanel,BorderLayout.CENTER);


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JButton btnAddRss = new JButton("OK");
        btnAddRss.setBounds(50,50,50,50);
        btnAddRss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buttonsPanel.add(btnAddRss);

        JButton btnAbort = new JButton("Abort");
        btnAbort.setBounds(50,50,50,50);
        buttonsPanel.add(btnAbort);
        add(buttonsPanel,BorderLayout.SOUTH);
    }

    public static AddRss_GUI getInstance(){
        if(instance == null)
            instance = new AddRss_GUI();

        return instance;
    }


}
