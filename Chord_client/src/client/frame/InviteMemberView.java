package client.frame;

import client.data.DataProvider;
import client.listener.InviteMemberButtonListener;
import client.model.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class InviteMemberView extends JFrame {

    private final HashMap<String, JCheckBox> friendCheckBoxList = new HashMap<>();

    public InviteMemberView(ContactView view) {
        setTitle("대화 상대 초대");
        setSize(370, 580);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                view.setEnabled(true);
            }
        });

        Color navy = new Color(59, 89, 182);; // 남색
        Color white = Color.WHITE;

        Font font = new Font("맑은 고딕", Font.PLAIN, 15);
        Font boldFont = new Font("맑은 고딕", Font.BOLD, 15);
        Font smallFont = new Font("맑은 고딕", Font.PLAIN, 12);
        Font miniFont = new Font("맑은 고딕", Font.PLAIN, 11);
        Font titleFont = new Font("맑은 고딕", Font.BOLD, 20);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(navy);
        northPanel.add(new JPanel(), BorderLayout.NORTH);
        northPanel.add(new JPanel(), BorderLayout.SOUTH);
        northPanel.add(new JPanel(), BorderLayout.WEST);
        northPanel.add(new JPanel(), BorderLayout.EAST);
        container.add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());
        southPanel.setBackground(navy);
        container.add(southPanel, BorderLayout.SOUTH);

        JPanel northCenterPanel = new JPanel();
        northCenterPanel.setLayout(new BorderLayout());
        northCenterPanel.setBackground(white);
        northPanel.add(northCenterPanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("대화 상대 초대");
        titleLabel.setFont(font);
        titleLabel.setForeground(white);
        northCenterPanel.add(titleLabel, BorderLayout.WEST);

        JButton inviteMemberButton = new JButton("초대");
        inviteMemberButton.setFont(font);
        inviteMemberButton.addActionListener(new InviteMemberButtonListener(view.roomId, friendCheckBoxList, view));
        northCenterPanel.add(inviteMemberButton, BorderLayout.EAST);

        JPanel friendListPanel = new JPanel();
        friendListPanel.setLayout(new GridBagLayout());
        friendListPanel.setBackground(white);

        JScrollPane chatRoomListPanelScroll = new JScrollPane(friendListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        container.add(chatRoomListPanelScroll, BorderLayout.CENTER);

        ArrayList<Member> memberData = DataProvider.getInstance().getMemberData();
        int i = 0;
        for (; i < memberData.size(); i++) {
            JPanel friendTabPanel = new JPanel();
            friendTabPanel.setLayout(new BorderLayout());
            friendTabPanel.add(new JPanel(), BorderLayout.NORTH);
            friendTabPanel.add(new JPanel(), BorderLayout.SOUTH);
            friendTabPanel.add(new JPanel(), BorderLayout.EAST);
            friendTabPanel.add(new JPanel(), BorderLayout.WEST);
            friendTabPanel.setPreferredSize(new Dimension(getWidth() - 25, 60));
            friendTabPanel.setBackground(white);
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.gridheight = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.weightx = 1.0;
            gbc.weighty = 0;
            friendListPanel.add(friendTabPanel, gbc);

            JPanel friendPanel = new JPanel();
            friendPanel.setLayout(new GridBagLayout());
            friendPanel.setBackground(white);
            friendTabPanel.add(friendPanel, BorderLayout.CENTER);

            JLabel friendNameLabel = new JLabel(memberData.get(i).getName());
            friendNameLabel.setFont(boldFont);
            friendNameLabel.setHorizontalAlignment(JLabel.LEFT);
            friendNameLabel.setForeground(navy);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.CENTER;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weighty = 1.0;
            friendPanel.add(friendNameLabel, gbc);

            JPanel trashPanel = new JPanel();
            trashPanel.setLayout(new GridBagLayout());
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.CENTER;
            friendPanel.add(trashPanel, gbc);

            JPanel friendCheckBoxPanel = new JPanel();
            friendCheckBoxPanel.setLayout(new BorderLayout());
            friendCheckBoxPanel.setBackground(white);
            friendCheckBoxPanel.add(new JPanel(), BorderLayout.NORTH);
            friendCheckBoxPanel.add(new JPanel(), BorderLayout.SOUTH);
            friendCheckBoxPanel.add(new JPanel(), BorderLayout.EAST);
            friendCheckBoxPanel.add(new JPanel(), BorderLayout.WEST);
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.CENTER;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            friendPanel.add(friendCheckBoxPanel, gbc);

            JCheckBox friendCheckBox = new JCheckBox();
            friendCheckBox.setBackground(white);
            friendCheckBoxList.put(memberData.get(i).getUserId(), friendCheckBox);
            friendCheckBoxPanel.add(friendCheckBox, BorderLayout.CENTER);
            if (DataProvider.getInstance().containsMemberFromChatRoom(memberData.get(i).getUserId(), view.roomId)) {
                friendCheckBox.setEnabled(false);
            }
        }

        JPanel trashPanel1 = new JPanel();
        trashPanel1.setBackground(white);
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        friendListPanel.add(trashPanel1, gbc);

        setVisible(true);
    }
}