package com.koen.server.party.service;

import com.koen.server.party.dto.RoomChatDto;
import com.koen.server.party.entity.AuthPerson;
import com.koen.server.party.entity.Dialog;
import com.koen.server.party.entity.RoomChat;
import com.koen.server.party.repository.AuthPersonRepository;
import com.koen.server.party.repository.DialogRepo;
import com.koen.server.party.repository.RoomChatRepository;
import com.koen.server.party.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoomChatService implements ServiceController {
    public String did = null;
    @Autowired
    AuthPersonRepository authPersonRepository;
    @Autowired
    RoomChatRepository roomChatRepository;
    @Autowired
    DialogRepo dialogRepo;
    @Override
    public void save(Object object) {
        RoomChatDto roomChatDto = (RoomChatDto)object;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        AuthPerson authPerson = authPersonRepository.findByEmail(userDetails.getEmail());
        AuthPerson authPerson_to = authPersonRepository.findByEmail(roomChatDto.getEmail_to());
        RoomChat roomChat = new RoomChat();
        roomChat.setEmailFrom(authPerson);
        RoomChat roomChatTo = new RoomChat();
        roomChatTo.setEmailFrom(authPerson_to);
        List<RoomChat> roomChatListDid = roomChatRepository.findAllByEmailFrom(roomChat.getEmailFrom());
        boolean needCreate = true;
        for (int i = 0; i < roomChatListDid.size(); i++){
            RoomChat[] roomChatsBuffer = {};
            roomChatsBuffer = roomChatListDid.get(i).getDialog().getRoomChats().
                    toArray(new RoomChat[roomChatListDid.get(i).getDialog().getRoomChats().size()]);
            if (roomChatTo.getEmailFrom().getEmail().equals(roomChatsBuffer[0].getEmailFrom().getEmail())){
                needCreate = false;
                did = roomChatsBuffer[0].getDialog().getDid();
                break;
            }
            if (roomChatTo.getEmailFrom().getEmail().equals(roomChatsBuffer[1].getEmailFrom().getEmail())){
                needCreate = false;
                did = roomChatsBuffer[1].getDialog().getDid();
                break;
            }
        }
        if (needCreate == true) {
            String UUIDstr = java.util.UUID.randomUUID().toString();
            UUIDstr = UUIDstr.substring(0,8);
            Dialog dialog = new Dialog();
            dialog.setDid(UUIDstr);
            dialogRepo.save(dialog);
            roomChat.setDialog(dialog);
            roomChatRepository.save(roomChat);
            roomChatTo.setDialog(dialog);
            roomChatRepository.save(roomChatTo);
            did = UUIDstr;
        }
    }
    public String getDid(){
        return did;
    }
    @Override
    public void remove(Object object) {

    }
}
