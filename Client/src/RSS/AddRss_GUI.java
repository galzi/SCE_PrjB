package RSS;

import RSS.model.Feed;
import RSS.model.FeedMessage;
import RSS.read.RSSFeedParser;
import com.sun.xml.internal.ws.api.model.ExceptionType;
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
    private JFrame frame;

    public AddRss_GUI() {
        super("AddRss");
        setLayout(new BorderLayout());
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 70, 800, 600);

        JPanel NorthPanel = new JPanel();
        JLabel rssListLabel = new JLabel("RSS list");
        rssListLabel.setPreferredSize(new Dimension(50,20));
        NorthPanel.add(rssListLabel);
        add(NorthPanel,BorderLayout.NORTH);


        JPanel CenterPanel = new JPanel();
        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("Jane Doe");
        listModel.addElement("John Smith");
        listModel.addElement("Kathy Green");
        JList rssList = new JList(listModel);
        CenterPanel.add(rssList);
        add(CenterPanel,BorderLayout.CENTER);


        listModel.addElement("Or Galili");

        JPanel EastPanel = new JPanel();
        JButton btnAbort = new JButton("Delete rss");
        btnAbort.setBounds(50,50,50,50);
        EastPanel.add(btnAbort);
        add(EastPanel,BorderLayout.EAST);

        JPanel SouthPanel = new JPanel();
        SouthPanel.setLayout(new FlowLayout());
        JButton btnAddRss = new JButton("Add rss");
        btnAddRss.setBounds(50,50,50,50);

        SouthPanel.add(btnAddRss);
        JTextField urlTextField = new JTextField("http://www.");
        urlTextField.setPreferredSize(new Dimension(300,30));
        SouthPanel.add(urlTextField);
        add(SouthPanel,BorderLayout.SOUTH);


        btnAddRss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String urlFeed = urlTextField.getText();
                try {
                    RSSFeedParser parser = new RSSFeedParser(urlFeed);/*http://www.vogella.com/article.rss*/
                    Feed feed = parser.readFeed();
                    listModel.addElement(feed.getDescription());
                    //System.out.println(feed);
                    for (FeedMessage message : feed.getMessages()) {
                        System.out.println(message);
                    }
                }
                catch (Exception el){
                    //custom title, error icon
                    JOptionPane.showMessageDialog(frame,
                            "This is not rss",
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                    urlTextField.setText("");
                    return;
                }

            }
        });
    }

    public static AddRss_GUI getInstance(){
        if(instance == null)
            instance = new AddRss_GUI();

        return instance;
    }


}
