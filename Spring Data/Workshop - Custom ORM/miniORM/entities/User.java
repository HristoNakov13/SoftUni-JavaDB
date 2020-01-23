package entities;

import annotations.Column;
import annotations.Entity;
import annotations.PrimaryKey;

import java.sql.Date;

@Entity(name = "users")
public class User {
    @PrimaryKey
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_date")
    private Date registrationDate;

    public User() {
    }

    public User(String username, int age, Date registrationDate) {
        this.username = username;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Username: %s | Age: %s | Registration date: %s",
                this.getId(),
                this.getUsername(),
                this.getAge(),
                this.getRegistrationDate().toString());
    }
}
