package client.listener;

import client.frame.ContactView;
import client.runnable.ThreadLock;
import client.service.InviteMemberService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class InviteMemberButtonListener implements ActionListener {

    private final int roomId;
    private final HashMap<String, JCheckBox> checkBoxList;
    private final ContactView contactView;

    public InviteMemberButtonListener(int roomId, HashMap<String, JCheckBox> checkBoxList, ContactView contactView) {
        this.checkBoxList = checkBoxList;
        this.roomId = roomId;
        this.contactView = contactView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton inviteMemberButton = (JButton) e.getSource();
        ArrayList<String> inviteMember = new ArrayList<>();
        boolean result;
        for (String friendId : checkBoxList.keySet()) {
            if (checkBoxList.get(friendId).isSelected()) {
                inviteMember.add(friendId);
            }
        }
        synchronized (ThreadLock.lock) {
            result = InviteMemberService.getInstance().inviteMember(roomId, inviteMember);
        }
        if (!result) {
            return;
        }
        inviteMemberButton.getTopLevelAncestor().setVisible(false);
        contactView.setVisible(false);
        contactView.getView().setEnabled(true);
        contactView.getView().requestFocus();
    }
}
