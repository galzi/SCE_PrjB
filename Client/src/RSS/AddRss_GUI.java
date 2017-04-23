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

        private JPanel contentPane;

        public AddRss_GUI() {
                super("AddRss");
                setLayout(new BorderLayout());
                setVisible(true);
                //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(150, 70, 300, 150);

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new FlowLayout());

                JTextField urlTextField = new JTextField();
                urlTextField.setPreferredSize(new Dimension(300,30));
                textPanel.add(urlTextField);
                add(textPanel,BorderLayout.CENTER);


                JPanel buttonsPanel = new JPanel();
                buttonsPanel.setLayout(new FlowLayout());

                JButton btnAddRss = new JButton("OK");
                btnAddRss.setBounds(50,50,50,50);
                buttonsPanel.add(btnAddRss);

                JButton btnAbort = new JButton("Abort");
                btnAbort.setBounds(50,50,50,50);
                buttonsPanel.add(btnAbort);
                add(buttonsPanel,BorderLayout.SOUTH);


        }
}
