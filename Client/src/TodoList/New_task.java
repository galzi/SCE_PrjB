package TodoList;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mor on 01/04/2017.
 */
public class New_task extends JFrame {

    private JButton addButton;
    private JButton deleteButton;
    private JTextField textField1;
    private JTextArea textArea1;

    public New_task(){
        super("New task");
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(400,350);
    }

}
