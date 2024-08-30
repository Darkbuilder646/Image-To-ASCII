package com.transformtoascii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class InterfaceSwing {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceSwing.class);

    JLabel label;
    public void showInterface() {
        LOGGER.info("Swing interface is starting...");

        JFrame frame = new JFrame("Transform image to ASCII");
        frame.setLayout(new BorderLayout());

        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        frame.add(label, BorderLayout.CENTER);

        JButton btn = new JButton("Select file");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btn);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        btn.addActionListener(this::chooseFile);

        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void chooseFile(ActionEvent e) {
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "png");
        file.addChoosableFileFilter(filter);
        int res = file.showOpenDialog(null);
        if(res == JFileChooser.APPROVE_OPTION) {
            File selectFile = file.getSelectedFile();
            String path = selectFile.getAbsolutePath();
            LOGGER.debug("Img path : {}", path);
            label.setIcon(resize(path, label.getWidth(), label.getHeight()));
        }
    }

    private ImageIcon resize(String imgPath, int maxWidth, int maxHeight) {
        ImageIcon icon = new ImageIcon(imgPath);
        Image img = icon.getImage();

        int originalWidth = img.getWidth(null);
        int originalHeight = img.getHeight(null);

        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);

        Image newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}
