package ru.lanit.model.entity;

import lombok.*;

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
    private Category parentCategory;

    public String getParentCategoryName() {
        return parentCategory != null ? parentCategory.getName() : "-";
    }
}
