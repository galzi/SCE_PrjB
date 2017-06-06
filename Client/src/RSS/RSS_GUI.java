package RSS;

import Comm.Comm;
import Comm.HttpUrlConnection;
import RSS.model.Feed;
import RSS.read.RSSFeedParser;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


import javax.swing.JFrame;
import javax.swing.JButton;

import static Comm.Comm.toMap;


/**
 * Created by Or on 4/23/2017.
 */
public class RSS_GUI extends JFrame {

    private static RSS_GUI instance = null;
    private Container RssGuiPanel;
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


    public RSS_GUI() {
        super("RssGUI");
        setVisible(true);
        setBounds(50, 50, 700, 600);

        RssGuiPanel = new Container();
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

        urlTextField = new JTextField("http://rss.cnn.com/rss/cnn_topstories.rss");
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

        String URLs = null;
        try {
            URLs = HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost+"rss/?action=get");
            System.out.println(URLs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> URLmap = toMap(URLs);

        for (Object o : (ArrayList<Object>) URLmap.get("url")) {
            System.out.println(o.toString());

            RSSFeedParser parser = new RSSFeedParser(o.toString());
            Feed feed = parser.readFeed();
            rssListModel.addElement(feed.getDescription());
            feedList.add(feed);
        }
    }

    public static RSS_GUI getInstance() {
        if (instance == null)
            instance = new RSS_GUI();
        else
            instance.setVisible(true);

        return instance;
    }
}
