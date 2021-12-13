package ru.lanit.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "order_table")
public class Order extends BaseEntity {
    @OneToMany(fetch = FetchType.EAGER)
    private List<Item> items;
    @OneToOne
    private User user;
}
