package zad1;

import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Database {
    String path;
    TravelData td;
    Connection connection;
    Database(String path, TravelData td){
        this.path=path;
        this.td=td;
    }

    void create(){
        try{
            PreparedStatement ps = connection.prepareStatement("Insert into Oferta Values(?,?,?,?,?,?,?)");
            int count=1;
            String sqlCommand = "Create Table Oferta(" + "id int PRIMARY KEY, " + "kraj varchar(30), " + "date_wyjazdu Date, " + "date_powrotu Date, " + "miejsce varchar(30), " + "cene varchar(15), " + "waluta varchar(10))";

            for(TravelData td : TravelData.oferty){
                ps.setInt(1, count);
                count=count+1;
                ps.setString(2, td.celPodrozy);
                ps.setString(3,td.dataWyjazdu);
                ps.setString(4,td.dataPowrotu);
                ps.setString(5,td.miejsce);
                ps.setString(6,td.cena);
                ps.setString(7,td.waluta);
                ps.executeUpdate();

                connection.createStatement().execute(sqlCommand);
            }
        }
        catch(Exception e){
        }
    }JTable getTable(String loc){
        int dat1=1;
        int dat2 =2;
        int height=100;
        String[] columnNames={"Kraj","Date Wyjazdu","Date Powrotu","Miejsce","cene","Waluta"};
        Object[][] obj = (TravelData.oferty).stream().map(oferta ->
                new Object[]{oferta.showCtr(loc), oferta.showDt("yyyy-MM-dd",dat1), oferta.showDt("yyyy-MM-dd",dat2),
                        oferta.showPl(loc),oferta.showNr(loc),oferta.showCurr()}
        ).toArray(size->new Object[size][4]);
        DefaultTableModel mod;
        mod= new DefaultTableModel(obj, columnNames);
        //JPanel jPanel = new JPanel();
        JTable jtable;
        jtable=new JTable(mod);
        jtable.setRowHeight(height);
        //jtable.getColumnCount();
        return jtable;
    }

    public void showGui() {
        Statement statement;
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                JFrame frame=new JFrame("FRAME");
                frame.pack();
                frame.setLocationRelativeTo(null);
                JPanel jPanel=new JPanel();
                frame.setSize(new Dimension(230,80));
                JTextField myTitle = new JTextField();
                myTitle = new JTextField();
                myTitle.setBounds(80, 40, 225, 20);
                myTitle.add(jPanel);
                JButton jb1=new JButton("Polski");
                jb1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTable jTable1 = getTable("pl_PL");
                        JFrame frame1 = new JFrame("Wyjazdy");
                        JScrollPane jScrollPane = new JScrollPane(jTable1);
                        //frame2.setLocation(100,300);
                        //jTable2.getColumnCount();

                        frame1.add(jScrollPane);
                        frame1.pack();
                        //jb2.addActionListener(ActionEvent ex){ };
                        //jScrollPane.createVerticalScrollBar();
                        frame1.setLocationRelativeTo(null);
                        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame1.setVisible(true);
                    }
                });
                JButton jb2=new JButton("English");
                jb2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTable jTable2 = getTable("en_GB");
                        JFrame frame2 = new JFrame("Trips");
                        JScrollPane jScrollPane = new JScrollPane(jTable2);
                        //frame2.setLocation(100,300);
                        //jTable2.getColumnCount();

                        frame2.add(jScrollPane);
                        frame2.pack();
                        //jb2.addActionListener(ActionEvent ex){ };
                        //jScrollPane.createVerticalScrollBar();
                        frame2.setLocationRelativeTo(null);
                        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame2.setVisible(true);
                    }
                });
                jPanel.add(jb1);
                jPanel.add(jb2);
                frame.add(jPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
