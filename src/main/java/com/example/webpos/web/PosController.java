package com.example.webpos.web;

import javax.servlet.http.HttpSession;

import com.example.webpos.biz.PosService;
import com.example.webpos.model.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PosController {

    @Autowired
    private HttpSession session;

    private PosService posService;

    private Cart getCart() {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            this.saveCart(cart);
            ;
        }
        return cart;
    }

    private void saveCart(Cart cart) {
        session.setAttribute("cart", cart);
    }

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model) {
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "index";
    }

    @GetMapping("/add")
    public String addByGet(@RequestParam(name = "pid") String pid, Model model) {
        saveCart(posService.add(getCart(), pid, 1));
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "index";
    }
}
