package client.frame;

import client.listener.AddFriendButtonListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddFriendView extends JFrame {
    public AddFriendView(FriendListView view) {
        setTitle("Add Friend");
        setSize(400, 250);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Helvetica Neue", Font.PLAIN, 14);
        Font smallFont = new Font("Helvetica Neue", Font.PLAIN, 12);
        Font titleFont = new Font("Helvetica Neue", Font.BOLD, 18);

        Color backgroundColor = Color.WHITE;  
        Color primaryColor = new Color(59, 89, 182);  
        Color buttonColor = new Color(30, 80, 140); 
        Color textColor = new Color(50, 50, 50); 

        Container container = getContentPane();
        container.setBackground(backgroundColor);
        container.setLayout(new BorderLayout(20, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(backgroundColor);
        JLabel titleLabel = new JLabel("친구 추가");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(primaryColor);
        titlePanel.add(titleLabel);
        container.add(titlePanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField friendIdField = new JTextField(20);
        friendIdField.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(friendIdField, gbc);

        JLabel infoLabel = new JLabel("추가하고자 하는 친구의 ID를 입력하세요.");
        infoLabel.setFont(smallFont);
        infoLabel.setForeground(textColor);
        gbc.gridy = 1;
        inputPanel.add(infoLabel, gbc);

        container.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        JButton addFriendButton = new JButton("친구 추가");
        addFriendButton.setFont(font);
        addFriendButton.setBackground(buttonColor);
        addFriendButton.setForeground(Color.WHITE);
        addFriendButton.setFocusPainted(false);
        addFriendButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addFriendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addFriendButton.addActionListener(new AddFriendButtonListener(view, friendIdField));
        buttonPanel.add(addFriendButton);
        container.add(buttonPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                view.setEnabled(true);
            }
        });

        setVisible(true);
    }
}
