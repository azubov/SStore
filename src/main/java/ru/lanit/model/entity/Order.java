package ru.lanit.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order extends BaseEntity {
    @OneToMany
    private List<Item> items;
    @OneToOne
    private User user;
}
