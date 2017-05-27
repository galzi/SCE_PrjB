package RSS;

import RSS.model.Feed;
import RSS.model.FeedMessage;
import RSS.read.RSSFeedParser;
import com.sun.xml.internal.ws.api.model.ExceptionType;
import javafx.scene.layout.Border;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JButton;


/**
 * Created by Or on 4/23/2017.
 */
public class Rss_GUI extends JFrame {

    private static Rss_GUI instance = null;
    private JFrame frame;
    private /*JPanel*/Container RssGuiPanel;
    private JScrollPane scrollRssGuiPanel;
    private GridBagConstraints constraints;
    private JLabel rssListLabel;
    private DefaultListModel rssListModel;
    private JList rssList;
    private JScrollPane scrollRssList;
    private JButton btnAddRss;
    private JTextField urlTextField;
    private JLabel rssDetailsLabel;
    private DefaultListModel feedMessageListModel;
    private JList rssMessages;
    private JScrollPane scrollRssMessages;

    private ArrayList<Feed> feedList = new ArrayList<Feed>();

    private ArrayList<String> feedLinks = new ArrayList<String>();


    public Rss_GUI() {
        super("RssGUI");
        setVisible(true);
        setBounds(50, 50, 700, 600);

        RssGuiPanel = new /*JPanel()*/Container();
        RssGuiPanel.setLayout(new GridBagLayout());
        RssGuiPanel.setPreferredSize(new Dimension(525, 525));
        scrollRssGuiPanel = new JScrollPane(RssGuiPanel);
        scrollRssGuiPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollRssGuiPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setContentPane(scrollRssGuiPanel);

        rssListLabel = new JLabel("RSS list");
        constraints = new GridBagConstraints(0,0,1,1,0.0,1.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),0,0);
        RssGuiPanel.add(rssListLabel, constraints);

        rssListModel = new DefaultListModel();
        rssList = new JList(rssListModel);
        rssList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rssList.setVisibleRowCount(-1);
        scrollRssList = new JScrollPane(rssList);
        constraints = new GridBagConstraints(0,1,2,1,0.0,300,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),500,200);
        RssGuiPanel.add(scrollRssList, constraints);

        btnAddRss = new JButton("Add rss");
        btnAddRss.setBounds(0, 0, 50, 50);
        constraints = new GridBagConstraints(0,2,1,1,0.0,100000,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),5,5);
        RssGuiPanel.add(btnAddRss, constraints);

        urlTextField = new JTextField("http://www.");
        constraints = new GridBagConstraints(1,2,1,1,0.0,100000,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),5,12);
        RssGuiPanel.add(urlTextField, constraints);

        rssDetailsLabel = new JLabel("RSS details");
        constraints = new GridBagConstraints(0,3,1,1,0.0,100000,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),5,12);
        RssGuiPanel.add(rssDetailsLabel, constraints);

        feedMessageListModel = new DefaultListModel();
        rssMessages = new JList(feedMessageListModel);
        constraints = new GridBagConstraints(0,4,2,1,0.0,10000000,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),500,200);
        rssMessages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rssMessages.setVisibleRowCount(-1);
        scrollRssMessages = new JScrollPane(rssMessages);
        RssGuiPanel.add(scrollRssMessages,constraints);

        btnAddRss.addActionListener(new AddRssButtonListener(urlTextField,rssListModel,feedList));
        rssList.addMouseListener(new RssListMouseListener(feedList,feedMessageListModel,rssList,feedLinks));
        rssMessages.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    JList rssMessagesc = (JList)e.getSource();
                    if(e.getClickCount()==2){
                        int row = rssMessagesc.getSelectedIndex();
                        try {
                            URL url = new URL(feedLinks.get(row));
                            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop():null;
                            desktop.browse(url.toURI());
                        }
                        catch(Exception el){}
                    }
                }
            }
        });

    }

    public static Rss_GUI getInstance() {
        if (instance == null)
            instance = new Rss_GUI();
        else
            instance.setVisible(true);

        return instance;
    }
}
