package net.ivan.kavaliou.moneyman.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
@Slf4j
@Controller
public class RootController {
    private static final String INDEX_VIEW = "index";

    @Autowired(required=true)
    CurrencyService currencyService;


    // inject via application.properties
    @Value("${welcome.message}")
    private String welcomeMessage;

    @GetMapping("/")
    public String root(Model model) {
        log.info("Simple log info ");
        model.addAttribute("message", welcomeMessage);
        model.addAttribute("currencyList", currencyService.getAllCurrency());
        return INDEX_VIEW; //view
    }

    // /hello?name=kotlin
    @GetMapping("/hello")
    public String mainWithParam(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        model.addAttribute("name", name);
        return INDEX_VIEW; //view
    }
}
