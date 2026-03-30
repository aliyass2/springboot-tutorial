package org.scopesky.jdktutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JdktutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdktutorialApplication.class, args);
    }

   @GetMapping (value = "/hello")
    public String hello(){ return "Hello!"; }
    @GetMapping (value = "/helloworld")
    public String helloWorld(){ return "Hello World!"; }
}
