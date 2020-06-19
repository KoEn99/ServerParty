package com.koen.server.party.service;

import com.koen.server.party.entity.MessageChat;
import com.koen.server.party.repository.MessageChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageChatService implements ServiceController {
    @Autowired
    MessageChatRepository messageChatRepository;
    @Override
    public void save(Object object) {
        MessageChat messageChat = (MessageChat)object;
        messageChatRepository.save(messageChat);
    }

    @Override
    public void remove(Object object) {

    }
}
