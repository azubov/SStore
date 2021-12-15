package ru.lanit.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lanit.model.entity.Category;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String categoryName;
    private String uploadedImageName;
    private String parentName;

    public void populateWith(Category categoryFromDb) {
        this.id = categoryFromDb.getId();
        if (this.categoryName == null) {
            this.categoryName = categoryFromDb.getName();
        }
        if (this.uploadedImageName == null) {
            this.uploadedImageName = categoryFromDb.getImageUrl();
        }
        if (this.parentName == null) {
            this.parentName = categoryFromDb.getParentCategoryName();
        }
    }
}
