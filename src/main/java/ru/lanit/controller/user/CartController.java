package ru.lanit.controller.user;

import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.dto.Cart;
import ru.lanit.model.entity.Item;
import ru.lanit.model.entity.Order;
import ru.lanit.service.item.ItemService;
import ru.lanit.service.order.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
        model.addAttribute("cart", Cart.getAll());
        return "user/userCartList";
    }

    @PostMapping("/add/{id}")
    public String add(@PathVariable("id") Long id, int quantity, Model model, HttpServletRequest request) {
        Item item = itemService.findById(id);
        Cart.add(item, quantity);

        String referer = request.getHeader("Referer");

        if (referer.equals("http://localhost:8080/user/shopping")) {
            return "redirect:/user/shopping";
        } else {
            return "redirect:/user/cart/";
        }
    }

    @GetMapping("/order")
    public String makeOrder(Model model) {

        Order order = new Order();
        List<Item> itemsFromCart = new ArrayList<>();

        for (Map.Entry<Item, Integer> entry : Cart.getAll().entrySet()) {
            for (int i = entry.getValue(); i > 0; i--) {
                itemsFromCart.add(entry.getKey());
            }
        }

        itemsFromCart.forEach(System.out::println);
//        order.setItems(itemsFromCart);
//
//        orderService.save(order);
//        Cart.clear();
//
//        model.addAttribute("order", orderService.getAll());

        return "user/userOrderList";
    }
}
