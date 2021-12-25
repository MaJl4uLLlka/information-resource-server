package com.example.demo.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateCommentDTO {
    @NotNull(message = "Выберите фото")
    private String image;
    @NotBlank(message = "Введите комментарий")
    private String comment;
    @NotNull(message = "Введите id пользователя")
    private Long userId;

    //region Getter and Setter

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    //endregion
}
