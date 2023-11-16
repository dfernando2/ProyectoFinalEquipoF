package com.Egg.Inmobiliaria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class InmobiliariaControlador {

    @GetMapping("/")
    public String navbar() {
        return "index.html";//home.html
    }
}
