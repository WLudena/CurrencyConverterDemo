package com.home.frame;

import com.home.data.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

public class MainFrame extends JFrame {

    private static DecimalFormat df = new DecimalFormat("0.000");

    public MainFrame(String key, Data data) {

        super("CurrConv");
        data.checkFile(); //perform initial check on start
        setSize(200, 250);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        JPanel refresh = new JPanel();
        refresh.setPreferredSize(new Dimension(200,80));
        refresh.setLayout(new FlowLayout());

        JButton convertBtn = new JButton("Convert...");
        JButton refreshBtn = new JButton("Refresh");

        JLabel lastUpdateLabel = new JLabel("Last update:");
        JLabel lastUpdateDate = new JLabel(data.getLastUpdate());
        refresh.add(lastUpdateLabel);
        refresh.add(lastUpdateDate);

        JComboBox<String> baseCurrency = new JComboBox<>(data.getCurrencies());
        baseCurrency.setEditable(true);

        JComboBox<String> targetCurrency = new JComboBox<>(data.getCurrencies());
        targetCurrency.setEditable(true);

        JTextField in = new JTextField(5);
        JTextField out = new JTextField(5);

        out.setEditable(false);

        convertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double curr1Val = data.getCurrencyValue(baseCurrency.getSelectedItem().toString());
                double curr2Val = data.getCurrencyValue(targetCurrency.getSelectedItem().toString());
                double input =  Double.parseDouble(in.getText());

                out.setText(String.valueOf(df.format(input * (curr2Val/curr1Val))));
            }
        });

        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                data.checkFile();
                lastUpdateDate.setText(data.getLastUpdate());
            }
        });

        buttons.add(convertBtn);
        refresh.add(refreshBtn);

        controlPanel.add(baseCurrency);
        controlPanel.add(targetCurrency);
        controlPanel.add(in);
        controlPanel.add(out);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        controlPanel.setLayout(new FlowLayout());
        controlPanel.setBackground(Color.red);

        add(refresh,BorderLayout.PAGE_START);
        add(controlPanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.PAGE_END);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }
}
