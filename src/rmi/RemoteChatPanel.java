package rmi;

import shared.ChatMessage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RemoteChatPanel extends JPanel implements IRemoteChatPanel {
    public RemoteChatPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void updateChatPanel(List<ChatMessage> chatMessages) {
        SwingUtilities.invokeLater(() -> {
            Component headingAndTextInput = getComponent(0);
            removeAll();
            add(headingAndTextInput);

            JList<String> chatHistoryList = new JList<>(chatMessages.stream()
                    .map(message -> message.username() + ": " + message.content()).toArray(String[]::new));
            JScrollPane chatScrollPane = new JScrollPane(chatHistoryList);
            add(chatScrollPane);
            revalidate();
            repaint();
        });
    }
}
