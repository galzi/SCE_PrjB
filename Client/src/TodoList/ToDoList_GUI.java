package TodoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mor on 30/03/2017.
 */
public class ToDoList_GUI extends JFrame{

    private JButton addANewTaskButton;

    public ToDoList_GUI(){
        super("To Do List");
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(250,400);

        addANewTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                New_task nt = new New_task();
                nt.setVisible(true);

            }
        });
    }

}
