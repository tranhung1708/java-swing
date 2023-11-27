package work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

class LoginForm extends JFrame {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/world";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("Đăng nhập");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for layout
        panel.add(loginButton);

        add(panel);

        setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            String sql = "SELECT * FROM city WHERE Name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Lấy mật khẩu từ cơ sở dữ liệu
                String storedPassword = resultSet.getString("District");

                if (storedPassword.equals(String.valueOf(password))) {
                    JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                }else {
                    JOptionPane.showMessageDialog(this, "Đăng nhập không thành công. Vui lòng kiểm tra lại tên đăng nhập và mật khẩu.");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Người dùng không tồn tại.");
            }

        }catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        usernameField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm();
            }
        });
    }
}
