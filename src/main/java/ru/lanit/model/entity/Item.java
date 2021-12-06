package ru.lanit.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Item extends BaseEntity {
    @NonNull
    private String name;
    private String partNumber;
    private Double price;
    @ManyToOne
    private Category category;
    private String color;
    private String imageUrl;
}
