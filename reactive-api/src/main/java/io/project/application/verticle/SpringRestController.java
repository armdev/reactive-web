package io.project.application.verticle;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secret/path")
@Slf4j
public class SpringRestController {


    @GetMapping(path = "/find", produces = "application/json;charset=UTF-8")
    public String checkMe() {
        return "This is strange, but I am Spring controller!!!";
    }

   

}
