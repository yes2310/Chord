package client.frame;

import client.data.DataProvider;
import client.listener.SendMessageButtonListener;
import client.model.Message;
import client.model.OpenedViewList;
import client.runnable.ThreadLock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Objects;

public class ChatRoomView extends JFrame {

    private final JTextArea messageListArea;
    private final int roomId;

    public ChatRoomView(int roomId) {
        this.roomId = roomId;

        setTitle(DataProvider.getInstance().getChatRoom(roomId).getName());
        setSize(370, 580);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                OpenedViewList.getInstance().getOpenedChatRoomView().remove(roomId);
            }
        });

        Font font = new Font("맑은 고딕", Font.PLAIN, 15);
        Font titleFont = new Font("맑은 고딕", Font.BOLD, 20);

        Color primaryColor = new Color(59, 89, 182); 
        Color backgroundColor = Color.WHITE; 
        Color messageAreaColor = new Color(240, 240, 240); 
        Color buttonColor = primaryColor; 
        Color buttonTextColor = Color.WHITE; 

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.setBackground(backgroundColor);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(primaryColor); 
        container.add(northPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel(DataProvider.getInstance().getChatRoom(roomId).getName());
        titleLabel.setFont(titleFont);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setForeground(Color.WHITE);
        northPanel.add(titleLabel, BorderLayout.WEST);

        JButton contactButton = new JButton("대화 상대");
        contactButton.setFont(font);
        contactButton.setBackground(Color.WHITE);
        contactButton.setForeground(primaryColor);
        contactButton.setFocusPainted(false);
        contactButton.setBorder(BorderFactory.createLineBorder(primaryColor));
        contactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (ThreadLock.lock) {
                    DataProvider.getInstance().loadChatRoomMemberData(roomId);
                }
                OpenedViewList.getInstance().getOpenedContactView().put(roomId, new ContactView((ChatRoomView) contactButton.getTopLevelAncestor(), roomId));
                setEnabled(false);
            }
        });
        northPanel.add(contactButton, BorderLayout.EAST);

        messageListArea = new JTextArea();
        messageListArea.setFont(font);
        messageListArea.setForeground(Color.BLACK);
        messageListArea.setLineWrap(true);
        messageListArea.setEditable(false);
        messageListArea.setBackground(messageAreaColor);
        ArrayList<Message> messageList = DataProvider.getInstance().getMessageData(roomId);
        messageListArea.setText("");
        for (Message message : messageList) {
            String text = messageListArea.getText();
            if (!Objects.equals(message.getMessageType(), "info")) {
                messageListArea.setText(text + message.getUserName() + " >> " + message.getMessage() + "\n");
            } else {
                messageListArea.setText(text + message.getMessage() + "\n");
            }
        }

        JScrollPane messageListPanelScroll = new JScrollPane(messageListArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        container.add(messageListPanelScroll, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());
        southPanel.setBackground(backgroundColor);
        container.add(southPanel, BorderLayout.SOUTH);

        JTextArea messageArea = new JTextArea(3, 45);
        messageArea.setFont(font);
        messageArea.setLineWrap(true);
        messageArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        messageArea.setBackground(Color.WHITE);

        JScrollPane messageAreaScrollPane = new JScrollPane(messageArea);
        messageAreaScrollPane.setPreferredSize(new Dimension(270, 90));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        southPanel.add(messageAreaScrollPane, gbc);

        JPanel sendMessageButtonPanel = new JPanel();
        sendMessageButtonPanel.setLayout(new BorderLayout());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.CENTER;
        southPanel.add(sendMessageButtonPanel, gbc);

        JButton sendMessageButton = new JButton("전송");
        sendMessageButton.setFont(font);
        sendMessageButton.setBackground(buttonColor); 
        sendMessageButton.setForeground(buttonTextColor);
        sendMessageButton.setFocusPainted(false);
        sendMessageButton.addActionListener(new SendMessageButtonListener(roomId, messageArea));
        sendMessageButtonPanel.add(sendMessageButton, BorderLayout.CENTER);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.LIGHT_GRAY);
        northPanel.add(separator, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void repaint() {
        super.repaint();
        ArrayList<Message> messageList = DataProvider.getInstance().getMessageData(roomId);
        messageListArea.setText("");
        for (Message message : messageList) {
            String text = messageListArea.getText();
            if (!Objects.equals(message.getMessageType(), "info")) {
                messageListArea.setText(text + message.getUserName() + " >> " + message.getMessage() + "\n");
            } else {
                messageListArea.setText(text + message.getMessage() + "\n");
            }
        }
    }
}
