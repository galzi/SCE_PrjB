package OnThisDay;

import Comm.HttpUrlConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static Comm.Comm.toMap;

public class OnThisDay_GUI extends JFrame {
    private JPanel list;
    private JScrollPane _list;
    private ArrayList<JLabel> labels = new ArrayList<JLabel>();
    private String month, day;

    public OnThisDay_GUI() throws HeadlessException {
        this.list = null;
        String today = (new SimpleDateFormat("dd/MM")).format(new Date());

        this.day = today.split("/")[0];
        this.month = today.split("/")[1];

        this.setTitle("On this day... " + today);

        this.setLayout(new BorderLayout());
        JPanel controls = new JPanel(new FlowLayout());
        JButton births = new JButton("Births");
        JButton deaths = new JButton("Deaths");
        JButton events = new JButton("Events");
        controls.add(births);
        controls.add(deaths);
        controls.add(events);
        this.add(controls, BorderLayout.NORTH);

        this.list = new JPanel();
        this.list.setBackground(Color.WHITE);
        this.list.setLayout(new BoxLayout(this.list, BoxLayout.PAGE_AXIS));
        _list = new JScrollPane(this.list);
        this.add(this._list, BorderLayout.CENTER);

        births.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanScreen();
                extractData("births");
            }
        });

        deaths.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanScreen();
                extractData("deaths");
            }
        });

        events.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanScreen();
                extractData("events");
            }
        });

        this.setLocationRelativeTo(null);
        this.setSize(600, 300); // this.pack();
        this.setVisible(true);
    }

    private void cleanScreen() {
        for (JLabel l : this.labels) {
            this.list.remove(l);
        }
        this.repaint();
    }

    private void extractData(String type) {
        Map<String, Object> Events = null;
        try {
            Events = toMap(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost + "history/?type=" + type + "&month=" + this.month + "&day=" + this.day));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        for (Object o : (ArrayList<Object>) Events.get(type)) {
            JLabel label = new JLabel("\u2022 " + ((Map) o).get("year").toString() + ": " + ((Map) o).get("text").toString());
            this.list.add(label);
            this.labels.add(label);
        }

        this.validate();
        this.repaint();
    }

    public static void main(String[] args) {
        new OnThisDay_GUI();
    }
}