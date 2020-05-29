package gui;

import collection.City;
import exceptions.InvalidArgumentsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditDialog extends JDialog {
    private JLabel keyField1;
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
    private JLabel ownerLocLabel;
    private JLabel timeLocLabel;
    private JLabel idLocLabel;
    private JLabel keyLabel;
    private JLabel nameLabel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel areaLabel;
    private JLabel populationLabel;
    private JLabel metersLabel;
    private JLabel climateLabel;
    private JLabel solLabel;
    private JLabel governmentLabel;
    private JLabel govnameLabel;
    private JLabel govageLabel;
    private JLabel govHeiLabel;
    private PipedWriter cmdWriter;

    private HashMap<String, Object> defaultValues;

    public EditDialog(HashMap<String, Object> defaultValues, PipedWriter cmdWriter, ResourceBundle res) {
        setModal(true);
        this.defaultValues = defaultValues;
        this.cmdWriter = cmdWriter;
        ClearListener clearListener = new ClearListener();
        UpdateListener updateListener = new UpdateListener(this);
        removeElementButton.addActionListener(clearListener);
        applyChangesButton.addActionListener(updateListener);
        setContentPane(editPanel);
        if (defaultValues.get(res.getString("climate"))!=null) {
            climateBox1.setSelectedItem(defaultValues.get(res.getString("climate")).toString());
        } else climateBox1.setSelectedItem("");
        governmentBox2.setSelectedItem(defaultValues.get(res.getString("government")).toString());
        if (defaultValues.get(res.getString("sol"))!=null) {
            solBox3.setSelectedItem(defaultValues.get(res.getString("sol")).toString());
        } else solBox3.setSelectedItem("");
        ownerLabel.setText(defaultValues.get(res.getString("owner")).toString());
        timeLabel.setText(defaultValues.get(res.getString("time")).toString());
        idLabel.setText(defaultValues.get(res.getString("id")).toString());
        keyField1.setText(defaultValues.get(res.getString("key")).toString());
        nameField2.setText(defaultValues.get(res.getString("name")).toString());
        xField3.setText(defaultValues.get(res.getString("x")).toString());
        yField4.setText(defaultValues.get(res.getString("y")).toString());
        areaField5.setText(defaultValues.get(res.getString("area")).toString());
        populationField6.setText(defaultValues.get(res.getString("population")).toString());
        metersField7.setText(defaultValues.get(res.getString("meters")).toString());
        govnameField10.setText(defaultValues.get(res.getString("govname")).toString());
        govageField11.setText(defaultValues.get(res.getString("govage")).toString());
        govheiField12.setText(defaultValues.get(res.getString("govhei")).toString());

        ownerLocLabel.setText(res.getString("owner"));
        timeLocLabel.setText(res.getString("time"));
        idLocLabel.setText(res.getString("id"));
        keyLabel.setText(res.getString("key"));
        nameLabel.setText(res.getString("name"));
        xLabel.setText(res.getString("x"));
        yLabel.setText(res.getString("y"));
        areaLabel.setText(res.getString("area"));
        populationLabel.setText(res.getString("population"));
        metersLabel.setText(res.getString("meters"));
        climateLabel.setText(res.getString("climate"));
        solLabel.setText(res.getString("sol"));
        governmentLabel.setText(res.getString("government"));
        govnameLabel.setText(res.getString("govname"));
        govageLabel.setText(res.getString("govage"));
        govHeiLabel.setText(res.getString("govhei"));

        applyChangesButton.setText(res.getString("applyChanges"));
        removeElementButton.setText(res.getString("removeElement"));

        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public EditDialog(String key, City city, PipedWriter cmdWriter, ResourceBundle res){
        setModal(true);
        this.cmdWriter = cmdWriter;
        ClearListener clearListener = new ClearListener();
        UpdateListener updateListener = new UpdateListener(this);
        removeElementButton.addActionListener(clearListener);
        applyChangesButton.addActionListener(updateListener);
        setContentPane(editPanel);
        if (city.getClimate()!=null) {
            climateBox1.setSelectedItem(city.getClimate().toString());
        } else climateBox1.setSelectedItem("");
        governmentBox2.setSelectedItem(city.getGovernment().toString());
        if (city.getStandardOfLiving()!=null) {
            solBox3.setSelectedItem(city.getStandardOfLiving().toString());
        } else solBox3.setSelectedItem("");
        ownerLabel.setText(city.getUser());
        timeLabel.setText(city.getCreationDate().toString());
        idLabel.setText(String.valueOf(city.getId()));
        keyField1.setText(key);
        nameField2.setText(city.getName());
        xField3.setText(String.valueOf(city.getCoordinates().getX()));
        yField4.setText(String.valueOf(city.getCoordinates().getY()));
        areaField5.setText(String.valueOf(city.getArea()));
        populationField6.setText(String.valueOf(city.getPopulation()));
        metersField7.setText(String.valueOf(city.getMetersAboveSeaLevel()));
        govnameField10.setText(city.getGovernor().getName());
        govageField11.setText(String.valueOf(city.getGovernor().getAge()));
        govheiField12.setText(String.valueOf(city.getGovernor().getHeight()));

        ownerLocLabel.setText(res.getString("owner"));
        timeLocLabel.setText(res.getString("time"));
        idLocLabel.setText(res.getString("id"));
        keyLabel.setText(res.getString("key"));
        nameLabel.setText(res.getString("name"));
        xLabel.setText(res.getString("x"));
        yLabel.setText(res.getString("y"));
        areaLabel.setText(res.getString("area"));
        populationLabel.setText(res.getString("population"));
        metersLabel.setText(res.getString("meters"));
        climateLabel.setText(res.getString("climate"));
        solLabel.setText(res.getString("sol"));
        governmentLabel.setText(res.getString("government"));
        govnameLabel.setText(res.getString("govname"));
        govageLabel.setText(res.getString("govage"));
        govHeiLabel.setText(res.getString("govhei"));

        applyChangesButton.setText(res.getString("applyChanges"));
        removeElementButton.setText(res.getString("removeElement"));

        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public class UpdateListener implements ActionListener {
        Component comp;

        UpdateListener(Component comp) {
            this.comp = comp;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                checkFields();
                cmdWriter.write("update " + idLabel.getText() + "\n");
                cmdWriter.write(nameField2.getText() + "\n");
                cmdWriter.write(xField3.getText() + "\n");
                cmdWriter.write(yField4.getText() + "\n");
                cmdWriter.write(areaField5.getText() + "\n");
                cmdWriter.write(populationField6.getText() + "\n");
                cmdWriter.write(metersField7.getText() + "\n");
                cmdWriter.write(climateBox1.getSelectedItem().toString() + "\n");
                cmdWriter.write(governmentBox2.getSelectedItem().toString() + "\n");
                cmdWriter.write(solBox3.getSelectedItem().toString() + "\n");
                cmdWriter.write(govnameField10.getText() + "\n");
                cmdWriter.write(govageField11.getText() + "\n");
                cmdWriter.write(govheiField12.getText() + "\n");
                cmdWriter.flush();
                setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(comp, ex.getMessage(), "АШИБКА БЛЯТЬ", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                cmdWriter.write("remove_by_id " + idLabel.getText() + "\n");
                cmdWriter.flush();
                setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void checkFields() {
        if (nameField2.getText().isEmpty())
            throw new IllegalArgumentException("Значение поля имя не может быть пустым");
        sureLong("Х", -773, Integer.MAX_VALUE, xField3);
        sureDouble("Y", -Double.MAX_VALUE, 664, 14, yField4);
        sureDouble("площадь города (float > 0, не больше 7 цифр)", 0, Float.MAX_VALUE, 7, areaField5);
        sureLong("популяция (long > 0)", 0, Long.MAX_VALUE, populationField6);
        sureDouble("высотa (float, не более 7 цифр)", -Float.MAX_VALUE, Float.MAX_VALUE, 7, metersField7);
        if (govnameField10.getText().isEmpty())
            throw new IllegalArgumentException("Имя губернатора не может быть пустым");
        sureLong("возраст губернатора (int > 0)", 0, Integer.MAX_VALUE, govageField11);
        sureDouble("рост губернатора (double > 0, не более 14 цифр)", 0, Double.MAX_VALUE, 14, govheiField12);
    }

    private void sureLong(String msg, Number min, Number max, JTextField field) {
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

    private void sureDouble(String msg, Number min, Number max, int digits, JTextField field) {
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
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введенное вами значение поля " + msg + " не является числом");

        }
    }

    private boolean checkDouble(double s, Number min, Number max) {
        return s > min.doubleValue() && s < max.doubleValue();
    }

    private boolean checkLong(long s, Number min, Number max) {
        return s > min.longValue() && s < max.longValue();
    }
}
