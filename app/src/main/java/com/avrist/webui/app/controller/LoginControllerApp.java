package com.avrist.webui.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginControllerApp extends AppBaseController {

    @GetMapping("")
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("baseUrl", getBaseUrl(request));
        return "/page/login/index.html";
    }

}
