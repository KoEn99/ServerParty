package com.koen.server.party.repository;

import com.koen.server.party.entity.Dialog;
import com.koen.server.party.entity.MessageChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageChatRepository extends JpaRepository<MessageChat, Long> {
    List<MessageChat> findAllByDid(String did);
    List<MessageChat> findAllByDidAndReadMessage(String did, boolean readMessage);
}
