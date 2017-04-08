package TodoList;


import javax.swing.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class New_task extends JFrame {

    private JPanel contentPane;



    /**
     * Create the frame.
     */
    public New_task() {
        super("New task");
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

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(110, 217, 89, 23);
        layeredPane.add(btnDelete);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(218, 217, 89, 23);
        layeredPane.add(btnAdd);

        JTextPane textPane = new JTextPane();
        textPane.setBounds(360, 50, -187, 40);
        layeredPane.add(textPane);

        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(163, 168, 43, 20);
        layeredPane.add(comboBox);

        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setBounds(218, 168, 89, 20);
        layeredPane.add(comboBox_1);

        JComboBox comboBox_2 = new JComboBox();
        comboBox_2.setBounds(110, 168, 43, 20);
        layeredPane.add(comboBox_2);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(42, 11, 338, 146);
        layeredPane.add(textArea);

        JLabel lblSetDate = new JLabel("Set date:");
        lblSetDate.setBounds(54, 171, 46, 14);
        layeredPane.add(lblSetDate);


    }
}

