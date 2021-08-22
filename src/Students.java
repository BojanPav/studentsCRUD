import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Students {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtMobile;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;
    private JScrollPane table_1;

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/studentscrud", "root", "SrbijadoTokija2012!");
            System.out.println("Successs");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void table_load() {
        try {
            pst = con.prepareStatement("select * from students");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Students");
        frame.setContentPane(new Students().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public Students() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String students_name, students_email, students_mobile;
                students_name = txtName.getText();
                students_email = txtEmail.getText();
                students_mobile = txtMobile.getText();

                try {
                    pst = con.prepareStatement("insert into students(students_name,students_email,students_mobile)values(?,?,?)");
                    pst.setString(1, students_name);
                    pst.setString(2, students_email);
                    pst.setString(3, students_mobile);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!!!!!");
                    table_load();
                    txtName.setText("");
                    txtEmail.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }

            }


        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String students_id = txtid.getText();

                    pst = con.prepareStatement("select students_name,students_email,students_mobile from students where students_id = ?");
                    pst.setString(1, students_id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String students_name = rs.getString(1);
                        String students_email = rs.getString(2);
                        String students_mobile = rs.getString(3);

                        txtName.setText(students_name);
                        txtEmail.setText(students_email);
                        txtMobile.setText(students_mobile);

                    } else {
                        txtName.setText("");
                        txtEmail.setText("");
                        txtMobile.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Students ID");

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String students_id, students_name, students_email, students_mobile;
                students_name = txtName.getText();
                students_email = txtEmail.getText();
                students_mobile = txtMobile.getText();
                students_id = txtid.getText();

                try {
                    pst = con.prepareStatement("update students set students_name = ?,students_email = ?,students_mobile= ? where students_id = ?");
                    pst.setString(1, students_name);
                    pst.setString(2, students_email);
                    pst.setString(3, students_mobile);
                    pst.setString(4, students_id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    table_load();
                    txtName.setText("");
                    txtEmail.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String students_id;
                students_id = txtid.getText();

                try {
                    pst = con.prepareStatement("delete from students  where students_id = ?");

                    pst.setString(1, students_id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted");
                    table_load();
                    txtName.setText("");
                    txtEmail.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }

        });
    }
}





