package Memos;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mor on 30/03/2017.
 */
public class New_memo extends JFrame {

    private JButton cancelButton;
    private JButton OKButton;
    private JTextField textField1;

    public New_memo(){
        super("new memo");
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(400,350);

        add(cancelButton);
        add(OKButton);
        add(textField1);




    }
}
