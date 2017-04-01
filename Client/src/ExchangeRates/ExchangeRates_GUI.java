package ExchangeRates;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mor on 30/03/2017.
 */
public class ExchangeRates_GUI extends JFrame {

    private JTextField textField2;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton convertValuesButton;

    public ExchangeRates_GUI(){
        super("Exchange Rates");
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(250,400);
    }
}
