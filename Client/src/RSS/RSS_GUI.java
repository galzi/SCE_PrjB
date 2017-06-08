package RSS;

import Comm.Comm;
import Comm.HttpUrlConnection;
import RSS.model.Feed;
import RSS.model.FeedMessage;
import RSS.read.RSSFeedParser;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


import javax.swing.JFrame;
import javax.swing.JButton;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Marshaller.Listener;

import static Comm.Comm.toMap;


/**
 * Created by Or on 4/23/2017.
 */
public class RSS_GUI extends JFrame implements ActionListener , MouseListener {

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

    private JPopupMenu rssListPopMenu;
    private JMenuItem openRssItem;
    private JMenuItem deleteRssItem;


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

        rssListPopMenu = new JPopupMenu();
        openRssItem = new JMenuItem("Open RSS");
        //buildOpenRssListener();
        deleteRssItem = new JMenuItem("Delete RSS");
        //buildDeleteRssListener();
        rssListPopMenu.add(openRssItem);
        rssListPopMenu.add(deleteRssItem);

        openRssItem.addActionListener(this);
        deleteRssItem.addActionListener(this);
        btnAddRss.addActionListener(this);
        rssList.addMouseListener(this);
        rssMessages.addMouseListener(this);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAddRss) {
            String urlFeed = urlTextField.getText();
            try {
                System.out.println(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost + "rss/?action=add&content=" + urlFeed));
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            try {
                //http://rssfeeds.usatoday.com/usatoday-NewsTopStories //error
                RSSFeedParser parser = new RSSFeedParser(urlFeed);
                Feed feed = parser.readFeed();
                rssListModel.addElement(feed.getDescription());
                feedList.add(feed);
            } catch (Exception el) {

                JOptionPane.showMessageDialog(this,
                        "This is not rss",
                        "error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if(e.getSource() == openRssItem){
            int row = rssList.getSelectedIndex();
            showRssMessagesInJList(row);
        }
        else if(e.getSource() == deleteRssItem){
            int row = rssList.getSelectedIndex();
            System.out.println(row);

            try {
                System.out.println(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost+"rss/?action=del&content="+feedList.get(row).getLink()));
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            ((DefaultListModel)rssList.getModel()).remove(row);
            feedList.remove(row);
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == rssMessages){
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
        else if(e.getSource() == rssList) {
            JList rssList = (JList) e.getSource();
            if (isPressedOnItem(e)) {

                if (SwingUtilities.isRightMouseButton(e)) {
                    rssListPopMenu.show(e.getComponent(), e.getX(), e.getY());
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    if (e.getClickCount() == 2) {

                        int row = rssList.locationToIndex(e.getPoint());
                        showRssMessagesInJList(row);
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JList rssList = (JList)e.getSource();
        if(isPressedOnItem(e)) {
            if (SwingUtilities.isRightMouseButton(e)) {
                int row = rssList.locationToIndex(e.getPoint());
                rssList.setSelectedIndex(row);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isPressedOnItem(e);
    }

    private void showRssMessagesInJList(int rssIndex){
        feedMessageListModel.clear();
        feedLinks.clear();
        for (FeedMessage message : feedList.get(rssIndex).getMessages()) {
            feedLinks.add(message.getLink());
            String msg = new String();
            msg+="Title: " + message.getTitle() +"\n Description: "+ message.getDescription();
            feedMessageListModel.addElement(msg);
        }
    }

    private boolean isPressedOnItem(MouseEvent e){
        JList rssList = (JList)e.getSource();
        if(rssList.getModel().getSize()>0) {
            Rectangle r = rssList.getCellBounds(rssList.getFirstVisibleIndex(), rssList.getLastVisibleIndex());
            if (e.getPoint().getY() > r.getY() && e.getPoint().getY() < r.getMaxY()) {
                return true;
            }
        }
        rssList.clearSelection();
        return false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
