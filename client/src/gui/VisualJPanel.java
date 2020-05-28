package gui;

import collection.City;
import communication.TransferObject;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class VisualJPanel extends JPanel {
    Dimension size;
    Dimension panelSize;
    Pair<Integer,Integer> centre;
    Long maxPop;
    boolean i;
    SpringLayout layout;
    ConcurrentHashMap<String, City> map;

    public VisualJPanel(TransferObject table){
        setBackground(Color.CYAN);
        updateVisual((ConcurrentHashMap<String, City>)table.getComplexArgs());
        setLayout(null);
    }

    public void updateVisual(ConcurrentHashMap<String, City> map) {
        this.map = map;
        paintComponent(this.getGraphics());
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(!i){i=true;}
        else {
            size = getMaxDimension(map);
            panelSize = new Dimension(getVisibleRect().width, getVisibleRect().height);
            System.out.println(panelSize.width + " panelSize " + panelSize.height);
            centre = new Pair<>(panelSize.width / 2, panelSize.height / 2);
            maxPop = getMaxPopul(map);
            setLayout(layout = new SpringLayout());
            for (City city : map.values()) {
                addPoint(city);
            }
            super.paintComponent(g);
        }
    }

    private void addPoint(City city){
        Double width = city.getCoordinates().getX().doubleValue()/size.width*panelSize.width;
        Double height = city.getCoordinates().getY()/size.height*panelSize.height;
        Pair<Integer,Integer> where = new Pair<>(width.intValue() + centre.getKey(),
                height.intValue() + centre.getValue());
        Double diam = city.getPopulation().doubleValue()/maxPop*14;
        RoundButton button = new RoundButton(city,diam.intValue()+10);
        button.setLocation(where.getKey(),where.getValue());
        add(button);
        layout.putConstraint(SpringLayout.WEST,button,where.getKey(),SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH,button,where.getValue(),SpringLayout.NORTH,this);
        paintComponent(this.getGraphics());
    }
    public Long getMaxPopul(ConcurrentHashMap<String, City> map){
        Long max = 0L;
        for(City city :map.values()){
            if (city.getPopulation()>max)max = city.getPopulation();
        }
        return max;
    }
    private Dimension getMaxDimension(ConcurrentHashMap<String, City> map){
        int height=Integer.MIN_VALUE;
        int width = Integer.MIN_VALUE;
        for(City city :map.values()){
            if(Math.abs(city.getCoordinates().getX().intValue())>width)width=Math.abs(city.getCoordinates().getX().intValue());
            if(Math.abs(city.getCoordinates().getY().intValue())>height)height=Math.abs(city.getCoordinates().getY().intValue());
        }
        width+=10;
        height+=10;
        return new Dimension(width,height);
    }
}

