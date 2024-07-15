package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.io.*;
import java.util.*;

public class LoginPage implements ActionListener {
    private JFrame frame = new JFrame();
    private JButton loginBt = new JButton("Login");
    private JButton signUp = new JButton("Sign up");
    private JTextField userField = new JTextField();
    private JPasswordField userPassword = new JPasswordField();
    private JLabel userLabel = new JLabel("Username: ");
    private JLabel userPasswordLabel = new JLabel("Password: ");
    private JLabel messageLabel = new JLabel();
    private JLabel loginLabel = new JLabel("Brick Breaker");
    private HashMap<String, String> loginInfo = new HashMap<String, String>();
    private String username;
    private String password;
    private static final String USERS_FILE = "users.txt";

    public LoginPage() {

        loginLabel.setBounds(125, 30, 200, 35);
        loginLabel.setFont(new Font(null, Font.BOLD, 25));
        loginLabel.setForeground(Color.blue);

        userLabel.setBounds(50, 100, 75, 25);
        userPasswordLabel.setBounds(50, 150, 75, 25);

        messageLabel.setBounds(125, 250, 250, 35);
        messageLabel.setFont(new Font(null, Font.PLAIN, 20));

        userField.setBounds(125, 100, 200, 25);
        userPassword.setBounds(125, 150, 200, 25);

        loginBt.setBounds(125, 200, 100, 25);
        loginBt.setFocusable(false);
        loginBt.addActionListener(this);

        signUp.setBounds(225, 200, 100, 25);
        signUp.setFocusable(false);
        signUp.addActionListener(this);

        frame.add(loginLabel);
        frame.add(userLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userField);
        frame.add(userPassword);
        frame.add(loginBt);
        frame.add(signUp);
        frame.setTitle("Brick Breaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }



    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (HashMap.Entry<String, String> entry : loginInfo.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        JFrame frame = new JFrame("Brick Breaker");
        // JFrame frame = new Game();
        Game gamePlay = new Game(this);
        frame.setBounds(10, 10, 700, 600);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePlay);
    }

    public void actionPerformed(ActionEvent e) {
        username = String.valueOf(userField.getText());
        password = String.valueOf(userPassword.getPassword());
        

        if (e.getSource() == signUp) {
            loginInfo.put(username, password);
            saveUsers();

            messageLabel.setForeground(Color.green);
            messageLabel.setText("Login successful");
            startGame();
            // boolean userFound = false;
            // String inputtedUser = String.valueOf(userField.getText());
            // // enhanced for loop
            // for (String user : loginInfo.keySet()) {
            //     if (user.equals(inputtedUser)) {
            //         userFound = true;
            //         messageLabel.setForeground(Color.red);
            //         messageLabel.setText("Username already taken");
            //         System.out.println(userFound);
            //     }
            // }
            // if (!userFound) {
            //     username = inputtedUser;
            // }

            // password = String.valueOf(userPassword.getPassword());
            // if (!username.equals("") && !password.equals("")) {
            //     loginInfo.put(username, password);
            //     messageLabel.setForeground(Color.green);
            //     messageLabel.setText("Login successful");
            //     startGame();
            // }
        }

        if (e.getSource() == loginBt) {
            if (loginInfo.containsKey(username)) {
                if (loginInfo.get(username).equals(password)) {
                    frame.dispose();
                    startGame();
                } else {
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("Incorrect password");
                }
            } else {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Incorrect username");
            }
        }

    }

    public String getUsername() {
        return username;
    }
}
