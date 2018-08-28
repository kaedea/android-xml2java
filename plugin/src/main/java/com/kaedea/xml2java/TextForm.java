package com.kaedea.xml2java;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Kaede.
 * @version 2018/8/28.
 */
public class TextForm {

    private JPanel mainPanel;
    private JTextArea editXml;
    private JTextArea editJava;
    private JButton btnConfirm;

    private final JFrame jFrame;

    public TextForm(@NotNull String xml) {
        editXml.setText(xml);
        jFrame = new JFrame("Text Input");
        jFrame.setContentPane(mainPanel);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();

        btnConfirm.addActionListener(e -> {
            String text = Utils.convert(xml);
            editJava.setText(text);
        });

        btnConfirm.doClick();
    }

    public void show() {
        jFrame.setVisible(true);
    }
}
