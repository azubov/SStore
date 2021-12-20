package ru.lanit.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cart_table")
public class Cart extends BaseEntity {

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Item, Integer> items = new HashMap<>();
    @OneToOne
    private User user;

    public Cart(User user) {
        this.user = user;
    }

    public void add(Item item, Integer quantity) {
        if (quantity == 0) {
            items.remove(item);
        } else {
            items.put(item, quantity);
        }
    }

    public Map<Item, Integer> getAll() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public List<Item> getItemsFromCart() {
        List<Item> itemsFromCart = new ArrayList<>();

        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            for (int i = entry.getValue(); i > 0; i--) {
                itemsFromCart.add(entry.getKey());
            }
        }

        return itemsFromCart;
    }
}
