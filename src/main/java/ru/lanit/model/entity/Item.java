package ru.lanit.model.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item_table")
public class Item extends BaseEntity {
    @NonNull
    private String name;
    private String partNumber;
    private Double price;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;
    private String color;
    private String imageUrl;
}
