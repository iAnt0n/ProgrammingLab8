package gui;

import collection.City;
import communication.TransferObject;
import communication.User;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.PipedWriter;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class VisualJPanel extends JPanel {
    private Dimension panelSize;
    private boolean i;
    private SpringLayout layout;
    private ConcurrentHashMap<String, City> localMap;
    private PipedWriter cmdWriter;
    private ResourceBundle res;

    public VisualJPanel(TransferObject table, PipedWriter cmdWriter, ResourceBundle res ){
        this.cmdWriter = cmdWriter;
        this.res= res;
        setBackground(Color.CYAN);
        updateVisual((ConcurrentHashMap<String, City>)table.getComplexArgs());
    }

    public void updateVisual(){
        updateVisual(localMap);
    }

    public void setRes(ResourceBundle res){
        this.res = res;
    }

    public void updateVisual(ConcurrentHashMap<String, City> map) {
        localMap = map;
        if(!i){i=true;}
        else {
            panelSize = new Dimension(getVisibleRect().width/2, getVisibleRect().height/2);
            setLayout(layout = new SpringLayout());
            removeAll();
            for (Map.Entry<String, City> city : localMap.entrySet()) {
                addPoint(city.getValue(), city.getKey());
            }
            revalidate();
            repaint();
        }
    }

    private void addPoint(City city, String key) {
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
}
