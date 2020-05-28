package gui;

import collection.City;
import communication.TransferObject;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class VisualJPanel extends JPanel {
    private Dimension panelSize;
    private Pair<Integer,Integer> centre;
    private boolean i;
    private SpringLayout layout;
    private ConcurrentHashMap<String, City> localMap;
    private ResourceBundle res;

    public VisualJPanel(TransferObject table, ResourceBundle res ){
        this.res= res;
        setBackground(Color.CYAN);
        updateVisual((ConcurrentHashMap<String, City>)table.getComplexArgs());
    }

    public void updateVisual(){
        updateVisual(localMap);
    }
    public void updateVisual(ConcurrentHashMap<String, City> map) {
        localMap = map;
        if(!i){i=true;}
        else {
            panelSize = new Dimension(getVisibleRect().width, getVisibleRect().height);
            centre = new Pair<>(panelSize.width / 2, panelSize.height / 2);
            setLayout(layout = new SpringLayout());
            removeAll();
            for (Map.Entry<String, City> city : localMap.entrySet()) {
                addPoint(city.getValue(), city.getKey());
            }
            revalidate();
            repaint();
        }
    }

    private void addPoint(City city, String key){
        Pair<Integer,Integer> where = new Pair<>(city.getCoordinates().getX()%centre.getKey()+centre.getKey(),city.getCoordinates().getY().intValue()%centre.getValue()+centre.getValue());
        Float diam = (city.getArea()%4+1)*10;
        RoundButton button = new RoundButton(city,key,diam.intValue(),res);

        System.out.println("Coordinates: "+where.getKey() + " " + where.getValue());
        layout.putConstraint(SpringLayout.WEST,button,where.getKey(),SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH,button,where.getValue(),SpringLayout.NORTH,this);

        add(button);
//        paintComponent(this.getGraphics());
    }
//    public Long getMaxPopul(ConcurrentHashMap<String, City> map){
//        Long max = 0L;
//        for(City city :map.values()){
//            if (city.getPopulation()>max)max = city.getPopulation();
//        }
//        return max;
//    }
//    private Dimension getMaxDimension(ConcurrentHashMap<String, City> map){
//        int height=Integer.MIN_VALUE;
//        int width = Integer.MIN_VALUE;
//        for(City city :map.values()){
//            if(Math.abs(city.getCoordinates().getX().intValue())>width)width=Math.abs(city.getCoordinates().getX().intValue());
//            if(Math.abs(city.getCoordinates().getY().intValue())>height)height=Math.abs(city.getCoordinates().getY().intValue());
//        }
//        width+=10;
//        height+=10;
//        return new Dimension(width,height);
//    }
}
