package ru.lanit.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.dto.Cart;
import ru.lanit.model.entity.Item;
import ru.lanit.model.entity.Order;
import ru.lanit.model.entity.User;
import ru.lanit.service.item.ItemService;
import ru.lanit.service.order.OrderService;
import ru.lanit.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/user/cart")
public class CartController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final UserService userService;

    @Autowired
    public CartController(OrderService orderService, ItemService itemService, UserService userService) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.userService = userService;
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

        Order order = orderService.createOrderFromCart();
        User currentUser = userService.getCurrentUser();

        order.setUser(currentUser);

        orderService.save(order);

        Cart.clear();

        System.out.println("Try To Display Orders");

        List<Order> orders = orderService.findAllByUser(currentUser);
        for (Order o : orders) {
            System.out.println(o.toString());
        }

        System.out.println("Try To Display Items of Each Order");

        List<Item> items = orders.stream().flatMap(o -> o.getItems().stream()).toList();
        for (Item i : items) {
            System.out.println(i.getName());
        }

        model.addAttribute("orders", orderService.findAllByUser(currentUser));

        return "user/userOrderList";
    }
}
