package RSS;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mor on 30/03/2017.
 */
public class RSS_GUI extends JFrame{

    private JButton addFeedButton;
    private JButton deleteFeedButton;

    public RSS_GUI(){
        super("RSS");
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(250,400);
    }
}
