package Content.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AudioController {

    @GetMapping("/audio/catalog")
    public String homeTheaterPage(){
        return "/audio/catalog";
    }

}
