package com.transformtoascii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InterfaceSwing {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceSwing.class);

    JTextArea textArea = new JTextArea();
    AsciiConverter asciiConverter = new AsciiConverter();
    ImageProcessor imageProcessor;
    double scaleImageFactor = 0.5;
    File fileToLoad = null;

    public void showInterface() {
        LOGGER.info("Swing interface is starting...");

        JFrame frame = new JFrame("Transform image to ASCII");
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(false);

        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton btnSelectFile = new JButton("Select file");
        JButton btnReaload = new JButton("Reload Image");
        SpinnerModel model = new SpinnerNumberModel(0.5, 0.05, 2, 0.05);
        JSpinner spFactor = new JSpinner(model);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnSelectFile);
        buttonPanel.add(btnReaload);
        buttonPanel.add(spFactor);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //Events
        btnSelectFile.addActionListener(this::chooseFile);
        btnReaload.addActionListener(this::reloadImage);
        spFactor.addChangeListener(this::changeFactor);

        frame.setSize(900,600);
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
            fileToLoad = file.getSelectedFile();
            String path = fileToLoad.getAbsolutePath();
            LOGGER.debug("Img path : {}", path);

            loadFileAndTransformToAscii();
        }
    }

    private void changeFactor(ChangeEvent e) {
        this.scaleImageFactor = (double) ((JSpinner)e.getSource()).getValue();

        //Reload image
        loadFileAndTransformToAscii();
    }

    private void reloadImage(ActionEvent e) {
        if(fileToLoad == null || !fileToLoad.isFile()) {
            LOGGER.error("Cannot load or reload image. File is null or not a valid file.");
        } else {
            loadFileAndTransformToAscii();
        }
    }

    private void loadFileAndTransformToAscii() {

        try {
            BufferedImage image = ImageIO.read(fileToLoad);
            imageProcessor = new ImageProcessor(fileToLoad.getAbsolutePath());
            BufferedImage resizedImage = imageProcessor.resizeImageWithFactor(image, scaleImageFactor);
            String asciiArt = asciiConverter.convertToAscii(resizedImage, 2,4, false);
            textArea.setText(asciiArt);
        } catch (IOException ex) {
            LOGGER.error("Error while loading image", ex);
        }

    }

}
