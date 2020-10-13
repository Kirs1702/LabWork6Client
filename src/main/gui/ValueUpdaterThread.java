package main.gui;

import main.entity.RouteSet;
import main.entity.SendReceiver;
import main.request.ConfirmReq;
import main.request.SetReq;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;
import java.util.ResourceBundle;

public class ValueUpdaterThread extends Thread {
    private MyTableModel model;
    private DrawPanel drawPanel;
    private SendReceiver sendReceiver;
    private ResourceBundle rb;

    public ValueUpdaterThread(MyTableModel model, DrawPanel drawPanel, SendReceiver sendReceiver, ResourceBundle rb) {
        this.sendReceiver = sendReceiver;
        this.model = model;
        this.drawPanel = drawPanel;
        this.rb = rb;
    }
    public  void run(){
        String s = "aa";
        while(true) {
            try {
                sendReceiver.send(new ConfirmReq());
                String s1 = sendReceiver.receiveString();
                if (!(s.equals(s1))) {
                    sendReceiver.send(new SetReq());
                    RouteSet rs = sendReceiver.receiveRouteSet();
                    model.fillRows(rs);
                    drawPanel.setRouteSet(rs);
                    s = s1;
                }

                sleep(1000);
            } catch (SocketException ex) {
                JOptionPane.showMessageDialog(new JFrame(), rb.getString("serverStop"), rb.getString("error"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                ex.printStackTrace();
            }

        }
    }

}
