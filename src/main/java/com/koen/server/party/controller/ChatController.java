package com.koen.server.party.controller;

import com.koen.server.party.dto.*;
import com.koen.server.party.entity.AuthPerson;
import com.koen.server.party.entity.MessageChat;
import com.koen.server.party.entity.RoomChat;
import com.koen.server.party.repository.AuthPersonRepository;
import com.koen.server.party.repository.MessageChatRepository;
import com.koen.server.party.repository.RoomChatRepository;
import com.koen.server.party.security.jwt.JwtUser;
import com.koen.server.party.service.MessageChatService;
import com.koen.server.party.service.RoomChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatController {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    AuthPersonRepository authPersonRepository;
    @Autowired
    RoomChatService roomChatService;
    @Autowired
    MessageChatService messageChatService;
    @Autowired
    MessageChatRepository messageChatRepository;
    @Autowired
    RoomChatRepository roomChatRepository;
    @MessageMapping("/send-message")
    public void greeting(MessageChatDto messageChatDto) throws Exception {
        AuthPerson authPerson = authPersonRepository.findByEmail(messageChatDto.getMyEmail());
        AuthPerson authPerson_to = authPersonRepository.findByEmail(messageChatDto.getAuthPerson_to());
        MessageChat messageChat = new MessageChat();
        messageChat.setTime(messageChatDto.getTime());
        messageChat.setDid(messageChatDto.getDid());
        //messageChat.setAuthPerson_to(messageChatDto.getAuthPerson_to());
        messageChat.setMessage(messageChatDto.getMessage());
        messageChat.setAuthPerson_from(authPerson);
        messageChat.setImageMessage(messageChatDto.getImageMessage());
     //  RoomChat roomChat = roomChatRepository.findByEmailFromAndEmailTo(messageChat.getAuthPerson_from(),
      //          messageChat.getAuthPerson_to());
      //  if (roomChat != null) messageChat.setDid(roomChat.getDid());
        messageChatService.save(messageChat);
        messageChat.setFio(authPerson.getProfilePerson().getName() + " " +
                authPerson.getProfilePerson().getMiddleName());
        this.template.convertAndSend("/topic/"+messageChatDto.getAuthPerson_to(),messageChat);
    }
    @RequestMapping(value = "/chat/email", method = RequestMethod.GET)
    @ResponseBody
    public String getMyEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        return userDetails.getEmail();
    }

    @RequestMapping(value = "/chat/create-room", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity changeProfile(@RequestBody RoomChatDto roomChatDto){

        roomChatService.save(roomChatDto);
        String did =roomChatService.did;
        Diddto diddto = new Diddto();
        diddto.setDid(did);
        return ResponseEntity.ok(diddto);
    }
   @RequestMapping(value = "/chat/message", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getMessage(@RequestBody MessageChatPrivate messageChatPrivate){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        List<MessageChat> messageChatList = messageChatRepository.findAllByDid(messageChatPrivate.getDid());
        return ResponseEntity.ok(messageChatList);
    }
    @RequestMapping(value = "/chat/read-message", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity setMessageRead(@RequestBody MessageReadDto messageReadDto){
        List<MessageChat> messageChatList = messageChatRepository.findAllByDidAndReadMessage(
                messageReadDto.getDid(),
                false
        );
        for (int i = 0; i < messageChatList.size(); i++){
            messageChatList.get(i).setReadMessage(true);
            messageChatService.save(messageChatList.get(i));
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/chat/my", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getMyChats(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        AuthPerson authPerson = authPersonRepository.findByEmail(userDetails.getEmail());
        List<RoomChat> roomChatList = roomChatRepository.findAllByEmailFrom(authPerson);
       List<MyChatsDto> myChatsDtoList = new ArrayList<>();
        for (int i = 0; i < roomChatList.size(); i++){
         //   AuthPerson authPerson_chats = authPersonRepository.findByEmail(roomChatList.get(i).getEmailTo());
            RoomChat[] roomChatsBuffer = {};
            roomChatsBuffer = roomChatList.get(i).getDialog().getRoomChats().
                    toArray(new RoomChat[roomChatList.get(i).getDialog().getRoomChats().size()]);
            String FIO = "";
            String email_to = "";
            String avatar = "";
            for (int j = 0; j < 2; j++){
                if (!roomChatsBuffer[j].getEmailFrom().getEmail().equals(authPerson.getEmail())){
                    FIO = roomChatsBuffer[j].getEmailFrom().getProfilePerson().getName() + " " +
                            roomChatsBuffer[j].getEmailFrom()
                            .getProfilePerson().getMiddleName();
                    email_to = roomChatsBuffer[j].getEmailFrom().getEmail();
                    avatar = roomChatsBuffer[j].getEmailFrom().getProfilePerson().getAvatar();
                }
            }
                List<MessageChat> messageChatList = messageChatRepository.findAllByDid(roomChatList.get(i).getDialog().getDid());
                if (messageChatList.size() != 0)
                    myChatsDtoList.add(new MyChatsDto(FIO,
                            messageChatList.get(messageChatList.size() - 1).getMessage(),
                            messageChatList.get(messageChatList.size() - 1).getTime(),
                            messageChatList.get(messageChatList.size() - 1).isReadMessage(),
                            roomChatList.get(i).getDialog().getDid(),
                            messageChatList.get(messageChatList.size()-1).getAuthPerson_from().getEmail(),
                            email_to,
                            avatar
                            )
                    );
        }
        return ResponseEntity.ok(myChatsDtoList);
    }


}
