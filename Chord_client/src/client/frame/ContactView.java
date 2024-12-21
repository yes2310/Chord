package client.frame;

import client.data.DataProvider;
import client.model.LoginAccount;
import client.model.OpenedViewList;
import client.runnable.ThreadLock;
import client.service.AddFriendService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ContactView extends JFrame {

    GridBagConstraints gbc = new GridBagConstraints();
    Font font = new Font("맑은 고딕", Font.PLAIN, 15);
    Font boldFont = new Font("맑은 고딕", Font.BOLD, 15);
    Font smallFont = new Font("맑은 고딕", Font.PLAIN, 12);
    Font miniFont = new Font("맑은 고딕", Font.PLAIN, 11);
    Font titleFont = new Font("맑은 고딕", Font.BOLD, 20);
    JPanel contactListPanel;
    String friendId;
    int roomId;
    ChatRoomView view;

    Color navy = new Color(59, 89, 182); // 남색
    Color white = Color.WHITE;

    public ContactView(ChatRoomView view, int roomId) {
        this.view = view;
        this.roomId = roomId;
        setTitle("대화 상대");
        setSize(370, 480);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                OpenedViewList.getInstance().getOpenedContactView().remove(roomId);
                view.setEnabled(true);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.setBackground(white);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(white);
        northPanel.add(new JPanel(), BorderLayout.NORTH);
        northPanel.add(new JPanel(), BorderLayout.SOUTH);
        northPanel.add(new JPanel(), BorderLayout.WEST);
        northPanel.add(new JPanel(), BorderLayout.EAST);
        container.add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());
        container.add(southPanel, BorderLayout.SOUTH);

        JPanel northCenterPanel = new JPanel();
        northCenterPanel.setLayout(new BorderLayout());
        northCenterPanel.setBackground(navy);
        northPanel.add(northCenterPanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("대화 상대");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(white);
        northCenterPanel.add(titleLabel, BorderLayout.WEST);

        if(DataProvider.getInstance().getChatRoom(roomId).getRoomType().equals("public")) {
            JButton inviteMemberButton = new JButton("친구 초대");
            inviteMemberButton.setFont(font);
            inviteMemberButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new InviteMemberView((ContactView) inviteMemberButton.getTopLevelAncestor());
                    setEnabled(false);
                }
            });
            northCenterPanel.add(inviteMemberButton, BorderLayout.EAST);
        }

        contactListPanel = new JPanel();
        contactListPanel.setLayout(new GridBagLayout());
        contactListPanel.setBackground(white);

        JScrollPane chatRoomListPanelScroll = new JScrollPane(contactListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        container.add(chatRoomListPanelScroll, BorderLayout.CENTER);

        int i = 0;
        ArrayList<String[]> chatRoomMemberData = DataProvider.getInstance().getChatRoomMemberData(roomId);
        for (; i < chatRoomMemberData.size(); i++) {
            friendId = chatRoomMemberData.get(i)[0];
            JPanel contactInfoTabPanel = new JPanel();
            contactInfoTabPanel.setLayout(new BorderLayout());
            contactInfoTabPanel.add(new JPanel(), BorderLayout.NORTH);
            contactInfoTabPanel.add(new JPanel(), BorderLayout.SOUTH);
            contactInfoTabPanel.add(new JPanel(), BorderLayout.EAST);
            contactInfoTabPanel.add(new JPanel(), BorderLayout.WEST);
            //contactInfoTabPanel.setPreferredSize(new Dimension(getWidth() - 25, 70));
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0;
            contactListPanel.add(contactInfoTabPanel, gbc);

            JPanel contactInfoPanel = new JPanel();
            contactInfoPanel.setLayout(new GridBagLayout());
            contactInfoTabPanel.add(contactInfoPanel, BorderLayout.CENTER);

            JLabel contactNameLabel = new JLabel(chatRoomMemberData.get(i)[1]);
            contactNameLabel.setFont(boldFont);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.CENTER;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0.0;
            gbc.weighty = 1.0;
            contactInfoPanel.add(contactNameLabel, gbc);

            JPanel trashPanel = new JPanel();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            contactInfoPanel.add(trashPanel, gbc);

            JButton addFriendButton = new JButton("추가");
            addFriendButton.setFont(titleFont);
            addFriendButton.setBackground(white);
            addFriendButton.setForeground(navy);
            addFriendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean result;
                    synchronized (ThreadLock.lock) {
                        result = AddFriendService.getInstance().addFriend(addFriendButton, friendId);
                        DataProvider.getInstance().loadMemberData();
                    }
                    if (!result) {
                        return;
                    }
                }
            });
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.CENTER;
            gbc.weightx = 0;
            contactInfoPanel.add(addFriendButton, gbc);
            if (DataProvider.getInstance().containsMemberFromFriend(chatRoomMemberData.get(i)[0]) || LoginAccount.getInstance().getMyInfo().getUserId().equals(chatRoomMemberData.get(i)[0])) {
                addFriendButton.setEnabled(false);
            }
        }

        JPanel trashPanel1 = new JPanel();
        trashPanel1.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.weightx = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        contactListPanel.add(trashPanel1, gbc);

        setVisible(true);
    }

    @Override
    public void repaint() {
        super.repaint();
        contactListPanel.removeAll();
        int i = 0;
        ArrayList<String[]> chatRoomMemberData = DataProvider.getInstance().getChatRoomMemberData(roomId);
        for (; i < chatRoomMemberData.size(); i++) {
            friendId = chatRoomMemberData.get(i)[0];
            JPanel contactInfoTabPanel = new JPanel();
            contactInfoTabPanel.setLayout(new BorderLayout());
            contactInfoTabPanel.add(new JPanel(), BorderLayout.NORTH);
            contactInfoTabPanel.add(new JPanel(), BorderLayout.SOUTH);
            contactInfoTabPanel.add(new JPanel(), BorderLayout.EAST);
            contactInfoTabPanel.add(new JPanel(), BorderLayout.WEST);
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0;
            contactListPanel.add(contactInfoTabPanel, gbc);

            JPanel contactInfoPanel = new JPanel();
            contactInfoPanel.setLayout(new GridBagLayout());
            contactInfoPanel.setBackground(white);
            contactInfoTabPanel.add(contactInfoPanel, BorderLayout.CENTER);

            JLabel contactNameLabel = new JLabel(chatRoomMemberData.get(i)[1]);
            contactNameLabel.setFont(boldFont);
            contactNameLabel.setForeground(Color.black);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.CENTER;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0.0;
            gbc.weighty = 1.0;
            contactInfoPanel.add(contactNameLabel, gbc);

            JPanel trashPanel = new JPanel();
            trashPanel.setBackground(white);
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            contactInfoPanel.add(trashPanel, gbc);

            JButton addFriendButton = new JButton("추가");
            addFriendButton.setFont(boldFont);
            addFriendButton.setBackground(white);
            addFriendButton.setForeground(Color.black);
            addFriendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean result;
                    synchronized (ThreadLock.lock) {
                        result = AddFriendService.getInstance().addFriend(addFriendButton, friendId);
                        DataProvider.getInstance().loadMemberData();
                    }
                    if (!result) {
                        return;
                    }
                }
            });
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.CENTER;
            gbc.weightx = 0;
            contactInfoPanel.add(addFriendButton, gbc);
            if (DataProvider.getInstance().containsMemberFromFriend(chatRoomMemberData.get(i)[0]) || LoginAccount.getInstance().getMyInfo().getUserId().equals(chatRoomMemberData.get(i)[0])) {
                addFriendButton.setEnabled(false);
            }
        }

        JPanel trashPanel1 = new JPanel();
        trashPanel1.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.weightx = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        contactListPanel.add(trashPanel1, gbc);
    }

    public ChatRoomView getView() {
        return view;
    }
}