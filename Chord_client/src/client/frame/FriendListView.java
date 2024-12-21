package client.frame;

import client.data.DataProvider;
import client.listener.PersonalChatButtonListener;
import client.model.LoginAccount;
import client.model.Member;
import client.model.OpenedViewList;
import client.network.ConnectionTermination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class FriendListView extends JFrame {

    private static final Font FONT = new Font("맑은 고딕", Font.PLAIN, 15);
    private static final Font BOLD_FONT = new Font("맑은 고딕", Font.BOLD, 15);
    private static final Font SMALL_FONT = new Font("맑은 고딕", Font.PLAIN, 12);
    private static final Font TITLE_FONT = new Font("맑은 고딕", Font.BOLD, 20);

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BUTTON_BACKGROUND_COLOR = new Color(59, 89, 182);
    private static final Color BUTTON_FOREGROUND_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color GRAY_TEXT_COLOR = Color.GRAY;

    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JPanel friendListPanel = new JPanel(new GridBagLayout());

    public FriendListView(Point location) {
        setTitle("Chord");
        setSize(370, 580);
        setLocation(location);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ConnectionTermination.getInstance().disconnect();
            }
        });

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.setBackground(BACKGROUND_COLOR);

        container.add(createTitlePanel(), BorderLayout.NORTH);
        container.add(createFriendListScrollPane(), BorderLayout.CENTER);
        container.add(createMenuPanel(), BorderLayout.SOUTH);

        addFriendListHeader();
        addFriendList();

        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BUTTON_BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("친구");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(BUTTON_FOREGROUND_COLOR);
        panel.add(titleLabel, BorderLayout.WEST);

        JButton addFriendButton = new JButton("친구 추가");
        styleButton(addFriendButton);
        addFriendButton.addActionListener(e -> {
            new AddFriendView(this);
            setEnabled(false);
        });
        panel.add(addFriendButton, BorderLayout.EAST);

        return panel;
    }

    private JScrollPane createFriendListScrollPane() {
        JScrollPane scrollPane = new JScrollPane(friendListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        return scrollPane;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.setBackground(BACKGROUND_COLOR);

        JButton friendButton = new JButton("친구");
        styleButton(friendButton);
        friendButton.setEnabled(false);
        panel.add(friendButton);

        JButton chatButton = new JButton("채팅");
        styleButton(chatButton);
        chatButton.addActionListener(e -> {
            setVisible(false);
            OpenedViewList.getInstance().setChatRoomListView(new ChatRoomListView(getLocation()));
        });
        panel.add(chatButton);

        return panel;
    }

    private void addFriendListHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth() - 25, 60));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        friendListPanel.add(headerPanel, gbc);

        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(BACKGROUND_COLOR);
        headerPanel.add(profilePanel, BorderLayout.CENTER);

        JPanel namePanel = new JPanel(new GridLayout(2, 1));
        namePanel.setBackground(BACKGROUND_COLOR);

        JLabel nameLabel = new JLabel(LoginAccount.getInstance().getMyInfo().getName());
        nameLabel.setFont(BOLD_FONT);
        nameLabel.setForeground(TEXT_COLOR);
        namePanel.add(nameLabel);

        JLabel statusLabel = new JLabel(LoginAccount.getInstance().getMyInfo().getStatusMessage());
        statusLabel.setFont(SMALL_FONT);
        statusLabel.setForeground(GRAY_TEXT_COLOR);
        namePanel.add(statusLabel);

        profilePanel.add(namePanel, BorderLayout.WEST);

        JButton editButton = new JButton("편집");
        styleButton(editButton);
        editButton.setPreferredSize(new Dimension(50, 25)); // 버튼 크기 조정
        editButton.addActionListener(e -> {
            new SetStatusMessageView(this);
            setEnabled(false);
        });
        profilePanel.add(editButton, BorderLayout.EAST);
    }

    private void addFriendList() {
        ArrayList<Member> memberData = DataProvider.getInstance().getMemberData();
        int row = 1;

        for (Member member : memberData) {
            JPanel friendPanel = createFriendPanel(member);
            gbc.gridx = 0;
            gbc.gridy = row++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.NORTH; // 위쪽 정렬
            friendListPanel.add(friendPanel, gbc);
        }

        // 상단에 붙이기 위해 여백을 제거
        gbc.weighty = 1.0;
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(BACKGROUND_COLOR);
        friendListPanel.add(emptyPanel, gbc);
    }

    private JPanel createFriendPanel(Member member) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setPreferredSize(new Dimension(getWidth() - 25, 60));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(BACKGROUND_COLOR);

        JLabel nameLabel = new JLabel(member.getName());
        nameLabel.setFont(BOLD_FONT);
        nameLabel.setForeground(TEXT_COLOR);
        infoPanel.add(nameLabel);

        JLabel statusLabel = new JLabel(member.getStatusMessage());
        statusLabel.setFont(SMALL_FONT);
        statusLabel.setForeground(GRAY_TEXT_COLOR);
        infoPanel.add(statusLabel);

        panel.add(infoPanel, BorderLayout.WEST);

        JButton chatButton = new JButton("채팅");
        styleButton(chatButton);
        chatButton.setPreferredSize(new Dimension(50, 25)); // 버튼 크기 조정
        chatButton.addActionListener(new PersonalChatButtonListener(member.getUserId()));
        panel.add(chatButton, BorderLayout.EAST);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(FONT);
        button.setBackground(BUTTON_BACKGROUND_COLOR);
        button.setForeground(BUTTON_FOREGROUND_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
}
