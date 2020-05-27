package gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.ResourceBundle;

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
    private JLabel ownerLocLabel;
    private JLabel timeLocLabel;
    private JLabel idLocLabel;
    private JLabel keyLocLabel;
    private JLabel nameLocLabel;
    private JLabel xLocLabel;
    private JLabel yLocLabel;
    private JLabel areaLocLabel;
    private JLabel populationLocLabel;
    private JLabel metersLocLabel;
    private JLabel climateLocLabel;
    private JLabel solLocLabel;
    private JLabel governmentLocLabel;
    private JLabel govnameLocLabel;
    private JLabel govageLocLabel;
    private JLabel govheiLocLabel;

    public InfoDialog(HashMap<String, Object> defaultValues, ResourceBundle res) {
        setContentPane(infoPanel);
        keyLabel.setText(defaultValues.get(res.getString("key")).toString());
        timeLabel.setText(defaultValues.get(res.getString("time")).toString());
        nameLabel.setText(defaultValues.get(res.getString("name")).toString());
        idLabel.setText(defaultValues.get(res.getString("id")).toString());
        xLabel.setText(defaultValues.get(res.getString("x")).toString());
        yLabel.setText(defaultValues.get(res.getString("y")).toString());
        areaLabel.setText(defaultValues.get(res.getString("area")).toString());
        populationLabel.setText(defaultValues.get(res.getString("population")).toString());
        metersLabel.setText(defaultValues.get(res.getString("meters")).toString());
        climateLabel.setText(defaultValues.get(res.getString("climate")).toString());
        solLabel.setText(defaultValues.get(res.getString("sol")).toString());
        govLabel.setText(defaultValues.get(res.getString("government")).toString());
        govnameLabel.setText(defaultValues.get(res.getString("govname")).toString());
        govageLabel.setText(defaultValues.get(res.getString("govage")).toString());
        govheiLabel.setText(defaultValues.get(res.getString("govhei")).toString());
        ownerLabel.setText(defaultValues.get(res.getString("owner")).toString());

        ownerLocLabel.setText(res.getString("owner"));
        timeLocLabel.setText(res.getString("time"));
        idLocLabel.setText(res.getString("id"));
        keyLocLabel.setText(res.getString("key"));
        nameLocLabel.setText(res.getString("name"));
        xLocLabel.setText(res.getString("x"));
        yLocLabel.setText(res.getString("y"));
        areaLocLabel.setText(res.getString("area"));
        populationLocLabel.setText(res.getString("population"));
        metersLocLabel.setText(res.getString("meters"));
        climateLocLabel.setText(res.getString("climate"));
        solLocLabel.setText(res.getString("sol"));
        governmentLocLabel.setText(res.getString("government"));
        govnameLocLabel.setText(res.getString("govname"));
        govageLocLabel.setText(res.getString("govage"));
        govheiLocLabel.setText(res.getString("govhei"));

        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
