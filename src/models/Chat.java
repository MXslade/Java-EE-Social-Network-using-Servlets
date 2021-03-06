package models;

import java.sql.Timestamp;

public class Chat {

    private Long id;
    private User user;
    private User opponentUser;
    private Timestamp createdDate;
    private String latestMessageText;
    private Timestamp latestMessageTime;

    public Chat(long id, User user, User opponentUser, Timestamp createdDate, String latestMessageText, Timestamp latestMessageTime) {
        this.id = id;
        this.user = user;
        this.opponentUser = opponentUser;
        this.createdDate = createdDate;
        this.latestMessageText = latestMessageText;
        this.latestMessageTime = latestMessageTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOpponentUser() {
        return opponentUser;
    }

    public void setOpponentUser(User opponentUser) {
        this.opponentUser = opponentUser;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getLatestMessageText() {
        return latestMessageText;
    }

    public void setLatestMessageText(String latestMessageText) {
        this.latestMessageText = latestMessageText;
    }

    public Timestamp getLatestMessageTime() {
        return latestMessageTime;
    }

    public void setLatestMessageTime(Timestamp latestMessageTime) {
        this.latestMessageTime = latestMessageTime;
    }
}
