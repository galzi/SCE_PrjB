package ExchangeRates;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageCurrencies extends JDialog {
    private DefaultTableModel model;
    public ManageCurrencies(final Frame owner, boolean modal) {
        super(owner, modal);
        this.setTitle("Manage Currencies");

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        JTable table = new JTable();
        JScrollPane tableContainer = new JScrollPane(table);
        this.add(tableContainer);

        model = new DefaultTableModel(new Object[]{"From", "To"},0);
        table.setModel(model);
        // get
        this.add(table);

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
                model.addRow(((ExchangeRates_GUI) owner).getSelected());

                // update server
            }
        });

        this.pack();
        this.setVisible(true);
    }
}
