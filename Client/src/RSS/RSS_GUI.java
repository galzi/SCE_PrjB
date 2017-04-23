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

public class RSS_GUI extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public RSS_GUI() {
        super("RSS");
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

        JButton btnAddRss = new JButton("Add RSS");
        //added by Or
        btnAddRss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddRss_GUI addRss  = AddRss_GUI.getInstance();//singleton
            }
        });
        //end adding
        btnAddRss.setBounds(117, 217, 89, 23);
        layeredPane.add(btnAddRss);

        JButton btnDeleteRss = new JButton("Delete RSS");
        btnDeleteRss.setBounds(230, 217,  115, 23);
        layeredPane.add(btnDeleteRss);
    }

}
