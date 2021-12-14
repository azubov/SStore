package ru.lanit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lanit.service.order.OrderService;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {

    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String getOrder(Model model) {

        model.addAttribute("orders", orderService.findAll());

        return "user/userOrderList";
    }

}
