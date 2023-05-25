package rmi;

import shared.ChatMessage;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

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

            List<String> chatHistory = chatMessages.stream()
                    .map(message -> message.getUsername() + ": " + message.getContent())
                    .collect(Collectors.toList());

            JList<String> chatHistoryList = new JList<>(chatHistory.toArray(new String[0]));
            JScrollPane chatScrollPane = new JScrollPane(chatHistoryList);
            add(chatScrollPane);
            revalidate();
            repaint();
        });
    }
}
