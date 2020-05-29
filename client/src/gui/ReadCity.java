package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.ResourceBundle;

public class ReadCity extends JDialog {
    private JTextField nameField1;
    private JTextField xField2;
    private JTextField yField3;
    private JTextField areaField4;
    private JTextField populationField5;
    private JTextField metersField6;
    private JComboBox climateBox1;
    private JComboBox solBox2;
    private JComboBox governmentBox3;
    private JTextField govnameField7;
    private JTextField govageField8;
    private JTextField govheiField9;
    private JButton confirmButton;
    private JPanel panel;
    private JLabel nameLabel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel areaLabel;
    private JLabel popLabel;
    private JLabel metersLabel;
    private JLabel climateLabel;
    private JLabel solLabel;
    private JLabel governmentLabel;
    private JLabel govnameLabel;
    private JLabel govageLabel;
    private JLabel govheiLabel;
    private PipedWriter cmdWriter;

    ReadCity(PipedWriter writer, JFrame owner, ResourceBundle res) {
        super(owner, res.getString("input"), true);
        cmdWriter = writer;

        updateText(res);

        ConfirmListener confirmListener = new ConfirmListener(this);
        confirmButton.addActionListener(confirmListener);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panel);
        pack();
    }

    public class ConfirmListener implements ActionListener {
        Component comp;

        ConfirmListener(Component comp) {
            this.comp = comp;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                checkFields();
                cmdWriter.write(nameField1.getText() + "\n");
                cmdWriter.write(xField2.getText() + "\n");
                cmdWriter.write(yField3.getText() + "\n");
                cmdWriter.write(areaField4.getText() + "\n");
                cmdWriter.write(populationField5.getText() + "\n");
                cmdWriter.write(metersField6.getText() + "\n");
                cmdWriter.write(climateBox1.getSelectedItem().toString() + "\n");
                cmdWriter.write(governmentBox3.getSelectedItem().toString() + "\n");
                cmdWriter.write(solBox2.getSelectedItem().toString() + "\n");
                cmdWriter.write(govnameField7.getText() + "\n");
                cmdWriter.write(govageField8.getText() + "\n");
                cmdWriter.write(govheiField9.getText() + "\n");
                cmdWriter.flush();
                setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(comp, ex.getMessage(), "АШИБКА БЛЯТЬ", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void checkFields() {
        if (nameField1.getText().isEmpty())
            throw new IllegalArgumentException("Значение поля имя не может быть пустым");
        sureLong("Х", -773, Integer.MAX_VALUE, xField2);
        sureDouble("Y", -Double.MAX_VALUE, 664, 14, yField3);
        sureDouble("площадь города (float > 0, не больше 7 цифр)", 0, Float.MAX_VALUE, 7, areaField4);
        sureLong("популяция (long > 0)", 0, Long.MAX_VALUE, populationField5);
        sureDouble("высотa (float, не более 7 цифр)", -Float.MAX_VALUE, Float.MAX_VALUE, 7, metersField6);
        if (govnameField7.getText().isEmpty())
            throw new IllegalArgumentException("Имя губернатора не может быть пустым");
        sureLong("возраст губернатора (int > 0)", 0, Integer.MAX_VALUE, govageField8);
        sureDouble("рост губернатора (double > 0, не более 14 цифр)", 0, Double.MAX_VALUE, 14, govheiField9);
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

    public void prepare() {
        nameField1.setText("");
        xField2.setText("");
        yField3.setText("");
        areaField4.setText("");
        populationField5.setText("");
        metersField6.setText("");
        govnameField7.setText("");
        govageField8.setText("");
        govheiField9.setText("");
        setVisible(true);
    }

    public void updateText(ResourceBundle res) {
        nameLabel.setText(res.getString("name"));
        xLabel.setText(res.getString("x"));
        yLabel.setText(res.getString("y"));
        areaLabel.setText(res.getString("area"));
        popLabel.setText(res.getString("population"));
        metersLabel.setText(res.getString("meters"));
        climateLabel.setText(res.getString("climate"));
        solLabel.setText(res.getString("sol"));
        governmentLabel.setText(res.getString("government"));
        govnameLabel.setText(res.getString("govname"));
        govageLabel.setText(res.getString("govage"));
        govheiLabel.setText(res.getString("govhei"));
        confirmButton.setText(res.getString("confirm"));
        setTitle(res.getString("input"));
        pack();
    }
}
