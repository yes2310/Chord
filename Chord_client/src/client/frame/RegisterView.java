package client.frame;

import client.listener.RegisterButtonListener;
import client.network.ConnectionTermination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterView extends JFrame {

    public RegisterView(Point location) {
        // Modern LookAndFeel 적용
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Chord - Register");
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
        Font titleFont = new Font("맑은 고딕", Font.BOLD, 32);

        // 메인 패널
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        getContentPane().add(mainPanel);

        // 상단 패널
        JPanel northPanel = new JPanel(new GridBagLayout()); // GridBagLayout 사용
        northPanel.setBackground(new Color(59, 89, 182));
        northPanel.setPreferredSize(new Dimension(400, 150));

        GridBagConstraints gbcLogo = new GridBagConstraints();
        gbcLogo.gridx = 0;
        gbcLogo.gridy = 0;
        gbcLogo.weightx = 1.0;
        gbcLogo.weighty = 1.0;
        gbcLogo.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Chord 회원가입");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        northPanel.add(titleLabel, gbcLogo);
        mainPanel.add(northPanel, BorderLayout.NORTH);

        // 중앙 입력 패널
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userIdLabel = new JLabel("아이디");
        userIdLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        registerPanel.add(userIdLabel, gbc);

        JTextField userIdField = new JTextField(15);
        userIdField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 0;
        registerPanel.add(userIdField, gbc);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        registerPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 1;
        registerPanel.add(passwordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("비밀번호 확인");
        confirmPasswordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(confirmPasswordLabel, gbc);

        JPasswordField confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 2;
        registerPanel.add(confirmPasswordField, gbc);

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(15);
        nameField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 3;
        registerPanel.add(nameField, gbc);

        JButton registerButton = new JButton("회원가입");
        registerButton.setFont(font);
        registerButton.setBackground(new Color(59, 89, 182));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(new RegisterButtonListener(userIdField, passwordField, confirmPasswordField, nameField));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        registerPanel.add(registerButton, gbc);

        mainPanel.add(registerPanel, BorderLayout.CENTER);

        // 하단 패널
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.WHITE);
        JButton goLoginButton = new JButton("로그인");
        goLoginButton.setFont(font);
        goLoginButton.setBackground(Color.LIGHT_GRAY);
        goLoginButton.setFocusPainted(false);
        goLoginButton.addActionListener(e -> {
            setVisible(false);
            new LoginView(getLocation());
        });

        JLabel goLoginLabel = new JLabel("이미 계정이 있으신가요?");
        goLoginLabel.setFont(font);
        goLoginLabel.setHorizontalAlignment(JLabel.CENTER);

        southPanel.add(goLoginLabel, BorderLayout.NORTH);
        southPanel.add(goLoginButton, BorderLayout.CENTER);
        southPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}