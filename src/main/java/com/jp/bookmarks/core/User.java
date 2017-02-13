package com.jp.bookmarks.core;

import javax.persistence.*;

/**
 * Created by lavoiejp on 11/02/17.
 */
@Entity
@Table(name = "Users")
@NamedQueries({
        @NamedQuery (
                name = "com.jp.bookmarks.core.User.findAll",
                query = "select u from User u"),
        @NamedQuery (
                name = "com.jp.bookmarks.core.User.findByUsernamePassword",
                query = "select u from User u " +
                        "where u.username = :username " +
                        "and u.password = :password"
        )
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
