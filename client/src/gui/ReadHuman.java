package gui;

import commands.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.ResourceBundle;

public class ReadHuman extends JDialog {
    private JTextField govnameField1;
    private JTextField govageField2;
    private JTextField govheiField3;
    private JButton confirmButton;
    PipedWriter cmdWriter;
    private JPanel panel;
    private JLabel govnameLabel;
    private JLabel govageLabel;
    private JLabel govheiLabel;

    ReadHuman(PipedWriter writer, JFrame owner, ResourceBundle res) {
        super(owner, "ЗАПОЛНЯЙ БЛЯТЬ", true);
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
                cmdWriter.write(govnameField1.getText() + "\n");
                cmdWriter.write(govageField2.getText() + "\n");
                cmdWriter.write(govheiField3.getText() + "\n");
                cmdWriter.flush();
                setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(comp, ex.getMessage(), "АШИБКА БЛЯТЬ", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void checkFields() {
        if (govnameField1.getText().isEmpty()) throw new IllegalArgumentException("Имя человека не может быть пустым");
        sureLong("возраст губернатора (int > 0)", 0, Integer.MAX_VALUE, govageField2);
        sureDouble("рост губернатора (double > 0, не более 14 цифр)", 0, Double.MAX_VALUE, 14, govheiField3);
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
        govnameField1.setText("");
        govageField2.setText("");
        govheiField3.setText("");
        setVisible(true);
    }

    public void updateText(ResourceBundle res) {
        govnameLabel.setText(res.getString("govname"));
        govageLabel.setText(res.getString("govage"));
        govheiLabel.setText(res.getString("govhei"));
        confirmButton.setText(res.getString("confirm"));
        pack();
    }
}
