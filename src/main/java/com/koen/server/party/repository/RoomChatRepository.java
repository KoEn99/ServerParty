package com.koen.server.party.repository;

import com.koen.server.party.entity.AuthPerson;
import com.koen.server.party.entity.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoomChatRepository extends JpaRepository<RoomChat, Long> {
  //  RoomChat findByEmailFromAndEmailTo(String emailFrom, String emailTo);

  //  List<RoomChat> findAllByEmailFromAndEmailTo(String emailFrom, String emailTo);

    List<RoomChat> findAllByEmailFrom(AuthPerson emailFrom);

}
