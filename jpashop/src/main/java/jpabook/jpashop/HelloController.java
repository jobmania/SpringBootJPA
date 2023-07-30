package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {


    @GetMapping("")
    public String home(){
        return "redirect:/hello";
    }


    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("data", "helloo!!");
        return "hello";
    }
}
