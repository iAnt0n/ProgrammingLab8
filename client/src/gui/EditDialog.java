package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EditDialog extends JDialog {
    private JTextField keyField1;
    private JTextField nameField2;
    private JTextField xField3;
    private JTextField yField4;
    private JTextField areaField5;
    private JTextField populationField6;
    private JTextField metersField7;
    private JTextField govnameField10;
    private JTextField govageField11;
    private JTextField govheiField12;
    private JPanel editPanel;
    private JComboBox climateBox1;
    private JComboBox governmentBox2;
    private JComboBox solBox3;
    private JLabel ownerLabel;
    private JLabel timeLabel;
    private JButton applyChangesButton;
    private JButton removeElementButton;
    private JLabel idLabel;

    private HashMap<String, Object> defaultValues;

    public EditDialog(HashMap<String, Object> defaultValues) {
        this.defaultValues = defaultValues;
        setContentPane(editPanel);
        climateBox1.setSelectedItem(defaultValues.get("Climate").toString());
        governmentBox2.setSelectedItem(defaultValues.get("Government").toString());
//        solBox3.setSelectedItem(defaultValues.get("Standard of living").toString());
        ownerLabel.setText(defaultValues.get("Owner").toString());
        timeLabel.setText(defaultValues.get("Creation Time").toString());
        idLabel.setText(defaultValues.get("Id").toString());
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createUIComponents() {
        keyField1 = new JTextField(defaultValues.get("Key").toString());
        nameField2 = new JTextField(defaultValues.get("Name").toString());
        xField3 = new JTextField(defaultValues.get("X").toString());
        yField4 = new JTextField(defaultValues.get("Y").toString());
        areaField5 = new JTextField(defaultValues.get("Area").toString());
        populationField6 = new JTextField(defaultValues.get("Population").toString());
        metersField7 = new JTextField(defaultValues.get("Meters Above Sea Level").toString());
        govnameField10 = new JTextField(defaultValues.get("Governor Name").toString());
        govageField11 = new JTextField(defaultValues.get("Governor Age").toString());
        govheiField12 = new JTextField(defaultValues.get("Governor Height").toString());
    }
}
