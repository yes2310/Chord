package client.frame;

import client.listener.LoginButtonListener;
import client.network.ConnectionTermination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginView extends JFrame {

    public LoginView(Point location) {
        // Modern LookAndFeel 적용
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Chord - Login");
        setSize(400, 600);
        if (location != null)
            setLocation(location);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ConnectionTermination.getInstance().disconnect();
            }
        });

        // 공통 폰트 설정
        Font font = new Font("맑은 고딕", Font.PLAIN, 16);
        Font logoFont = new Font("맑은 고딕", Font.BOLD, 32);

        // 메인 패널
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        getContentPane().add(mainPanel);

        // 상단 로고 패널
        JPanel logoPanel = new JPanel(new GridBagLayout()); // GridBagLayout 사용
        logoPanel.setBackground(new Color(59, 89, 182));
        logoPanel.setPreferredSize(new Dimension(400, 150));

        GridBagConstraints gbcLogo = new GridBagConstraints();
        gbcLogo.gridx = 0;
        gbcLogo.gridy = 0;
        gbcLogo.weightx = 1.0;
        gbcLogo.weighty = 1.0;
        gbcLogo.anchor = GridBagConstraints.CENTER;

        JLabel logoLabel = new JLabel("Chord");
        logoLabel.setFont(logoFont);
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel, gbcLogo);

        mainPanel.add(logoPanel, BorderLayout.NORTH);

        // 중앙 로그인 패널
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userIdLabel = new JLabel("아이디");
        userIdLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        loginPanel.add(userIdLabel, gbc);

        JTextField userIdField = new JTextField(15);
        userIdField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(userIdField, gbc);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("로그인");
        loginButton.setFont(font);
        loginButton.setBackground(new Color(59, 89, 182));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new LoginButtonListener(userIdField, passwordField));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(loginButton, gbc);

        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // 하단 버튼 패널
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.WHITE);
        JButton registerButton = new JButton("회원가입");
        registerButton.setFont(font);
        registerButton.setBackground(Color.LIGHT_GRAY);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> {
            setVisible(false);
            new RegisterView(getLocation());
        });

        southPanel.add(registerButton, BorderLayout.CENTER);
        southPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}