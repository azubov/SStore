package ru.lanit.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.dto.Cart;
import ru.lanit.model.entity.Item;
import ru.lanit.model.entity.Order;
import ru.lanit.service.item.ItemService;
import ru.lanit.service.order.OrderService;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    private final OrderService orderService;
    private final ItemService itemService;

    @Autowired
    public CartController(OrderService orderService, ItemService itemService) {
        this.orderService = orderService;
        this.itemService = itemService;
    }

    @GetMapping
    public String showAll(Model model) {
        System.out.println(Cart.getAll().entrySet());
        model.addAttribute("cart", Cart.getAll());
        return "user/userCartList";
    }

    @PostMapping("/add/{id}")
    public String add(@PathVariable("id") Long id, int quantity, Model model) {
        Item item = itemService.findById(id);
        Cart.add(item, quantity);

//        model.addAttribute("id", id);
//        model.addAttribute("quantity", quantity);
//        model.addAttribute("cart", Cart.getAll());

        return "redirect:/user/shopping";
    }

    @GetMapping("/order")
    public String makeOrder(@ModelAttribute Item item, @ModelAttribute String quantity) {
        Integer.parseInt(quantity);
        Cart.getAll();
        Order order = new Order();
        orderService.save(order);
        Cart.clear();

        return "user/userItemList";
    }
}
