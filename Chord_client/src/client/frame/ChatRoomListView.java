package client.frame;

import client.data.DataProvider;
import client.listener.GroupChatButtonListener;
import client.listener.LeaveChatRoomButtonListener;
import client.model.ChatRoom;
import client.model.OpenedViewList;
import client.network.ConnectionTermination;
import client.runnable.ThreadLock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatRoomListView extends JFrame {

    private GridBagConstraints gbc = new GridBagConstraints();
    private Font font = new Font("맑은 고딕", Font.PLAIN, 15);
    private Font boldFont = new Font("맑은 고딕", Font.BOLD, 13);
    private Font smallFont = new Font("맑은 고딕", Font.PLAIN, 12);
    private Font miniFont = new Font("맑은 고딕", Font.PLAIN, 11);
    private Font titleFont = new Font("맑은 고딕", Font.BOLD, 20);
    private JPanel chatRoomListPanel;

    // 색상 설정
    private Color backgroundColor = Color.WHITE; // 전체 배경색
    private Color buttonBackgroundColor = new Color(59, 89, 182); // 버튼 배경색
    private Color buttonForegroundColor = Color.WHITE; // 버튼 텍스트 색상
    private Color titleBackgroundColor = new Color(59, 89, 182); // 상단바 배경색
    private Color textColor = Color.BLACK; // 기본 텍스트 색상
    private Color grayTextColor = Color.GRAY; // 회색 텍스트 색상

    public ChatRoomListView(Point location) {
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
        container.setBackground(backgroundColor);
        container.setLayout(new BorderLayout());

        JPanel northPanel = createTitlePanel();
        container.add(northPanel, BorderLayout.NORTH);

        chatRoomListPanel = new JPanel(new GridBagLayout());
        chatRoomListPanel.setBackground(backgroundColor);

        JScrollPane scrollPane = new JScrollPane(chatRoomListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(backgroundColor);
        container.add(scrollPane, BorderLayout.CENTER);

        JPanel menuPanel = createMenuPanel();
        container.add(menuPanel, BorderLayout.SOUTH);

        updateChatRoomList();

        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(buttonBackgroundColor); // 상단바 전체 배경색 설정

        JLabel titleLabel = new JLabel("채팅");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(buttonForegroundColor); // 버튼 텍스트와 동일한 색상으로 설정
        panel.add(titleLabel, BorderLayout.WEST);

        JButton createChatRoomButton = new JButton("새로운 채팅");
        createChatRoomButton.setFont(font);
        styleButton(createChatRoomButton); // 버튼 스타일링
        createChatRoomButton.addActionListener(new GroupChatButtonListener());
        panel.add(createChatRoomButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.setBackground(backgroundColor);

        JButton friendButton = new JButton("친구");
        friendButton.setFont(font);
        styleButton(friendButton);
        friendButton.addActionListener(e -> {
            setVisible(false);
            new FriendListView(getLocation());
        });
        panel.add(friendButton);

        JButton chatButton = new JButton("채팅");
        chatButton.setFont(font);
        styleButton(chatButton);
        chatButton.setEnabled(false);
        panel.add(chatButton);

        return panel;
    }

    private void updateChatRoomList() {
        ArrayList<ChatRoom> chatRoomData = DataProvider.getInstance().getChatRoomData();
        chatRoomListPanel.removeAll();
        int i = 0;
        for (ChatRoom chatRoom : chatRoomData) {
            JPanel chatRoomPanel = createChatRoomPanel(chatRoom);
            gbc.gridx = 0;
            gbc.gridy = i++;
            gbc.fill = GridBagConstraints.HORIZONTAL; // 가로로만 늘어나도록 설정
            gbc.anchor = GridBagConstraints.NORTH; // 상단 정렬 유지
            gbc.weighty = 0; // 여백 분배를 막음
            chatRoomListPanel.add(chatRoomPanel, gbc);
        }

        // 빈 패널을 추가해 여백을 아래쪽으로 강제로 밀어냄
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.weighty = 1; // 남은 공간은 이 패널이 모두 차지
        gbc.fill = GridBagConstraints.BOTH;
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(backgroundColor);
        chatRoomListPanel.add(emptyPanel, gbc);

        chatRoomListPanel.revalidate();
        chatRoomListPanel.repaint();
    }

    private JPanel createChatRoomPanel(ChatRoom chatRoom) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setBackground(backgroundColor);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(backgroundColor);

        // 채팅방 이름: 왼쪽 정렬
        JLabel nameLabel = new JLabel(chatRoom.getName());
        nameLabel.setFont(boldFont);
        nameLabel.setForeground(textColor);
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT); // 왼쪽 정렬
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // 그리드 배치도 왼쪽 정렬
        infoPanel.add(nameLabel, gbc);

        // 마지막 대화 내용: 왼쪽 정렬
        JLabel lastMessageLabel = new JLabel(chatRoom.getLastMessage() != null ? chatRoom.getLastMessage() : "");
        lastMessageLabel.setFont(smallFont);
        lastMessageLabel.setForeground(grayTextColor);
        lastMessageLabel.setHorizontalAlignment(SwingConstants.LEFT); // 왼쪽 정렬
        gbc.gridy = 1;
        infoPanel.add(lastMessageLabel, gbc);

        // 마지막 메시지 시간: 오른쪽 정렬
        JLabel lastTimeLabel = new JLabel(convertLastTime(chatRoom.getLastTime()));
        lastTimeLabel.setFont(miniFont);
        lastTimeLabel.setForeground(grayTextColor);
        lastTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT); // 오른쪽 정렬
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; // 그리드 배치도 오른쪽 정렬
        infoPanel.add(lastTimeLabel, gbc);

        panel.add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(backgroundColor);

        JButton enterButton = new JButton("입장");
        enterButton.setFont(miniFont);
        styleButton(enterButton);
        enterButton.addActionListener(e -> enterChatRoom(chatRoom));
        buttonPanel.add(enterButton);

        JButton leaveButton = new JButton("나가기");
        leaveButton.setFont(miniFont);
        styleButton(leaveButton);
        leaveButton.addActionListener(new LeaveChatRoomButtonListener(chatRoom.getRoomId()));
        buttonPanel.add(leaveButton);

        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setBackground(buttonBackgroundColor);
        button.setForeground(buttonForegroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void enterChatRoom(ChatRoom chatRoom) {
        if (OpenedViewList.getInstance().getOpenedChatRoomView().containsKey(chatRoom.getRoomId())) {
            JFrame openedView = OpenedViewList.getInstance().getOpenedChatRoomView().get(chatRoom.getRoomId());
            openedView.setState(JFrame.NORMAL);
            openedView.requestFocus();
        } else {
            synchronized (ThreadLock.lock) {
                DataProvider.getInstance().loadMessageData(chatRoom.getRoomId());
            }
            OpenedViewList.getInstance().getOpenedChatRoomView().put(chatRoom.getRoomId(), new ChatRoomView(chatRoom.getRoomId()));
        }
    }

    private String convertLastTime(Date lastTime) {
        SimpleDateFormat todayFormat = new SimpleDateFormat("a hh:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastCal = Calendar.getInstance();
        lastCal.setTime(lastTime);
        Calendar now = Calendar.getInstance();

        if (now.get(Calendar.YEAR) == lastCal.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) == lastCal.get(Calendar.DAY_OF_YEAR)) {
            return todayFormat.format(lastTime);
        } else {
            return dateFormat.format(lastTime);
        }
    }
}
