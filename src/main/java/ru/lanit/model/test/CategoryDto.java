package ru.lanit.model.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private String categoryName;
    private String uploadedImageName;
    private String parentName;

}
