package com.tilepay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @RequestMapping(value = {"", "/", "*"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("class", "active");
        return "projects";
    }

}
