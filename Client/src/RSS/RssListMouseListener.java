package RSS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Or on 5/3/2017.
 */
public class RssListMouseListener implements MouseListener {

    private JPopupMenu rssListPopMenu;
    private JMenuItem openRssItem;
    private JMenuItem deleteRssItem;

    public RssListMouseListener(){
        rssListPopMenu = new JPopupMenu();
        openRssItem = new JMenuItem("Open RSS");
        deleteRssItem = new JMenuItem("Delete RSS");
        rssListPopMenu.add(openRssItem);
        rssListPopMenu.add(deleteRssItem);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JList rssList = (JList)e.getSource();
        if(isPressedOnItem(e)) {
            if (SwingUtilities.isRightMouseButton(e)) {
                rssListPopMenu.show(e.getComponent(), e.getX(), e.getY());
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

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
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
}
