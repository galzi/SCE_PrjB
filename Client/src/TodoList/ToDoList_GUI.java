package TodoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ToDoList_GUI extends JFrame {

    private JPanel contentPane;


    /**
     * Create the frame.
     */
    public ToDoList_GUI() {
        super("To Do List");
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

        JButton btnNewButton = new JButton("Add a new task");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                New_task nt = new New_task();
                nt.setVisible(true);
            }
        });
        btnNewButton.setBounds(146, 217, 122, 23);
        layeredPane.add(btnNewButton);
    }

    public static void main(String[] args) {
        new ToDoList_GUI();
    }
}
