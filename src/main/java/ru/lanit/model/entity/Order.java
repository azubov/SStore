package ru.lanit.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_table")
public class Order extends BaseEntity {
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Item> items;
    @OneToOne
    private User user;
}
