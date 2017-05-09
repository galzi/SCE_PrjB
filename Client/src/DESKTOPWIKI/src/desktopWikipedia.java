/* File name: desktopWikipedia.java
 * 
 * Purpose: This is the file which contains the main method and generates the GUI.
 * 
 * Author: yakir gaziel


 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import static java.util.stream.Collectors.toMap;

public class desktopWikipedia {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGui();
			}
		});
	}
	
	private static void createGui() { 
		JFrame frame = new JFrame("This Day In History- written by yakir gaziel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(600, 600));
		panel.setBackground(new Color(130,120,255));

	//	URL wikipediaLogo = desktopWikipedia.class.getResource("/resources/Wikipedia-logo-v2-en.png");
	//	ImageIcon icon = new ImageIcon(wikipediaLogo);
	//	JLabel picLabel = new JLabel(icon);

		final JTextField searchQuery1 = new JTextField("day");
		searchQuery1.setPreferredSize(new Dimension(50, 20));
		final JTextField searchQuery2 = new JTextField("month");
		searchQuery2.setPreferredSize(new Dimension(50, 20));
		
		 JTextArea results = new JTextArea();
		results.setLineWrap(true);
		results.setWrapStyleWord(true);
		
		JScrollPane scrollResults = new JScrollPane(results);
		scrollResults.setPreferredSize(new Dimension(500, 400));
		scrollResults.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollResults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JButton searchButton1 = new JButton("Births");
		JButton searchButton2 = new JButton("Deaths");
		JButton searchButton3 = new JButton("Events");
		searchButton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (searchQuery1.getText().trim() != null && searchQuery2.getText().trim() != null) {
					functions function = new functions();
					function.accessWikipedia(searchQuery1.getText().trim(),searchQuery2.getText().trim(),results,searchButton1.getText());


				}
			}


			
		});

				searchButton2.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						if (searchQuery1.getText().trim() != null && searchQuery2.getText().trim() != null) {
							functions function = new functions();
							function.accessWikipedia(searchQuery1.getText().trim(),searchQuery2.getText().trim(),results,searchButton2.getText());


						}
					}



				});

						searchButton3.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								if (searchQuery1.getText().trim() != null && searchQuery2.getText().trim() != null) {
									functions function = new functions();
									function.accessWikipedia(searchQuery1.getText().trim(),searchQuery2.getText().trim(),results,searchButton3.getText());


								}
							}



						}













		);
		
		//panel.add(picLabel);
		panel.add(searchQuery1);
		panel.add(searchQuery2);


		panel.add(searchButton1);
		panel.add(searchButton2);
		panel.add(searchButton3);
		panel.add(scrollResults);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
