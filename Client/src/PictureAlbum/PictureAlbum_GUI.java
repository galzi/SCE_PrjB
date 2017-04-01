package PictureAlbum;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mor on 30/03/2017.
 */
public class PictureAlbum_GUI extends JFrame {

    private JButton uploadAnImageButton;
    private JButton fullScreenButton;
    private JButton nextButton;
    private JButton backButton;
    private JButton downloadButton;
    private JButton deleteButton;
    private JButton changeDateButton;
    private JButton albumCollectionButton;

    public PictureAlbum_GUI(){
        super("Picture Album");
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(250,400);
    }
}
