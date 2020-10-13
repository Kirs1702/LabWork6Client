package main.gui;

import main.entity.SendReceiver;
import main.request.LoginReq;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginDialog extends JDialog {

    private JTextField tfUser;
    private JPasswordField tfPassword;
    private JRadioButton rbLogin, rbRegister;
    private JButton butOK, butCancel;
    private SendReceiver sendReceiver;
    private MyFrame crutchParent;
    private DrawPanel drawPanel;
    private ResourceBundle rb;
    private JLabel userLabel, passLabel;





    public LoginDialog(MyFrame parent, DrawPanel drawPanel, SendReceiver sendReceiver, ResourceBundle rb) {
        super(parent);
        crutchParent = parent;
        this.drawPanel = drawPanel;
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.rb = rb;
        setTitle(rb.getString("auth"));
        add(createGUI());
        pack();
        setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        setVisible(true);
        this.sendReceiver = sendReceiver;


    }



    private Box createGUI() {

        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));


        userLabel = new JLabel(rb.getString("username"));
        passLabel = new JLabel(rb.getString("password"));
        Box namePass = Box.createVerticalBox();
        namePass.add(userLabel);
        tfUser = new JTextField(15);
        namePass.add(tfUser);
        namePass.add(Box.createVerticalStrut(12));
        namePass.add(passLabel);
        tfPassword = new JPasswordField(15);
        namePass.add(tfPassword);

        Box logReg = Box.createHorizontalBox();
        rbLogin = new JRadioButton(rb.getString("login"));
        rbRegister = new JRadioButton(rb.getString("register"));
        ButtonGroup group = new ButtonGroup();
        group.add(rbLogin); group.add(rbRegister);
        logReg.add(rbLogin);
        logReg.add(Box.createHorizontalStrut(12));
        logReg.add(rbRegister);
        rbLogin.setSelected(true);

        Box butBox = Box.createHorizontalBox();
        butOK = new JButton("OK");
        butCancel = new JButton(rb.getString("cancel"));
        butBox.add(Box.createHorizontalStrut(20));
        butBox.add(butOK);
        butBox.add(Box.createHorizontalStrut(20));
        butBox.add(butCancel);


        JComboBox<String> cb = new JComboBox<>(new String[] {"Eng", "Rus", "Tur", "Swe"});

        box.add(cb);
        box.add(Box.createVerticalStrut(12));
        box.add(namePass);

        box.add(logReg);
        box.add(butBox);





        cb.addActionListener(e -> {
            if (cb.getSelectedItem() == "Eng") {
                Locale.setDefault(new Locale("en_IE"));
            }
            if (cb.getSelectedItem() == "Rus") {
                Locale.setDefault(new Locale("ru"));
            }
            if (cb.getSelectedItem() == "Tur") {
                Locale.setDefault(new Locale("tr"));
            }
            if (cb.getSelectedItem() == "Swe") {
                Locale.setDefault(new Locale("sv"));
            }

            ResourceBundle.clearCache();
            rb = ResourceBundle.getBundle("Gui");
            crutchParent.changeLng(cb.getSelectedIndex());
            setTitle(rb.getString("auth"));
            userLabel.setText(rb.getString("username"));
            passLabel.setText(rb.getString("password"));
            rbLogin.setText(rb.getString("login"));
            rbRegister.setText(rb.getString("register"));
            butCancel.setText(rb.getString("cancel"));
        });



        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        butCancel.addActionListener(e -> {
            System.exit(0);
        });

        butOK.addActionListener(e -> {
            try {
                if (!Arrays.equals(tfPassword.getPassword(), new char[]{}) && !tfUser.getText().equals("")) {
                    LoginReq req = new LoginReq();
                    req.setUsername(tfUser.getText());
                    req.setPassword(new String(tfPassword.getPassword()));
                    req.setRegister(rbRegister.isSelected());
                    sendReceiver.send(req);
                    byte b = sendReceiver.receiveByte();
                    if (b == 1) {
                        crutchParent.setUsername(tfUser.getText());
                        ////
                        getParent().setVisible(true);
                        new ValueUpdaterThread(crutchParent.getModel(), drawPanel, sendReceiver, rb).start();
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(new JFrame(), rb.getString("wrongData"), rb.getString("warning"), JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SocketException ex) {
                JOptionPane.showMessageDialog(getParent(), rb.getString("serverStop"), rb.getString("error"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return box;
    }




}
