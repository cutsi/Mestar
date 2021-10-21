package com.example.demo.Repos;

import com.example.demo.Models.Message;
import com.example.demo.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findById(Long id);
    Set<Message> findAllByUser(AppUser user);
    Set<Message> findAllByIsReadIsTrueAndUser(AppUser user);
    Set<Message> findAllByIsReadIsFalseAndUser(AppUser user);
    Set<Message> findAllByIsReadIsFalseAndUserAndIsReceivedIsTrue(AppUser user);
    Set<Message> findAllByIsReceivedIsTrueAndUser(AppUser user);
    Set<Message> findAllByIsSentIsTrueAndUser(AppUser user);
    Set<Message> findAllByIsReceivedIsFalseAndUser(AppUser user);
    Set<Message> findAllByIsSentIsFalseAndUser(AppUser user);
    Set<Message> findAllByReceiverId(Long id);
    //@Query("SELECT m FROM Message m WHERE m.receiverId = ?1")
    ArrayList<Message> findAllByReceiverIdAndUserOrderBySentAt(Long receiver, AppUser user);
    ArrayList<Message> findAllByUserIdAndReceiverId(Long receiver, Long sender);
}
