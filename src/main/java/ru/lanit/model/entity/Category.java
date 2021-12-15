package ru.lanit.model.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.lanit.model.dto.CategoryDto;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category_table")
public class Category extends BaseEntity {
    private String name;
    private String imageUrl;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category parentCategory;

    public String getParentCategoryName() {
        return parentCategory != null ? parentCategory.getName() : "-";
    }

    public void populateWith(CategoryDto categoryDto, Category parentCategory) {
        this.name = categoryDto.getCategoryName();
        this.imageUrl = categoryDto.getUploadedImageName();
        this.parentCategory = parentCategory;
    }
}
