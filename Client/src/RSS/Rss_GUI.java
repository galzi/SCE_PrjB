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

    public Rss_GUI() {
        super("AddRss");
        setVisible(true);
        setBounds(50, 50, 700, 500);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(525,400));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        //scrollPane.setBounds(50, 30, 525, 50);
        setContentPane(scrollPane);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel rssListLabel = new JLabel("RSS list");
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 0;

        panel.add(rssListLabel,c);

        DefaultListModel listModel = new DefaultListModel();
        JList rssList = new JList(listModel);
        rssList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rssList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(rssList);
        rssList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList)e.getSource();
                if(e.getClickCount()==2)



                    if(isPressedOnItem(e)) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            JPopupMenu pop = new JPopupMenu();
                            JMenuItem openRssItem = new JMenuItem("Open RSS");
                            JMenuItem deleteRssItem = new JMenuItem("Delete RSS");
                            pop.add(openRssItem);
                            pop.add(deleteRssItem);
                            pop.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
            }


            @Override
            public void mousePressed(MouseEvent e) {
                JList list = (JList)e.getSource();
                if(isPressedOnItem(e)) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        int row = list.locationToIndex(e.getPoint());
                        list.setSelectedIndex(row);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressedOnItem(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            private boolean isPressedOnItem(MouseEvent e){
                JList list = (JList)e.getSource();
                if(list.getModel().getSize()>0) {
                    Rectangle r = list.getCellBounds(list.getFirstVisibleIndex(), list.getLastVisibleIndex());
                    if (e.getPoint().getY() > r.getY() && e.getPoint().getY() < r.getMaxY()) {
                        return true;
                    }
                }
                list.clearSelection();
                return false;
            }
        });

        c.ipady = 300;
        c.ipadx = 500;
        c.weighty = 20;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(listScroller,c);

        JButton btnAddRss = new JButton("Add rss");
        btnAddRss.setBounds(0,0,50,50);
        c.ipady = 5;
        c.ipadx = 5;
        c.gridwidth=1;
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 100000;
        panel.add(btnAddRss,c);

        JTextField urlTextField = new JTextField("http://www.");
        urlTextField.setPreferredSize(new Dimension(300,30));
        c.gridx=1;
        c.gridy=2;
        c.gridwidth = 1;
        panel.add(urlTextField,c);

        btnAddRss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String urlFeed = urlTextField.getText();
                try {
                    RSSFeedParser parser = new RSSFeedParser(urlFeed);
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
                            "error",
                            JOptionPane.ERROR_MESSAGE);
                    urlTextField.setText("");
                    return;
                }

            }
        });

        JLabel rssDetailsLabel = new JLabel("RSS details");
        c.gridy = 3;
        c.gridx = 1;
        c.weighty = 10000000;
        panel.add(rssDetailsLabel,c);


    }

    public static Rss_GUI getInstance(){
        if(instance == null)
            instance = new Rss_GUI();
        else
            instance.setVisible(true);

        return instance;
    }






}
