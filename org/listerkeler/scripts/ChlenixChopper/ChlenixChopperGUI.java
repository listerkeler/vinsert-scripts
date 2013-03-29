package org.listerkeler.scripts.ChlenixChopper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChlenixChopperGUI extends JFrame implements ActionListener {

    ChlenixChopper context;
    private static final long serialVersionUID = 1L;

    JButton btnStart;
    JComboBox comboTrees;
    JLabel lblTreesToChop;

    public ChlenixChopperGUI(ChlenixChopper script) {
        context = script;

        setFont(new Font("Segoe UI", Font.PLAIN, 12));
        setTitle("ChlenixChopper Settings");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        lblTreesToChop = new JLabel("Trees to chop:");
        lblTreesToChop.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTreesToChop.setBounds(10, 13, 75, 14);
        add(lblTreesToChop);

        comboTrees = new JComboBox();
        comboTrees.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        comboTrees.setModel(new DefaultComboBoxModel(new String[]{"Willows", "Maples", "Yews", "Magics"}));
        comboTrees.setBounds(95, 11, 159, 20);
        add(comboTrees);

        btnStart = new JButton("Start");
        btnStart.addActionListener(this);
        btnStart.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnStart.setBounds(10, 38, 244, 23);
        add(btnStart);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("I'm being called!");
        if (e.getSource().equals(btnStart)) {
            System.out.println(comboTrees.getSelectedItem());
            context.waitForGUI = false;
        }
    }
}
