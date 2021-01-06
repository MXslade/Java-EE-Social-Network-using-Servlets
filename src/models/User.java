package models;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;

public class User {

    private Long id;
    private String email;
    private String password;
    private String fullName;
    private Date birthdate;
    private String pictureUrl;

    public User(Long id, String email, String password, String fullName, Date birthdate, String pictureUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.pictureUrl = pictureUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getAge() {
        LocalDate now = LocalDate.now();
        Duration diff = Duration.between(birthdate.toLocalDate().atStartOfDay(), now.atStartOfDay());
        return (int) diff.toDays() / 365;
    }
}
