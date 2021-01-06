package models;

import java.sql.Timestamp;

public class Friend {

    private Long id;
    private User main;
    private User secondary;
    private Timestamp addedTime;

    public Friend(Long id, User main, User secondary, Timestamp addedTime) {
        this.id = id;
        this.main = main;
        this.secondary = secondary;
        this.addedTime = addedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getMain() {
        return main;
    }

    public void setMain(User main) {
        this.main = main;
    }

    public User getSecondary() {
        return secondary;
    }

    public void setSecondary(User secondary) {
        this.secondary = secondary;
    }

    public Timestamp getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Timestamp addedTime) {
        this.addedTime = addedTime;
    }
}
