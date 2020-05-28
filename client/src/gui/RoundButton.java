package gui;

import collection.City;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ResourceBundle;

public class RoundButton extends JButton {
    City city;
    String key;
    public RoundButton(City city, String key, Integer diam) {
        this.city=city;
        this.key= key;
        Dimension size = new Dimension(diam,diam);
        setPreferredSize(size);
        // Не закрашиваем кнопочку.
        setContentAreaFilled(false);
    }

    // Рисуем нашу кнопочку.
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            // Устанавливаем цвет по умолчанию.
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        // Рисуем окружность.
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        // Прорисовываем сам JButton.
        super.paintComponent(g);
    }

//     Рисуем бордюр кнопочки.
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0,0 , getSize().width - 1, getSize().height - 1);
    }

    // Определяем принадлежность точки к нашей кнопочки.
    Shape shape;

    public boolean contains(int x, int y) {
        // Если кнопка изменилась в размере
        // создаем новый объект shape.
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        // Возвращаем true если точка принадлежит
        // и false если не принадлежит кнопке.
        return shape.contains(x, y);
    }

}