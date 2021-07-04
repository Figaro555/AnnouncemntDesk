package com.announcementdesk.domain;


import javax.persistence.*;

@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String topic;
    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Announcement(){

    }

    public Announcement(String topic, String text, String tag, User author) {
        this.topic = topic;
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthorName(){
        return author.getName();
    }
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
