package gui;

import javax.swing.*;
import java.util.HashMap;

public class InfoDialog extends JDialog{
    private JPanel infoPanel;
    private JLabel timeLabel;
    private JLabel idLabel;
    private JLabel keyLabel;
    private JLabel nameLabel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel areaLabel;
    private JLabel populationLabel;
    private JLabel metersLabel;
    private JLabel climateLabel;
    private JLabel solLabel;
    private JLabel govLabel;
    private JLabel govnameLabel;
    private JLabel govageLabel;
    private JLabel govheiLabel;
    private JLabel ownerLabel;

    public InfoDialog(HashMap<String, Object> defaultValues) {
        setContentPane(infoPanel);
        keyLabel.setText(defaultValues.get("Key").toString());
        timeLabel.setText(defaultValues.get("Creation Time").toString());
        idLabel.setText(defaultValues.get("Id").toString());
        xLabel.setText(defaultValues.get("X").toString());
        yLabel.setText(defaultValues.get("Y").toString());
        areaLabel.setText(defaultValues.get("Area").toString());
        populationLabel.setText(defaultValues.get("Population").toString());
        metersLabel.setText(defaultValues.get("Meters Above Sea Level").toString());
        climateLabel.setText(defaultValues.get("Climate").toString());
        solLabel.setText(defaultValues.get("Standard of living").toString());
        govLabel.setText(defaultValues.get("Government").toString());
        govnameLabel.setText(defaultValues.get("Governor Name").toString());
        govageLabel.setText(defaultValues.get("Governor Age").toString());
        govheiLabel.setText(defaultValues.get("Governor Height").toString());
        ownerLabel.setText(defaultValues.get("Owner").toString());

        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
