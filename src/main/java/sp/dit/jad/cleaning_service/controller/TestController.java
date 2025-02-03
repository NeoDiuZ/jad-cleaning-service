package sp.dit.jad.cleaning_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String testJsp(Model model) {
        model.addAttribute("message", "JSP is working!");
        return "test"; // This will look for /WEB-INF/jsp/test.jsp
    }
}