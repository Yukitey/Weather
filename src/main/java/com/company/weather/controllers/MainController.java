package com.company.weather.controllers;

import com.company.weather.data.GetWeather;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) throws IOException {
        model.addAttribute("title", "Сведения о погоде");
        model.addAttribute("weather", GetWeather.getCurrentWeather());
        return "home";
    }

}
