


package ExchangeRates;
/**
 * Created by Mor on 09/05/2017.
 */

        import org.json.*;


        import java.awt.BorderLayout;
        import java.awt.EventQueue;

        import javax.swing.*;
        import java.awt.event.ActionListener;
        import java.awt.event.ActionEvent;
        import java.awt.event.*;
        import java.io.IOException;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.Scanner;

public class ExchangeRates extends JFrame {

    private JTextField textField;
    JLabel resultLabel;

    private  JComboBox comboBox ;
    private  JComboBox comboBox_1 ;
    private double Afterconversion=1.0 ;
    private double coin=1.0;

    public ExchangeRates() throws MalformedURLException {


        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setSize(600,800);
        getContentPane().add(layeredPane, BorderLayout.CENTER);
        layeredPane.setVisible(true);


        textField = new JTextField();
        textField.setBounds(181, 72, 86, 20);
        layeredPane.add(textField);
        textField.setColumns(10);




        JButton btnNewButton = new JButton("Convert");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                String c1 =(String) comboBox.getSelectedItem();
                String c2 =(String) comboBox_1.getSelectedItem();


                try {
                    Afterconversion=(double) geocoding(c1,c2);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                coin =  Double.parseDouble( textField.getText());
                double anser =coin*Afterconversion;
                resultLabel.setText(String.valueOf(anser));



            }
        });
        btnNewButton.setBounds(297, 71, 89, 23);
        layeredPane.add(btnNewButton);

        resultLabel = new JLabel("value");
        resultLabel.setBounds(181, 125, 108, 45);
        layeredPane.add(resultLabel);



        String coin1List[] = {"EUR", "ILS", "USD"};
        comboBox = new JComboBox(coin1List);
        comboBox.setBounds(63, 45, 108, 20);
        layeredPane.add(comboBox);




        String coin2List[] = {"ILS", "EUR", "USD"};
        comboBox_1 = new JComboBox(coin2List);
        comboBox_1.setBounds(63, 90, 108, 20);
        layeredPane.add(comboBox_1);


    }

    public static double geocoding (String addr,String c2) throws Exception
    {
        // build a URL

        String url1 = "http://api.fixer.io/latest?base=";
        URL url = new URL(url1+addr);

        // read from the URL
        Scanner scan = new Scanner(url.openStream());
        String str = new String();
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();

        // build a JSON object
        JSONObject obj = new JSONObject(str);
        if (!obj.getString("base").equals(addr))
            return 0;

        // get the first result

        JSONObject loc = obj.getJSONObject("rates");
        double q = loc.getDouble(c2);
        System.out.println(q);
        return q;
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ExchangeRates frame = new ExchangeRates();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
