package main.gui;

import javafx.stage.FileChooser;
import main.entity.*;
import main.request.*;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyFrame  extends JFrame {

    private JButton bExit, bHistory, bScript, bAdd, bAddIfMin,
                    bUpd, bClear, bFCName, bHelp, bInfo,
            bPrAsc, bRemGr, bRemId, bRemDist, bShow;


    private SendReceiver sendReceiver;
    private History history = new History(10);
    private  JLabel userLabel, usernameLabel;
    private String username = "";
    private ParametersDialog parametersDialog = null;
    private SendArgDialog sendArgDialog = null;
    private MyTableModel model;
    private DrawPanel drawPanel;
    private  JSlider slider;
    private JTabbedPane tabbedPane;
    private ResourceBundle rb;
    JComboBox<String> cb;

    public MyFrame(SendReceiver sendReceiver) {
        super();
        this.sendReceiver = sendReceiver;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Locale.setDefault(new Locale("en_IE"));
        rb = ResourceBundle.getBundle("Gui");
        add(createGUI());
        setMinimumSize(new Dimension(700, 500));
        setPreferredSize(new Dimension(700, 500));
        pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        new LoginDialog(this, drawPanel, sendReceiver, rb);
        setTitle(rb.getString("window"));
        parametersDialog = new ParametersDialog(this, sendReceiver,0, rb);
        sendArgDialog = new SendArgDialog(this,parametersDialog,sendReceiver, rb);


    }



    private JPanel createGUI() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        tabbedPane = new JTabbedPane();


        Box northBox = Box.createHorizontalBox();
        Box userBox = Box.createHorizontalBox();
        userLabel = new JLabel(rb.getString("user"));
        userBox.add(userLabel);
        userBox.add(Box.createHorizontalStrut(10));
        usernameLabel = new JLabel(username);
        userBox.add(usernameLabel);
        northBox.add(userBox);
        JPanel ppp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cb = new JComboBox<>(new String[] {"Eng", "Rus", "Tur", "Swe"});
        ppp.add(cb);
        northBox.add(ppp);


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
            changeLng(cb.getSelectedIndex());



        });



        model = new MyTableModel();
        JTable table = new JTable(model){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        };
        table.setRowHeight(20);
        table.setIntercellSpacing(new Dimension(6, 2));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        RowSorter<TableModel> sorter = new TableRowSorter<>(model);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        table.setRowSorter(sorter);
        table.setRowSorter(sorter);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        model.fillRows(new RouteSet());




        JPanel visual = new JPanel(new BorderLayout());
        drawPanel = new DrawPanel();
        slider = new JSlider(JSlider.VERTICAL, 10, 100, 100);
        slider.setMinorTickSpacing(1);
        slider.setBorder(BorderFactory.createLineBorder(Color.gray));
        slider.addChangeListener(e -> drawPanel.setFactor(slider.getValue()));








        visual.add(drawPanel, BorderLayout.CENTER);
        visual.add(slider, BorderLayout.EAST);



        tabbedPane.addTab(rb.getString("table"), new JScrollPane(table));
        tabbedPane.addTab(rb.getString("graphics"), visual);
        panel.add(northBox, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);


        {
            JPanel sPan = new JPanel(new GridLayout(3, 5, 5, 5));
            sPan.add(bAdd = new JButton("add"));
            sPan.add(bRemId = new JButton("remove_by_id"));
            sPan.add(bInfo = new JButton("info"));
            //sPan.add(bShow = new JButton("show"));
            //sPan.add(bClear = new JButton("clear"));

            sPan.add(bAddIfMin = new JButton("add_if_min"));
            sPan.add(bRemDist = new JButton("remove_by_dist"));
            sPan.add(bHelp = new JButton("help"));
            sPan.add(bPrAsc = new JButton("print_ascending"));
            sPan.add(bScript = new JButton("execute_script"));

            sPan.add(bUpd = new JButton("update"));
            sPan.add(bRemGr = new JButton("remove_greater"));
            sPan.add(bHistory = new JButton("history"));
            //sPan.add(bFCName = new JButton("filter_name"));
            sPan.add(bExit = new JButton("exit"));
            panel.add(sPan, BorderLayout.SOUTH);
        }

        bAdd.addActionListener(e -> {
            setEnabled(false);
            parametersDialog.clearFields();
            parametersDialog.setVisible(true);
            parametersDialog.setOption(ParametersDialog.ADD);
            history.capture(bAdd.getText());
        });

        bAddIfMin.addActionListener(e -> {
            setEnabled(false);
            sendArgDialog.clearField();
            sendArgDialog.setCom("add_if_min");
            sendArgDialog.setVisible(true);
            history.capture(bAddIfMin.getText());
        });

        bUpd.addActionListener(e -> {
            setEnabled(false);
            sendArgDialog.clearField();
            sendArgDialog.setCom("update");
            sendArgDialog.setVisible(true);
            history.capture(bUpd.getText());
        });

        bRemId.addActionListener(e -> {
            setEnabled(false);
            sendArgDialog.clearField();
            sendArgDialog.setCom("remove_by_id");
            sendArgDialog.setVisible(true);
            history.capture(bRemId.getText());
        });

        bRemDist.addActionListener(e -> {
            setEnabled(false);
            sendArgDialog.clearField();
            sendArgDialog.setCom("remove_all_by_distance");
            sendArgDialog.setVisible(true);
            history.capture(bRemDist.getText());
        });

        bRemGr.addActionListener(e -> {
            setEnabled(false);
            sendArgDialog.clearField();
            sendArgDialog.setCom("remove_greater");
            sendArgDialog.setVisible(true);
            history.capture(bRemGr.getText());
        });

        bInfo.addActionListener(e -> {
            try {
                sendReceiver.send(new InfoReq());
                JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("collInfo"), JOptionPane.INFORMATION_MESSAGE);
            } catch (SocketException ex) {
                JOptionPane.showMessageDialog(getParent(), rb.getString("serverStop"), rb.getString("error"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            history.capture(bInfo.getText());
        });

        bHelp.addActionListener(e -> {
            try {
                sendReceiver.send(new HelpReq());
                JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("help"), JOptionPane.INFORMATION_MESSAGE);
            } catch (SocketException ex) {
                JOptionPane.showMessageDialog(getParent(), rb.getString("serverStop"), rb.getString("error"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            history.capture(bHelp.getText());
        });

        bHistory.addActionListener(e -> {
            JOptionPane.showMessageDialog(getParent(), history.get(), rb.getString("lastCom"), JOptionPane.INFORMATION_MESSAGE);
            history.capture(bHistory.getText());
        });

//        bShow.addActionListener(e -> {
//            try {
//                sendReceiver.send(new ShowReq());
//                JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("elements"), JOptionPane.INFORMATION_MESSAGE);
//            } catch (SocketException ex) {
//                JOptionPane.showMessageDialog(getParent(), rb.getString("serverStop"), rb.getString("error"), JOptionPane.ERROR_MESSAGE);
//                System.exit(0);
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//            history.capture(bShow.getText());
//        });

        bPrAsc.addActionListener(e -> {
            try {
                sendReceiver.send(new PrintAscendingReq());
                JOptionPane.showMessageDialog(getParent(), sendReceiver.receiveString(), rb.getString("elSort"), JOptionPane.INFORMATION_MESSAGE);
            } catch (SocketException ex) {
                JOptionPane.showMessageDialog(getParent(), rb.getString("serverStop"), rb.getString("error"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            history.capture(bPrAsc.getText());
        });

//        bFCName.addActionListener(e -> {
//            setEnabled(false);
//            sendArgDialog.clearField();
//            sendArgDialog.setCom("filter_contains_name");
//            sendArgDialog.setVisible(true);
//            history.capture(bFCName.getText());
//        });
//
//        bClear.addActionListener(e -> {
//            JOptionPane.showMessageDialog(getParent(), rb.getString("noExec"), rb.getString("warning"), JOptionPane.INFORMATION_MESSAGE);
//            history.capture(bClear.getText());
//        });

        bScript.addActionListener(e -> {
            try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("C:\\Users\\Kirill\\Desktop\\scripts"));
            File script = null;
            int ret = chooser.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                script = chooser.getSelectedFile();
            }


                ScriptHandler.sendScript(sendReceiver, script);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            catch (NullPointerException ignored) {}
            history.capture(bScript.getText());
        });

        bExit.addActionListener(e -> System.exit(0));



        return panel;
    }

    public void changeLng(int index) {
        rb = ResourceBundle.getBundle("Gui");
        cb.setSelectedIndex(index);
        setTitle(rb.getString("window"));
        tabbedPane.setTitleAt(0, rb.getString("table"));
        tabbedPane.setTitleAt(1, rb.getString("graphics"));
        userLabel.setText(rb.getString("user"));
        parametersDialog.changeLang(rb);
        sendArgDialog.changeLang(rb);

    }


    public void setUsername(String username) {
        this.username = username;
        usernameLabel.setText(username);
    }

    public MyTableModel getModel() {
        return model;
    }
}
