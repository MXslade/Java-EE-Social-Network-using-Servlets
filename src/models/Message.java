package models;

import java.sql.Timestamp;

public class Message {

    private Long id;
    private Chat chat;
    private User receiver;
    private User sender;
    private String text;
    private boolean readByReceiver;
    private Timestamp sentDate;

    public Message(Long id, Chat chat, User receiver, User sender, String text, boolean readByReceiver, Timestamp sentDate) {
        this.id = id;
        this.chat = chat;
        this.receiver = receiver;
        this.sender = sender;
        this.text = text;
        this.readByReceiver = readByReceiver;
        this.sentDate = sentDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isReadByReceiver() {
        return readByReceiver;
    }

    public void setReadByReceiver(boolean readByReceiver) {
        this.readByReceiver = readByReceiver;
    }

    public Timestamp getSentDate() {
        return sentDate;
    }

    public void setSentDate(Timestamp sentDate) {
        this.sentDate = sentDate;
    }
}
