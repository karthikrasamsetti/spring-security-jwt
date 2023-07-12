package com.spring.security.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
@Slf4j
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> sayHello(){
        log.info("Demo-controller");
        return ResponseEntity.ok("Hello from Secured endPoint");
    }

}
