package RSS;

import Comm.HttpUrlConnection;
import RSS.model.Feed;
import RSS.model.FeedMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Or on 5/3/2017.
 */
public class RssListMouseListener implements MouseListener {

    private JPopupMenu rssListPopMenu;
    private JMenuItem openRssItem;
    private JMenuItem deleteRssItem;
    private ArrayList<Feed> feedList;
    private DefaultListModel feedMessageListModel;
    private JList rssList;
    private ArrayList<String> feedLinks;

    public RssListMouseListener(ArrayList<Feed> feedList, DefaultListModel feedMessageListModel,JList rssList,ArrayList<String> feedLinks){
        this.feedList = feedList;
        this.feedMessageListModel = feedMessageListModel;
        this.rssList = rssList;
        this.feedLinks = feedLinks;
        rssListPopMenu = new JPopupMenu();
        openRssItem = new JMenuItem("Open RSS");
        buildOpenRssListener();
        deleteRssItem = new JMenuItem("Delete RSS");
        buildDeleteRssListener();
        rssListPopMenu.add(openRssItem);
        rssListPopMenu.add(deleteRssItem);
    }

    public void buildOpenRssListener(){
        openRssItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = rssList.getSelectedIndex();
                showRssMessagesInJList(row);
            }
        });
    }

    public void buildDeleteRssListener(){
        deleteRssItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JList rssList = (JList)e.getSource();
        if(isPressedOnItem(e)) {

            if (SwingUtilities.isRightMouseButton(e)) {
                rssListPopMenu.show(e.getComponent(), e.getX(), e.getY());
            }

            else if(SwingUtilities.isLeftMouseButton(e)){
                if(e.getClickCount()==2){
                    //feedMessageListModel.clear();
                    int row = rssList.locationToIndex(e.getPoint());
                    showRssMessagesInJList(row);
                    //for (FeedMessage message : feedList.get(row).getMessages()) {
                      //  feedMessageListModel.addElement(message);
                        //System.out.println(message);
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

            //feedMessageListModel.addElement(message);
            //System.out.println(message);
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
