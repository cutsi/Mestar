package com.example.demo.Services;

import com.example.demo.Models.Message;
import com.example.demo.Repos.MessageRepository;
import com.example.demo.Models.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Getter
@Setter
public class MessageService {
    private final MessageRepository messageRepository;
    public Optional<Message> getMessageById(Long id){
        return messageRepository.findById(id);
    }
    public Set<Message> getAllMessagesFromUser(AppUser user){
        return messageRepository.findAllByUser(user);
    }
    public Set<Message> getAllReadMessagesFromUser(AppUser user){
        return messageRepository.findAllByIsReadIsTrueAndUser(user);
    }
    public Set<Message> getAllUnreadMessagesFromUser(AppUser user){
        return messageRepository.findAllByIsReadIsFalseAndUser(user);
    }
    public Set<Message> getAllMessagesWhereReceivedIsTrue(AppUser user){
        return messageRepository.findAllByIsReceivedIsTrueAndUser(user);
    }
    public Set<Message> getAllUnReceivedMessagesFromUser(AppUser user){
        return messageRepository.findAllByIsReceivedIsFalseAndUser(user);
    }
    public Set<Message> getAllUnreadReceivedMessagesFromUser(AppUser user){
        return messageRepository.findAllByIsReadIsFalseAndUserAndIsReceivedIsTrue(user);
    }
    public Set<Message> getAllSentMessagesFromUser(AppUser user){
        return messageRepository.findAllByIsSentIsTrueAndUser(user);
    }
    public Set<Message> getAllUnSentMessagesFromUser(AppUser user){
        return messageRepository.findAllByIsSentIsFalseAndUser(user);
    }
    public Set<Message> getAllReceivedMessagesFromUser(Long id){
        return messageRepository.findAllByReceiverId(id);
    }
    public ArrayList<Message> getConversationBetweenReceiverAndSender(AppUser receiver, AppUser sender){
        ArrayList<Message> conversation1 = messageRepository.findAllByReceiverIdAndUserOrderBySentAt(sender.getId(), receiver);
        ArrayList<Message> conversation2 = messageRepository.findAllByReceiverIdAndUserOrderBySentAt(receiver.getId(), sender);
        conversation1.addAll(conversation2);
        sort((conversation1));
        return conversation1;
    }
    public static void sort(ArrayList<Message> list)
    {
        list.sort(Comparator.comparing(Message::getSentAt));
    }
    public Set<Long> getAllReceiversFromCurrentUser(AppUser user){
        Set<Message> allMessages = messageRepository.findAllByUser(user);
        Set<Long> receiverIds = extractReceiverIdsFromMessageSets(allMessages);
        return receiverIds;
    }
    public Set<Long> getAllSendersFromCurrentUser(AppUser user){
        Set<Message> allMessages = messageRepository.findAllByReceiverId(user.getId());
        Set<Long> receiverIds = extractSenderIdsFromMessageSets(allMessages);
        return receiverIds;
    }
    public void SaveMessage(Message message){
        messageRepository.save(message);
    }
    private static Set<Long> extractReceiverIdsFromMessageSets(Set<Message> messages){
        Set<Long> ids = new HashSet<>();
        for (Message m:messages) {
            ids.add(m.getReceiverId());
        }
        return ids;
    }
    private static Set<Long> extractSenderIdsFromMessageSets(Set<Message> messages){
        Set<Long> ids = new HashSet<>();
        for (Message m:messages) {
            ids.add(m.getUser().getId());
        }
        return ids;
    }
    public static <T> Set<T> mergeSet(Set<T> a, Set<T> b)
    {
        return new HashSet<T>() {{
            addAll(a);
            addAll(b);
        } };
    }

}
