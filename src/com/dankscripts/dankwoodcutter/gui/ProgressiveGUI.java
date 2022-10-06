package com.dankscripts.dankwoodcutter.gui;



import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Task;
import com.dankscripts.api.utils.Location;
import com.dankscripts.dankwoodcutter.DankWoodcutter;
import com.dankscripts.dankwoodcutter.data.TreeType;
import com.dankscripts.dankwoodcutter.nodes.*;
import com.dankscripts.dankwoodcutter.settings.UserSettings;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.function.BooleanSupplier;

public class ProgressiveGUI extends JDialog {

    private DankWoodcutter script;

    public ProgressiveGUI(DankWoodcutter script) {
        this.script = script;
        setTitle("Dank Woodcutter by novak");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 493, 214);
        getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
        setTitle("Dank Woodcutter");

        final DefaultListModel<Task> listModel = new DefaultListModel<Task>();
        JButton startButton = new JButton("Start");
        JButton btnRemove = new JButton("Remove");
        JList<Task> list = new JList<>();
        JPanel panel = new JPanel();
        JLabel treeLabel = new JLabel("Tree: ");
        JComboBox<TreeType> treeComboBox = new JComboBox(TreeType.values());
        JButton addButton = new JButton("Add Task");
        JLabel locationLabel = new JLabel("Location: ");
        JComboBox<Location> locationComboBox = new JComboBox(TreeType.values()[0].getLocations());
        JTextField levelAmountTextField = new JTextField();
        JComboBox<String> taskTypeComboBox = new JComboBox<>(new String[]{"Amount", "Level"});
        JCheckBox bankCheckBox = new JCheckBox("Bank?");
        JPanel nullPanel = new JPanel();

        getContentPane().add(panel);
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        panel.add(treeLabel);
        panel.add(treeComboBox);

        treeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeType treeType = (TreeType) treeComboBox.getSelectedItem();
                locationComboBox.setModel(new DefaultComboBoxModel<>(treeType.getLocations()));
            }
        });


        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                LinkedList<Node> nodeList = new LinkedList<>();
                UserSettings us = new UserSettings();
                us.setTreeType((TreeType) treeComboBox.getSelectedItem());
                us.setCutLocation((Location) locationComboBox.getSelectedItem());
                if (bankCheckBox.isSelected()) {
                    nodeList.add(new NestNode(script));
                    nodeList.add(new BankNode(script));
                } else
                    nodeList.add(new DropNode(script));
                if(script.getAPIContext().combat().canSpecialAttack()) {
                    nodeList.add(new DragonAxeSpecial(script));
                }
                nodeList.add(new ChopNode(script, us));
                int amountText = Integer.parseInt(levelAmountTextField.getText());
                BooleanSupplier bc = () -> {
                    if (taskTypeComboBox.getSelectedItem().equals("Amount")) {
                        return amountText == script.getNumberOfLogsObtained();
                    }
                    return script.getAPIContext().skills().woodcutting().getRealLevel() == amountText;
                };

                if (taskTypeComboBox.getSelectedItem().equals("Amount"))
                    listModel.addElement(new Task(nodeList, bc, treeComboBox.getSelectedItem().toString() + " - for " + amountText + " logs"));
                else
                    listModel.addElement(new Task(nodeList, bc, treeComboBox.getSelectedItem().toString() + " - until level:  " + amountText));


            }
        });


        panel.add(locationLabel);


        panel.add(locationComboBox);
        panel.add(taskTypeComboBox);
        panel.add(levelAmountTextField);
        panel.add(bankCheckBox);
        panel.add(nullPanel);
        panel.add(addButton);


        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < listModel.size(); i++) {
                    script.getTaskList().add(listModel.getElementAt(i));
                }
                script.addNodes();
                setVisible(false);
                dispose();

            }
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

    public void open() {
        setVisible(true);
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
