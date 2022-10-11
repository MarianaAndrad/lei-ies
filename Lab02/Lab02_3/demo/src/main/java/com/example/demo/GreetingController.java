package com.example.demo;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}*/

@RestController
public class GreetingController {
    private static final String template = "Welcome, %s";
    private static final String templateMessage = "Message: %s" ;
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name,
            @RequestParam(value = "message", required = false, defaultValue = "") String message
    ) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name),String.format(templateMessage, message));
    }

}