package main.gui;

import main.WrongScriptFileException;
import main.entity.RequestList;
import main.entity.SendReceiver;
import main.request.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.SocketException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SendArgDialog extends JDialog {

    private JTextField tfArg;
    private JButton butOk, butCancel;
    private MyFrame crutchParent;
    private ParametersDialog parametersDialog;
    private SendReceiver sendReceiver;
    private JLabel argLabel;
    private String com;
    private ResourceBundle rb;





    public SendArgDialog(MyFrame parent, ParametersDialog parametersDialog, SendReceiver sendReceiver, ResourceBundle rb) {
        super(parent);
        crutchParent = parent;
        this.sendReceiver = sendReceiver;
        this.parametersDialog = parametersDialog;
        this.rb = rb;
        setTitle(rb.getString("argInput"));
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        add(createGUI());
        pack();
        setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);

    }



    private JPanel createGUI() {
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(200, 100));

        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        Box box1 = Box.createHorizontalBox();
        box1.add(argLabel = new JLabel(rb.getString("arg")));
        box1.add(Box.createHorizontalStrut(12));
        box1.add(tfArg = new JTextField(15));

        Box box2 = Box.createHorizontalBox();
        box2.add(butOk = new JButton("OK"));
        box2.add(Box.createHorizontalStrut(12));
        box2.add(butCancel = new JButton(rb.getString("cancel")));

        box.add(box1);
        box.add(Box.createVerticalStrut(20));
        box.add(box2);

        panel.add(box);


        butOk.addActionListener(e -> {
            try {
                switch (com) {
                    case "add_if_min": {
                        parametersDialog.setNewId(Long.parseLong(tfArg.getText()));
                        parametersDialog.clearFields();
                        parametersDialog.setVisible(true);
                        parametersDialog.setOption(ParametersDialog.ADD_IF_MIN);
                        break;
                    }
                    case "update": {
                        parametersDialog.setNewId(Long.parseLong(tfArg.getText()));
                        parametersDialog.clearFields();
                        parametersDialog.setVisible(true);
                        parametersDialog.setOption(ParametersDialog.UPDATE);
                        break;
                    }
                    case "remove_by_id": {
                        RemoveByIdReq req = new RemoveByIdReq(Long.parseLong(tfArg.getText()));
                        sendReceiver.send(req);
                        JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("success"), JOptionPane.INFORMATION_MESSAGE);
                        getParent().setEnabled(true);
                        break;
                    }
                    case "remove_all_by_distance": {
                        RemoveAllByDistanceReq req = new RemoveAllByDistanceReq(Integer.parseInt(tfArg.getText()));
                        sendReceiver.send(req);
                        JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("success"), JOptionPane.INFORMATION_MESSAGE);
                        getParent().setEnabled(true);
                        break;
                    }
                    case "remove_greater": {
                        RemoveGreaterReq req = new RemoveGreaterReq();
                        req.setId(Long.parseLong(tfArg.getText()));
                        sendReceiver.send(req);
                        JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("success"), JOptionPane.INFORMATION_MESSAGE);
                        getParent().setEnabled(true);
                        break;
                    }
                    case "filter_contains_name": {
                        FilterContainsNameReq req = new FilterContainsNameReq(tfArg.getText());
                        sendReceiver.send(req);
                        JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("success"), JOptionPane.INFORMATION_MESSAGE);
                        getParent().setEnabled(true);
                        break;
                    }

                }
                setVisible(false);
            }
            catch (SocketException ex) {
                JOptionPane.showMessageDialog(getParent(), rb.getString("serverStop"), rb.getString("error"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(getParent(), rb.getString("wrongArg") + " " + com + "!", rb.getString("error"), JOptionPane.ERROR_MESSAGE);
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

    private void onCancel() {
        getParent().setEnabled(true);
        setVisible(false);
    }

    public void changeLang(ResourceBundle rb) {
        this.rb = rb;
        setTitle(rb.getString("argInput"));
        butCancel.setText(rb.getString("cancel"));
        argLabel.setText(rb.getString("arg"));


    }

    public void clearField() {
        tfArg.setText("");
    }

    public void setCom(String com) {
        this.com = com;
    }
}
