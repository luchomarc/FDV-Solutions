package com.luchomarc.codechallenge;

/**
 * Created by Laptop on 27-04-2017.
 */

public class User {

    private String username;
    private String name;
    private String email;
    private String thumbnailUrl;
    private String largeImageUrl;

    public User(String username, String name, String email, String thumbnailUrl, String largeImageUrl) {
        this.username = username;
        this.name = name;
        this.email  = email;
        this.thumbnailUrl = thumbnailUrl;
        this.largeImageUrl = largeImageUrl;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }
}
