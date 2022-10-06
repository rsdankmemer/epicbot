package com.dankscripts.dankfletcher.gui;


import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Task;
import com.dankscripts.dankfletcher.AutoFletcherPro;
import com.dankscripts.dankfletcher.data.ActionType;
import com.dankscripts.dankfletcher.data.ArrowType;
import com.dankscripts.dankfletcher.data.BowType;
import com.dankscripts.dankfletcher.nodes.BankNode;
import com.dankscripts.dankfletcher.nodes.CutNode;
import com.dankscripts.dankfletcher.nodes.MakeArrowNode;
import com.dankscripts.dankfletcher.nodes.StringBowNode;
import com.dankscripts.dankfletcher.settings.UserSettings;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.function.BooleanSupplier;

public class ProgressiveGUI extends JDialog {

    private AutoFletcherPro script;

    public ProgressiveGUI(AutoFletcherPro script) {
        this.script = script;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 493, 214);
        getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
        setTitle("Auto Fletcher Pro");

        final DefaultListModel<Task> listModel = new DefaultListModel<Task>();
        JButton startButton = new JButton("Start");
        JButton btnRemove = new JButton("Remove");
        JList<Task> list = new JList<>();
        JPanel panel = new JPanel();
        JLabel actionLabel = new JLabel("Action: ");
        JComboBox<ActionType> actionTypeComboBox = new JComboBox<>(ActionType.values());
        JButton addButton = new JButton("Add Task");
        JLabel itemLabel = new JLabel("Item: ");
        JComboBox itemComboBox = new JComboBox<>(BowType.values());
        JTextField levelAmountTextField = new JTextField();
        JComboBox<String> taskTypeComboBox = new JComboBox<>(new String[]{"Amount", "Level"});
        JPanel nullPanel = new JPanel();

        getContentPane().add(panel);
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        panel.add(actionLabel);
        panel.add(actionTypeComboBox);

        actionTypeComboBox.addActionListener(e -> {
            ActionType actionType = (ActionType)actionTypeComboBox.getSelectedItem();
            itemComboBox.removeAllItems();
            if(actionType.equals(ActionType.CUT_BOWS)) {
                itemComboBox.setModel(new DefaultComboBoxModel<>(BowType.values()));
            } else if(actionType.equals(ActionType.STRING_BOWS)) {
                for(int i = 1; i < BowType.values().length - 1; i++) {
                    itemComboBox.addItem(BowType.values()[i]);
                }
            } else if(actionType.equals(ActionType.MAKE_ARROWS)) {
                for(int i = 1; i < ArrowType.values().length - 1; i++) {
                    itemComboBox.addItem(ArrowType.values()[i]);
                }
            } else if(actionType.equals(ActionType.MAKE_DARTS)){

            }
        });


        addButton.addActionListener(e -> {
            LinkedList<Node> nodeList = new LinkedList<>();
            UserSettings us = new UserSettings();
            us.setActionType((ActionType) actionTypeComboBox.getSelectedItem());
            if(actionTypeComboBox.getSelectedItem().equals(ActionType.CUT_BOWS)) {
                us.setBowType((BowType) itemComboBox.getSelectedItem());
                nodeList.add(new BankNode(script, us));
                nodeList.add(new CutNode(script, us));
            } else if(actionTypeComboBox.getSelectedItem().equals(ActionType.STRING_BOWS)) {
                us.setBowType((BowType) itemComboBox.getSelectedItem());
                nodeList.add(new BankNode(script, us));
                nodeList.add(new StringBowNode(script, us));
            } else if(actionTypeComboBox.getSelectedItem().equals(ActionType.MAKE_ARROWS)) {
                us.setArrowType((ArrowType) itemComboBox.getSelectedItem());
                nodeList.add(new BankNode(script, us));
                nodeList.add(new MakeArrowNode(script, us));
            } else if(actionTypeComboBox.getSelectedItem().equals(ActionType.MAKE_DARTS)){

            }
            int amountText = Integer.parseInt(levelAmountTextField.getText());
            BooleanSupplier bc = () -> {
                if (taskTypeComboBox.getSelectedItem().equals("Amount")) {
                    return amountText == script.getActionsPerformed();
                }
                return script.getAPIContext().skills().fletching().getRealLevel() == amountText;
            };
            listModel.addElement(new Task(nodeList, bc, itemComboBox.getSelectedItem().toString()));
        });


        panel.add(itemLabel);
        panel.add(itemComboBox);
        panel.add(taskTypeComboBox);
        panel.add(levelAmountTextField);
        panel.add(nullPanel);
        panel.add(addButton);


        startButton.addActionListener(e -> {
            for (int i = 0; i < listModel.size(); i++) {
                script.getTaskList().add(listModel.getElementAt(i));
            }
            script.addNodes();
            setVisible(false);
            dispose();

        });
        panel.add(startButton);

        JPanel panel_1 = new JPanel();
        getContentPane().add(panel_1);
        panel_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        list.setBounds(100, 100, 100, 100);
        panel_1.setLayout(new BorderLayout(0, 0));
        list.setModel(listModel);
        list.setCellRenderer(new MyCellRenderer());
        panel_1.add(list, BorderLayout.CENTER);


        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (list.getSelectedValue() != null) {
                    int index = list.getSelectedIndex();
                    if (index >= 0) {
                        listModel.removeElementAt(index);
                    }
                }

            }
        });
        panel_1.add(btnRemove, BorderLayout.SOUTH);

    }

    public void start() {
        EventQueue.invokeLater(() -> {
            try {
                ProgressiveGUI frame = new ProgressiveGUI(script);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}

class MyCellRenderer extends JLabel implements ListCellRenderer {
    //final ImageIcon longIcon = new ImageIcon("long.gif");
    //final ImageIcon shortIcon = new ImageIcon("short.gif");

    // This is the only method defined by ListCellRenderer.
    // We just reconfigure the JLabel each time we're called.

    public Component getListCellRendererComponent(
            JList list,              // the list
            Object value,            // value to display
            int index,               // cell index
            boolean isSelected,      // is the cell selected
            boolean cellHasFocus)    // does the cell have focus
    {
        String s = value.toString();
        setText(s);
        //setIcon((s.length() > 10) ? longIcon : shortIcon);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}
