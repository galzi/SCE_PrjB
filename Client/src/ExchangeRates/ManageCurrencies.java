package ExchangeRates;

import Comm.HttpUrlConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import static Comm.Comm.toMap;

public class ManageCurrencies extends JFrame {
    private DefaultTableModel model;
    public ManageCurrencies(final Frame owner) {
        this.setTitle("Manage Currencies");

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        final JTable table = new JTable();
        JScrollPane tableContainer = new JScrollPane(table);
        this.add(tableContainer);

        model = new DefaultTableModel(new Object[]{"From", "To"},0);
        table.setModel(model);
        JScrollPane scrollTable = new JScrollPane(table);
        // get

        Map<String, Object> data = null;
        try {
            data = toMap(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost + "exchange/?action=get"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        System.out.println(data);

        for (Object o : (ArrayList<Object>) data.get("list")) {
            o = ((Map) o).get("curr");
            model.addRow(new Object[]{((ExchangeRates_GUI) owner).getToName().get(((Map) o).get("source").toString()).toString(), ((ExchangeRates_GUI) owner).getToName().get(((Map) o).get("destination").toString()).toString()});
        }

        this.add(scrollTable);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton select = new JButton("Select");

        panel.add(add);
        panel.add(remove);
        panel.add(select);
        this.add(panel);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] curr = ((ExchangeRates_GUI) owner).getSelected();

                // update server
                Map<String, Object> result = null;
                try {
                    result = toMap(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost + "exchange/?action=add&source=" + curr[0] + "&destination=" + curr[1]));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if (result.get("status").toString().equals("Success")) {
                    model.addRow(new String[]{((ExchangeRates_GUI) owner).getToName().get(curr[0]).toString(), ((ExchangeRates_GUI) owner).getToName().get(curr[1]).toString()});
                } else {
                    JOptionPane.showMessageDialog(owner, result.get("status").toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<String> curr = (Vector<String>) model.getDataVector().elementAt(table.getSelectedRow());

                try {
                    System.out.println(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost + "exchange/?action=del&source=" + ((ExchangeRates_GUI) owner).getToAcronym().get(curr.get(0)) + "&destination=" + ((ExchangeRates_GUI) owner).getToAcronym().get(curr.get(1))));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                model.removeRow(table.getSelectedRow());
            }
        });

        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<String> curr = (Vector<String>) model.getDataVector().elementAt(table.getSelectedRow());
                ((ExchangeRates_GUI) owner).setSelected(curr.get(0), curr.get(1));
            }
        });

        this.pack();
        this.setVisible(true);
    }
}
