package Memos;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

/**
 * Created by Mor on 30/03/2017.
 */
public class Mamo_GUI extends JFrame{

    private JButton createANewMemoButton;

    public Mamo_GUI(){
        super("Memos ");
        setLayout(new FlowLayout());
        setSize(250,400);

        add(createANewMemoButton);


        createANewMemoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                New_memo nm=new New_memo();
            }
        });
    }
}
