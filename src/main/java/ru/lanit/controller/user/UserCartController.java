package ru.lanit.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.model.entity.Cart;
import ru.lanit.model.entity.Item;
import ru.lanit.model.entity.Order;
import ru.lanit.model.entity.User;
import ru.lanit.service.cart.CartService;
import ru.lanit.service.item.ItemService;
import ru.lanit.service.order.OrderService;
import ru.lanit.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/cart")
public class UserCartController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public UserCartController(OrderService orderService, ItemService itemService, UserService userService, CartService cartService) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    public String showAll(Model model) {

        User currentUser = userService.getCurrentUser();
        Cart cart = cartService.getCart(currentUser);
        Map<Item, Integer> itemsMapFromCart = cartService.getItemsMap(cart);

        model.addAttribute("cart", itemsMapFromCart);
        return "user/userCartList";
    }

    @PostMapping("/add/{id}")
    public String add(@PathVariable("id") Long id, int quantity, HttpServletRequest request) {

        User currentUser = userService.getCurrentUser();
        Cart cart = cartService.getCart(currentUser);
        Item item = itemService.findById(id);

        cartService.add(cart, item, quantity);
        cartService.save(cart);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/order")
    public String makeOrder(Model model) {

        User currentUser = userService.getCurrentUser();
        Cart cart = cartService.getCart(currentUser);
        Order order = orderService.makeOrderFrom(cart, currentUser);

        orderService.save(order);

        cartService.clear(cart);
        cartService.save(cart);

        return "redirect:/user/cart/orders";
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {

        User currentUser = userService.getCurrentUser();
        List<Order> ordersFromUser = orderService.findAllByUser(currentUser);

        model.addAttribute("orders", ordersFromUser);

        return "orderList";
    }
}
