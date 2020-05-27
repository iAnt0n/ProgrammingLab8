package gui;

import exceptions.InvalidArgumentsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
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
    private PipedWriter cmdWriter;

    private HashMap<String, Object> defaultValues;

    public EditDialog(HashMap<String, Object> defaultValues,PipedWriter cmdWriter) {
        this.defaultValues = defaultValues;
        this.cmdWriter=cmdWriter;
        ClearListener clearListener = new ClearListener();
        UpdateListener updateListener = new UpdateListener(this);
        removeElementButton.addActionListener(clearListener);
        applyChangesButton.addActionListener(updateListener);
        setContentPane(editPanel);
        climateBox1.setSelectedItem(defaultValues.get("Climate").toString());
        governmentBox2.setSelectedItem(defaultValues.get("Government").toString());
        solBox3.setSelectedItem(defaultValues.get("Standard of living").toString());
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
    public class UpdateListener implements ActionListener{
        Component comp;
        UpdateListener(Component comp){
            this.comp=comp;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                checkFields();
                cmdWriter.write("update " + idLabel.getText() + "\n");
                cmdWriter.write(nameField2.getText()+"\n");
                cmdWriter.write(xField3.getText()+"\n");
                cmdWriter.write(yField4.getText()+"\n");
                cmdWriter.write(areaField5.getText()+"\n");
                cmdWriter.write(populationField6.getText()+"\n");
                cmdWriter.write(metersField7.getText()+"\n");
                cmdWriter.write(climateBox1.getSelectedItem().toString()+"\n");
                cmdWriter.write(governmentBox2.getSelectedItem().toString()+"\n");
                cmdWriter.write(solBox3.getSelectedItem().toString()+"\n");
                cmdWriter.write(govnameField10.getText()+"\n");
                cmdWriter.write(govageField11.getText()+"\n");
                cmdWriter.write(govheiField12.getText()+"\n");
                cmdWriter.flush();
                setVisible(false);
            }catch (IOException ex){
                ex.printStackTrace();
            }
            catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(comp,ex.getMessage(),"АШИБКА БЛЯТЬ",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class ClearListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                cmdWriter.write("remove_by_id " + idLabel.getText() + "\n");
                cmdWriter.flush();
                setVisible(false);
            }catch(IOException ex ){
                ex.printStackTrace();
            }
        }
    }
    private void checkFields(){
        if (nameField2.getText().isEmpty()) throw new IllegalArgumentException("Значение поля имя не может быть пустым");
        sureLong("Х", -773, Integer.MAX_VALUE,xField3);
        sureDouble("Y", -Double.MAX_VALUE, 664, 14,yField4);
        sureDouble("площадь города (float > 0, не больше 7 цифр)", 0, Float.MAX_VALUE, 7,areaField5);
        sureLong("популяция (long > 0)", 0, Long.MAX_VALUE,populationField6);
        sureDouble("высотa (float, не более 7 цифр)", -Float.MAX_VALUE, Float.MAX_VALUE, 7,metersField7);
        if(govnameField10.getText().isEmpty()) throw new IllegalArgumentException("Имя губернатора не может быть пустым");
        sureLong("возраст губернатора (int > 0)", 0, Integer.MAX_VALUE,govageField11);
        sureDouble("рост губернатора (double > 0, не более 14 цифр)", 0, Double.MAX_VALUE, 14,govheiField12);
    }
    private void sureLong(String msg, Number min, Number max,JTextField field) {
        if (field.getText().isEmpty())
            throw new IllegalArgumentException("Значение поля " + msg + " не может быть пустым");
        try {
            long l = Long.parseLong(field.getText().trim());
            if (checkLong(l, min, max)) {
            } else {
                throw new IllegalArgumentException("Введенное вами значение поля " + msg + " не подходит по формату");
            }
        } catch (NullPointerException | NumberFormatException e) {
            throw new IllegalArgumentException("Введенное вами значение поля " + msg + " не является числом");
        }
    }
    private void sureDouble(String msg, Number min, Number max, int digits,JTextField field) {
        if (field.getText().isEmpty())
            throw new IllegalArgumentException("Значение поля " + msg + " не может быть пустым");
        try {
            String input = field.getText().trim();
            char[] digs = input.toCharArray();
            int counter = 0;
            for (char s : digs) {
                if (Character.isDigit(s)) counter++;
            }
            double d = Double.parseDouble(input);
            if (checkDouble(d, min, max) && counter <= digits) {
            } else {
                throw new IllegalArgumentException("Введенное вами значение поля " + msg + " не подходит по формату");
            }
        }catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введенное вами значение поля " + msg + " не является числом");

        }
    }
    private boolean checkDouble(double s, Number min, Number max) {
        return s>min.doubleValue()&&s<max.doubleValue();
    }
    private boolean checkLong(long s, Number min, Number max) {
        return s>min.longValue()&&s<max.longValue();
    }
}
