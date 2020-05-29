package gui;

import collection.City;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ResourceBundle;


public class RoundButton extends JButton implements ActionListener {
    City city;
    String key;
    private int diam;
    private int initDiam = 0;
    Timer tm = new Timer(15, this);
    public boolean grow = true;
    public boolean disgrow = false;
    private Pair<Integer,Integer> where;
    private int red, green, blue;

    public RoundButton(City city, String key, Integer diam, Pair<Integer,Integer> where) {
        this.where = where;
        this.city=city;
        this.key= key;
        this.diam = diam-1;
        Dimension size = new Dimension(diam,diam);
        setPreferredSize(size);
        // Не закрашиваем кнопочку.
        setContentAreaFilled(false);
        red = city.getUser().hashCode()*38%255;
        green = city.getUser().hashCode()*25%255;
        blue = city.getUser().hashCode()*67%255;
        tm.start();
    }
    public void remove(){
        disgrow=true;
    }
    public String getKey(){
        return key;
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
        g.setColor(new Color(red,green,blue));
        g.fillOval(0, 0, initDiam, initDiam);
        // Прорисовываем сам JButton.
        super.paintComponent(g);
    }

//     Рисуем бордюр кнопочки.
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0,0 , initDiam, initDiam);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (initDiam>=diam) grow = false;
        if (diam<=2) {
            disgrow = false;
            if (!grow) setVisible(false);
        }
        if (grow) initDiam +=1;
        if (disgrow) initDiam -=1;
        repaint();
    }
    Pair<Integer,Integer> getPlace(){
        return where;
    }
}