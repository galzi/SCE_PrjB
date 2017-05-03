package RSS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Or on 5/3/2017.
 */
public class RssListMouseListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        JList list = (JList)e.getSource();
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
}
