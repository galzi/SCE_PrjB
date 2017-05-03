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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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


    public Rss_GUI() {
        super("RssGUI");
        setVisible(true);
        setBounds(50, 50, 700, 500);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RssGuiPanel = new /*JPanel()*/Container();
        RssGuiPanel.setLayout(new GridBagLayout());
        RssGuiPanel.setPreferredSize(new Dimension(525, 400));
        scrollRssGuiPanel = new JScrollPane(RssGuiPanel);
        scrollRssGuiPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollRssGuiPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        //scrollPane.setBounds(50, 30, 525, 50);
        setContentPane(scrollRssGuiPanel);

        rssListLabel = new JLabel("RSS list");
        constraints = new GridBagConstraints(0,0,1,1,0.0,1.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),0,0);
        RssGuiPanel.add(rssListLabel, constraints);

        rssListModel = new DefaultListModel();
        rssList = new JList(rssListModel);
        rssList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rssList.setVisibleRowCount(-1);
        scrollRssList = new JScrollPane(rssList);
        rssList.addMouseListener(new RssListMouseListener());
        constraints = new GridBagConstraints(0,1,2,1,0.0,300,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),500,300);
        RssGuiPanel.add(scrollRssList, constraints);

        btnAddRss = new JButton("Add rss");
        btnAddRss.setBounds(0, 0, 50, 50);
        constraints = new GridBagConstraints(0,2,1,1,0.0,100000,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),5,5);
        RssGuiPanel.add(btnAddRss, constraints);

        urlTextField = new JTextField("http://www.");
        constraints = new GridBagConstraints(1,2,1,1,0.0,100000,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),5,12);
        RssGuiPanel.add(urlTextField, constraints);

        btnAddRss.addActionListener(new AddRssButtonListener(urlTextField,rssListModel));

        rssDetailsLabel = new JLabel("RSS details");
        constraints = new GridBagConstraints(0,3,1,1,0.0,10000000,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0),5,12);
        RssGuiPanel.add(rssDetailsLabel, constraints);

    }

    public static Rss_GUI getInstance() {
        if (instance == null)
            instance = new Rss_GUI();
        else
            instance.setVisible(true);

        return instance;
    }
}
