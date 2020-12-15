package com.home.frame;

import com.home.data.Data;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame(){
        super("CurrConv");
        setSize(250, 175);
        setResizable(false);
        setLayout(new BorderLayout());

        Data dataHandler = new Data();

        JPanel buttons = new JPanel();
        JPanel credentials = new JPanel();

        JLabel error = new JLabel();
        JLabel keyLabel = new JLabel("Enter Fixer Key:");
        JTextField key = new JTextField(20);

        JButton signInBtn = new JButton("Sign in");

        signInBtn.addActionListener(e -> {
            if(key.getText().isEmpty()){
                error.setText("No Key Entered");
            }
            else if(dataHandler.isKeyValid(key.getText())){
                new MainFrame(key.getText(), dataHandler);
                dispose();
            }else{
                error.setText("Wrong Key! Try again");
            }
        });

        credentials.setLayout(new FlowLayout());
        buttons.setLayout(new FlowLayout());
        credentials.add(keyLabel);
        credentials.add(key);
        credentials.add(error);
        buttons.add(signInBtn);

        add(buttons, BorderLayout.PAGE_END);
        add(credentials, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
