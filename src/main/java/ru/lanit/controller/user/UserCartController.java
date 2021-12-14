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
import java.util.List;


@Controller
@RequestMapping("/user/cart")
public class UserCartController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final UserService userService;

    @Autowired
    public UserCartController(OrderService orderService, ItemService itemService, UserService userService) {
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
    public String add(@PathVariable("id") Long id, int quantity, HttpServletRequest request) {

        Item item = itemService.findById(id);
        Cart.add(item, quantity);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/order")
    public String makeOrder(Model model) {

        Order order = new Order();

        List<Item> itemsFromCart = Cart.getItemsFromCart();
        order.setItems(itemsFromCart);

        User currentUser = userService.getCurrentUser();
        order.setUser(currentUser);

        orderService.save(order);

        Cart.clear();

        return "redirect:/user/cart/orders";
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("orders", orderService.findAllByUser(currentUser));
        return "orderList";
    }
}
