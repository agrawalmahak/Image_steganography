import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class imageop2 {

    public static void operate(int key) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();

        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();

            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (data[i] ^ key);
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            JOptionPane.showMessageDialog(null, "Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Image Operation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel with a background hacking image
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image image = ImageIO.read(new File("C:/Users/Agraj Agrawal/Downloads/background.jpg"));
                    g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mainPanel.setPreferredSize(new Dimension(400, 200));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false); // Make the input panel transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel keyLabel = new JLabel("Key:");
        keyLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        keyLabel.setForeground(Color.WHITE);
        JTextField keyTextField = new JTextField(10);
        JButton openButton = new JButton("Open Image");
        openButton.setFont(new Font("Roboto", Font.BOLD, 16));
        openButton.setBackground(new Color(0, 153, 204));
        openButton.setForeground(Color.GREEN);

        openButton.addActionListener(e -> {
            String keyText = keyTextField.getText();
            int key = Integer.parseInt(keyText);
            operate(key);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(keyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(keyTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(openButton, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
