package com.example.demo.dto.event;

import com.example.demo.dto.comment.CommentDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.validator.ImageFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateEventDTO {
    @ImageFile
    @NotNull(message = "Выберите фото")
    private String image;
    @NotBlank(message = "Введите заголовок")
    private String title;
    @NotBlank(message = "Введите описание")
    private String description;

    //region Getter and Setter


    public CreateEventDTO(String image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //endregion
}
