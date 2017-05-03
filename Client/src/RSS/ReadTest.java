package RSS;

import RSS.model.Feed;
import RSS.model.FeedMessage;
import RSS.read.RSSFeedParser;

public class ReadTest {
    public static void main(String[] args) {
        RSSFeedParser parser = new RSSFeedParser(
                "http://rss.cnn.com/rss/cnn_topstories.rss");/*http://www.vogella.com/article.rss*/
        Feed feed = parser.readFeed();
        System.out.println(feed);
        for (FeedMessage message : feed.getMessages()) {
            System.out.println(message);

        }


    }
}