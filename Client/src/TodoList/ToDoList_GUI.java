package TodoList;

import Comm.HttpUrlConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

import static Comm.Comm.toMap;

public class ToDoList_GUI extends JFrame {
    private ArrayList<JCheckBox> done = new ArrayList<JCheckBox>();
    private ArrayList<JLabel> tasks = new ArrayList<JLabel>();
    private ArrayList<JButton> remove = new ArrayList<JButton>();
    private JPanel pan = new JPanel(new GridLayout(0, 3));

    public ToDoList_GUI() {
        this.setTitle("ToDo List");
        this.setLayout(new BorderLayout());

        this.add(this.pan, BorderLayout.CENTER);

        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem(false, false, null);
            }
        });

        // get items from user
        Map<String, Object> toDoList = null;
        try {
            toDoList = toMap(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost + "todo/?action=get"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object o : (ArrayList<Object>) toDoList.get("list")) {
            System.out.println(o.toString());
            addItem(true, ((Map) o).get("check").toString().equals("true"), ((Map) o).get("content").toString());

        }

        this.add(add, BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addItem(boolean isFromServer, boolean check, String task) {
        final JCheckBox checkBox = new JCheckBox();
        final JLabel label = new JLabel();
        final JButton button = new JButton("-");

        if (isFromServer) {
            checkBox.setSelected(check);
            label.setText(task);
        } else {
            label.setText(JOptionPane.showInputDialog("Task"));
        }

        ToDoList_GUI.this.done.add(checkBox);
        ToDoList_GUI.this.tasks.add(label); // server
        ToDoList_GUI.this.remove.add(button);

        ToDoList_GUI.this.pan.add(checkBox);
        ToDoList_GUI.this.pan.add(label);
        ToDoList_GUI.this.pan.add(button);

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        System.out.println(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost + "todo/?action=check&content=" + label.getText()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    try {
                        System.out.println(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost + "todo/?action=uncheck&content=" + label.getText()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost + "todo/?action=del&content=" + label.getText()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                ToDoList_GUI.this.pan.remove(checkBox);
                ToDoList_GUI.this.pan.remove(label);
                ToDoList_GUI.this.pan.remove(button);
            }
        });

        try {
            System.out.println(HttpUrlConnection.GetPageContent(HttpUrlConnection.serverHost + "todo/?action=add&content=" + label.getText()));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        ToDoList_GUI.this.repaint();
        ToDoList_GUI.this.pack();
    }

    public static void main(String[] args) {
        new ToDoList_GUI();
    }
}
