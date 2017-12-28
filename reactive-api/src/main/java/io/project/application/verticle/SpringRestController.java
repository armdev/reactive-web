package io.project.application.verticle;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secret/path")
public class SpringRestController {

    private static final Logger log = LoggerFactory.getLogger(SpringRestController.class);

    @GetMapping(path = "/find", produces = "application/json;charset=UTF-8")
    public String checkMe() {
        return "This is strange, but I am Spring controller!!!";
    }

   

}
