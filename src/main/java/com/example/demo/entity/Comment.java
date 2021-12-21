package com.example.demo.entity;

import com.example.demo.entity.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Выберите фото")
    private String image;
    @NotBlank(message = "Введите комментарий")
    private String comment;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "Введите id пользователя")
    private User user;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @NotNull(message = "Введите id события")
    private Event event;

    //region Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    //endregion
}
