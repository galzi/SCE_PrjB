package ExchangeRates;

// import org.json.*;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

import Comm.Comm;

public class ExchangeRates_GUI extends JFrame {
    private JTextField amountLbl;
    JLabel resultLabel;

    private JComboBox sourceCurr;
    private JComboBox destinationCurr;
    private double conversionValue = 1.0;
    private double amount = 1.0;

    private String[] currencies;
    private Map<String, String> toAcronym = new HashMap<>();

    public ExchangeRates_GUI() {
        getCurrencyList();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        amountLbl = new JTextField();
        amountLbl.setColumns(10);
        sourceCurr = new JComboBox(this.currencies); // (String[]) this.currencies.toArray()
        destinationCurr = new JComboBox(this.currencies); // (String[]) this.currencies.toArray()
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new FlowLayout());
        dataPanel.add(amountLbl);
        dataPanel.add(sourceCurr);
        dataPanel.add(destinationCurr);
        panel.add(dataPanel, BorderLayout.NORTH);

        JButton convert = new JButton("Convert");
        convert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String source = toAcronym.get((String) sourceCurr.getSelectedItem());
                String destination = toAcronym.get((String) destinationCurr.getSelectedItem());

                conversionValue = convertCurrency(source, destination);
                amount = Double.parseDouble(amountLbl.getText());
                resultLabel.setText(String.valueOf(amount * conversionValue));
            }
        });
        panel.add(convert, BorderLayout.CENTER);

        resultLabel = new JLabel(" ");
        panel.add(resultLabel, BorderLayout.SOUTH);

        this.add(panel);
        this.pack();
    }

    public static double convertCurrency(String source, String destination) {
        Map<String, Object> values = Comm.toMap(Comm.GetURLContent("http://apilayer.net/api/live?access_key=5a9785bc12c18412ea75e910dd525285&currencies=" + source + "," + destination));

        /*
        source: USD -> SOURCE
        destination: USD -> DESTINATION

        source ^ -1: SOURCE -> USD

        destination of source ^ -1: SOURCE -> DESTINATION
        (destination of source ^ -1)(x) = destination(source ^ -1(x))

        functions are in form of y = ax (bijective function)
        therefore, x = y / a (a != 0, always true)
        => f(x) = a * x <=> f ^ -1(x) = (1 / a) * x
        */

        return Double.parseDouble(String.valueOf(((Map) values.get("quotes")).get(String.valueOf(values.get("source")) + destination))) * (1 / Double.parseDouble(String.valueOf(((Map) values.get("quotes")).get(String.valueOf(values.get("source")) + source))));
    }

    public void getCurrencyList() {
        Map<String, Object> map = (Map) Comm.toMap(Comm.GetURLContent("http://apilayer.net/api/list?access_key=5a9785bc12c18412ea75e910dd525285")).get("currencies");
        String[] newList = new String[map.size() + 1];

        newList[0] = "";
        int i = 0;

        for (Map.Entry<String, Object> o : map.entrySet()) {
            newList[i++ + 1] = String.valueOf(o.getValue());
            this.toAcronym.put(String.valueOf(o.getValue()), o.getKey());
        }

        this.currencies = newList;
    }

    public static void main(String[] args) {
        new ExchangeRates_GUI();
    }
}
