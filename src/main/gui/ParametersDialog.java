package main.gui;

import main.entity.Coordinates;
import main.entity.Location;
import main.entity.Route;
import main.entity.SendReceiver;
import main.request.AddIfMinReq;
import main.request.AddReq;
import main.request.UpdateReq;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.SocketException;
import java.util.ResourceBundle;

public class ParametersDialog extends JDialog {

    public static final int ADD = 1;
    public static final int ADD_IF_MIN = 2;
    public static final int UPDATE = 3;
    private JTextField nmField, cXField, cYField,
            fromNameField, fromXField, fromYField, toNameField, toXField, toYField, distField;
    private JLabel nmLabel, cXLabel, cYLabel,
            fromNameLabel, fromXLabel, fromYLabel, toNameLabel, toXLabel, toYLabel, distLabel;
    private JCheckBox noAddFrom;
    private JButton butOK, butCancel;
    private long newId;
    private SendReceiver sendReceiver;
    private int option;
    private ResourceBundle rb;

    public ParametersDialog(JFrame frame, SendReceiver sendReceiver, int option, ResourceBundle rb) {
        super(frame);
        setTitle(rb.getString("dataInput"));
        this.option = option;
        this.sendReceiver = sendReceiver;
        this.rb = rb;
        add(createGUI());
        pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        setResizable(false);

    }

    private JPanel createGUI() {
        GridLayout gl = new GridLayout(0,3,5, 5);
        JPanel panel = new JPanel(gl);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        initLabels();

        Box nameBox = Box.createVerticalBox();
        nameBox.add(nmLabel); nameBox.add(nmField = new JTextField(15));

        Box cXBox = Box.createVerticalBox();
        cXBox.add(cXLabel); cXBox.add(cXField = new JTextField(15));

        Box cYBox = Box.createVerticalBox();
        cYBox.add(cYLabel); cYBox.add(cYField = new JTextField(15));


        Box nFBox = Box.createVerticalBox();
        nFBox.add(fromNameLabel); nFBox.add(fromNameField = new JTextField(15));

        Box xFBox = Box.createVerticalBox();
        xFBox.add(fromXLabel); xFBox.add(fromXField = new JTextField(15));

        Box yFBox = Box.createVerticalBox();
        yFBox.add(fromYLabel); yFBox.add(fromYField = new JTextField(15));


        Box nTBox = Box.createVerticalBox();
        nTBox.add(toNameLabel); nTBox.add(toNameField = new JTextField(15));

        Box xTBox = Box.createVerticalBox();
        xTBox.add(toXLabel); xTBox.add(toXField = new JTextField(15));

        Box yTBox = Box.createVerticalBox();
        yTBox.add(toYLabel); yTBox.add(toYField= new JTextField(15));


        Box distBox = Box.createVerticalBox();
        distBox.add(distLabel); distBox.add(distField = new JTextField(15));

        noAddFrom = new JCheckBox();

        Box butBox = Box.createHorizontalBox();
        butOK = new JButton("OK");
        butCancel = new JButton();
        butBox.add(butOK);
        butBox.add(Box.createHorizontalStrut(20));
        butBox.add(butCancel);

        panel.add(nameBox);
        panel.add(cXBox);
        panel.add(cYBox);
        panel.add(nFBox);
        panel.add(xFBox);
        panel.add(yFBox);
        panel.add(nTBox);
        panel.add(xTBox);
        panel.add(yTBox);
        panel.add(distBox);
        panel.add(noAddFrom);
        panel.add(butBox);

        changeLang(rb);





        butOK.addActionListener(e -> {
            try {
                Route route;
                if (nmField.getText() != null && Integer.parseInt(cYField.getText()) > -861 &&
                        fromNameField.getText().length() < 366 && toNameField.getText().length() < 366 && Integer.parseInt(distField.getText()) > 1) {
                    route = new Route(nmField.getText(), new Coordinates(Float.parseFloat(cXField.getText()), Integer.parseInt(cYField.getText())),
                            new Location(Integer.parseInt(toXField.getText()), Integer.parseInt(toYField.getText()), toNameField.getText()), Integer.parseInt(distField.getText()));
                    if (noAddFrom.isSelected()) route.setFrom(null);
                    else
                        route.setFrom(new Location(Integer.parseInt(fromXField.getText()), Integer.parseInt(fromYField.getText()), fromNameField.getText()));
                } else {
                    throw new NumberFormatException();
                }
                switch (option) {
                    case ADD: {
                        AddReq req = new AddReq(route);
                        sendReceiver.send(req);
                        JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("success"), JOptionPane.INFORMATION_MESSAGE);
                        getParent().setEnabled(true);
                        setVisible(false);
                        break;
                    }
                    case ADD_IF_MIN: {
                        AddIfMinReq req = new AddIfMinReq(route);
                        req.setNewId(newId);
                        sendReceiver.send(req);
                        JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("success"), JOptionPane.INFORMATION_MESSAGE);
                        getParent().setEnabled(true);
                        setVisible(false);
                        break;
                    }
                    case UPDATE: {
                        UpdateReq req = new UpdateReq(newId, route);
                        sendReceiver.send(req);
                        JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("success"), JOptionPane.INFORMATION_MESSAGE);
                        getParent().setEnabled(true);
                        setVisible(false);
                        break;
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(getParent(), rb.getString("wrongData"), rb.getString("error"), JOptionPane.ERROR_MESSAGE);
            } catch (SocketException ex) {
                JOptionPane.showMessageDialog(getParent(), rb.getString("serverStop"), rb.getString("error"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            catch (IOException ex1) {
                ex1.printStackTrace();
            }

        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        butCancel.addActionListener(e -> onCancel());



        return panel;
    }


    public void clearFields() {
        nmField.setText("");
        cXField.setText("");
        cYField.setText("");
        fromNameField.setText("");
        fromXField.setText("");
        fromYField.setText("");
        toNameField.setText("");
        toXField.setText("");
        toYField.setText("");
        distField.setText("");
    }

    private void onCancel() {
        getParent().setEnabled(true);
        setVisible(false);
    }

    public void changeLang(ResourceBundle rb) {
        this.rb = rb;
        setTitle(rb.getString("dataInput"));
        nmLabel.setText(rb.getString("name"));
        cXLabel.setText(rb.getString("cordX"));
        cYLabel.setText(rb.getString("cordY"));
        fromNameLabel.setText(rb.getString("startName"));
        fromXLabel.setText(rb.getString("startX"));
        fromYLabel.setText(rb.getString("startY"));
        toNameLabel.setText(rb.getString("fnshName"));
        toXLabel.setText(rb.getString("fnshX"));
        toYLabel.setText(rb.getString("fnshY"));
        distLabel.setText(rb.getString("dist"));
        noAddFrom.setText(rb.getString("noAdd"));
        butCancel.setText(rb.getString("cancel"));
    }
    private void initLabels() {
        nmLabel = new JLabel();
        cXLabel = new JLabel();
        cYLabel = new JLabel();
        fromNameLabel = new JLabel();
        fromXLabel = new JLabel();
        fromYLabel = new JLabel();
        toNameLabel = new JLabel();
        toXLabel = new JLabel();
        toYLabel = new JLabel();
        distLabel = new JLabel();
    }


    public void setOption(int option) {
        this.option = option;
    }

    public void setNewId(long newId) {
        this.newId = newId;
    }
}
