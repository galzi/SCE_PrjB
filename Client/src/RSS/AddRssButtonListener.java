package RSS;

import RSS.model.Feed;
import RSS.model.FeedMessage;
import RSS.read.RSSFeedParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Or on 5/3/2017.
 */
public class AddRssButtonListener implements ActionListener {

    private JTextField urlTextField;
    private DefaultListModel rssListModel;
    private JFrame frame;


    public AddRssButtonListener(JTextField urlTextField, DefaultListModel rssListModel){
        //is this good build?
        this.urlTextField = urlTextField;
        this.rssListModel = rssListModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String urlFeed = urlTextField.getText();
        try {
            RSSFeedParser parser = new RSSFeedParser(urlFeed);
            Feed feed = parser.readFeed();
            rssListModel.addElement(feed.getDescription());
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
}
