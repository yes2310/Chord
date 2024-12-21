package client.frame;

import client.listener.SetStatusMessageButtonListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SetStatusMessageView extends JFrame {
    public SetStatusMessageView(FriendListView view) {
        setTitle("상태 공유");
        setSize(400, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                view.setEnabled(true);
            }
        });

        // 폰트 설정
        Font font = new Font("맑은 고딕", Font.PLAIN, 15);
        Font smallFont = new Font("맑은 고딕", Font.PLAIN, 12);
        Font titleFont = new Font("맑은 고딕", Font.BOLD, 20);

        // 메인 컨테이너 설정
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.setBackground(Color.WHITE);

        // 제목 패널
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(new EmptyBorder(20, 0, 10, 0)); // 위쪽 20, 아래쪽 10 여백 추가
        JLabel titleLabel = new JLabel("상대에게 내 상태 알리기!");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.DARK_GRAY);
        titlePanel.add(titleLabel);
        container.add(titlePanel, BorderLayout.NORTH);

        // 상태 메시지 입력 패널
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 상태 메시지 입력 필드
        JTextField statusMessageField = new JTextField(20);
        statusMessageField.setFont(font);
        statusMessageField.setBackground(Color.WHITE);
        statusMessageField.setForeground(Color.DARK_GRAY);
        statusMessageField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true)); // 둥근 테두리
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(statusMessageField, gbc);

        // 안내 라벨
        JLabel infoLabel = new JLabel("어떤 상태이신가요?");
        infoLabel.setFont(smallFont);
        infoLabel.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(infoLabel, gbc);

        container.add(inputPanel, BorderLayout.CENTER);

        // 설정 버튼 패널
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 20, 0)); // 아래쪽 20 여백 추가

        JButton setStatusMessageButton = new JButton("    설정    ");
        setStatusMessageButton.setFont(font);
        setStatusMessageButton.setBackground(Color.WHITE);
        setStatusMessageButton.setForeground(Color.DARK_GRAY);
        setStatusMessageButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true)); // 둥근 테두리
        setStatusMessageButton.setMargin(new Insets(5, 20, 5, 20)); // 좌우 여백 추가
        setStatusMessageButton.addActionListener(new SetStatusMessageButtonListener(view, statusMessageField));
        buttonPanel.add(setStatusMessageButton);

        container.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}