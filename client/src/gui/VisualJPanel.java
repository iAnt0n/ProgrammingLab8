package gui;

import collection.City;
import communication.TransferObject;
import communication.User;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.PipedWriter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class VisualJPanel extends JPanel {
    private Dimension panelSize;
    private boolean i;
    private boolean b;
    private SpringLayout layout;
    private ConcurrentHashMap<String, City> localMap = new ConcurrentHashMap<String, City>();
    private ConcurrentHashMap<String, City> buffMap;
    private PipedWriter cmdWriter;
    private ResourceBundle res;

    public VisualJPanel(TransferObject table, PipedWriter cmdWriter, ResourceBundle res ){
        this.cmdWriter = cmdWriter;
        this.res= res;
        setBackground(Color.CYAN);
        updateVisual((ConcurrentHashMap<String, City>)table.getComplexArgs());
    }

    public void updateVisual(){
        if(!b){
            b=true;
            updateVisual(buffMap);
        }else updateVisual(localMap);
    }

    public void setRes(ResourceBundle res){
        this.res = res;
    }

    public void updateVisual(ConcurrentHashMap<String, City> map) {
        System.out.println(map.size());
        if(!i){
            i=true;
            buffMap=map;
        }
        else {
            panelSize = new Dimension(getVisibleRect().width/2, getVisibleRect().height/2);
            setLayout(layout = new SpringLayout());
            Pair<ArrayList<String>, HashMap<String,City>> pair = compareMaps(map);
            ArrayList<String> keys = pair.getKey();
            HashMap<String,City> newCities = pair.getValue();
            for (String remCity : keys){
                removeByKey(remCity);
            }
            for (Map.Entry<String,City> e:newCities.entrySet()){
                addPoint(e.getValue(),e.getKey());
            }
            if(map==localMap){
                removeAll();
                for (Map.Entry<String,City> e:localMap.entrySet()){
                    addPoint(e.getValue(),e.getKey());
                }
            }
            localMap = map;
            revalidate();
//            repaint();
        }
    }
    public void removeByKey(String key){
        System.out.println("removeCheck");
        for ( int i =0; i < getComponentCount();i++){
            RoundButton button =(RoundButton)getComponent(i);
            if (button.getKey()==key){
                button.remove();
                remove(i);
                break;
            }
        }
    }

    private void addPoint(City city, String key) {
        System.out.println("addPointCheck");
        int diam;
        if (city.getArea() <= 50) {
            diam = 20;
        } else if (city.getArea() <= 200) {
            diam = 30;
        } else diam = 40;
        Pair<Integer, Integer> where = new Pair<>(city.getCoordinates().getX() % panelSize.width +panelSize.width, city.getCoordinates().getY().intValue() % panelSize.height +panelSize.height);

        RoundButton button = new RoundButton(city, key, diam);
        button.addActionListener(e-> {
            if (city.getUser().equals(User.getLogin())){
                new EditDialog(key, city, cmdWriter, res);
            }
            else new InfoDialog(key, city, res);
        });

        layout.putConstraint(SpringLayout.WEST, button, where.getKey(), SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, button, where.getValue(), SpringLayout.NORTH, this);
        add(button);
    }
    public Pair<ArrayList<String>, HashMap<String,City>> compareMaps(ConcurrentHashMap<String, City> newMap){
        System.out.println(newMap.size());
        System.out.println("CompareMApsCheck");
        ArrayList<String> deletedCity = new ArrayList<>();
        HashMap<String,City> changes = new HashMap<>();
        Pair<ArrayList<String>, HashMap<String,City>> pair = new Pair<>(deletedCity,changes);
        for(String oldK : localMap.keySet()){
            if(newMap.get(oldK)==null||localMap.get(oldK).compare(newMap.get(oldK))){
                deletedCity.add(oldK);
                System.out.println("-");
            }
        }
        for(String newK:newMap.keySet()){
            System.out.println("AddCityCheck");
            if (localMap.get(newK)==null||localMap.get(newK).compare(newMap.get(newK))){
                changes.put(newK,newMap.get(newK));
                System.out.println("+");
            }
        }
        return pair;
    }
//    public void compareMaPs(ConcurrentHashMap<String, City> newMap){
//        for (String k: localMap.keySet()){
//            if (newMap.get(k)==null) {
//                RoundButton b = currentButts.get(k);
//                b.disgrow = true;
//                currentButts.remove(k);
//            }
//            else{
//                City newC = newMap.get(k);
//                City oldC = localMap.get(k);
//                if (!newC.getCoordinates().getX().equals(oldC.getCoordinates().getX())
//                        || !newC.getCoordinates().getY().equals(oldC.getCoordinates().getY())
//                        || !(newC.getArea()==oldC.getArea())){
//                    RoundButton b = currentButts.get(k);
//                    b.disgrow = true;
//                    currentButts.remove(k);
//                    addPoint(newMap.get(k), k);
//                }
//            }
//        }
//        for (String k: newMap.keySet()){
//            if (localMap.get(k)==null){
//                addPoint(newMap.get(k), k);
//            }
//        }
//    }
}
