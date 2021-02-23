package com.example.monitoring.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BaseController {

    @Autowired
    private RestTemplate restTemplate;

	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public String welcome(){
        LOG.info("api: welcome");
        return "Hello World";
    }

    /**
     * Simulate calling a microservice "Helper" to get fibonacci number
     * @param fib
     * @return
     */
    @GetMapping(path="/fib"+"/{fib}" ,produces=MediaType.APPLICATION_JSON_VALUE)
    public Integer fibonacci(@PathVariable int fib){
        LOG.info("api: fibonacci started");

        if(fib == 0){
            return 0;
        }
        if(fib ==1){
            return 1;
        }

        ResponseEntity<Integer> fib_1 = restTemplate.exchange("http://localhost:8081/api/fib/" + String.valueOf(fib-1), HttpMethod.GET,null , Integer.class);
        ResponseEntity<Integer> fib_2 = restTemplate.exchange("http://localhost:8081/api/fib/" + String.valueOf(fib-2), HttpMethod.GET,null , Integer.class);

        LOG.info("api: fibonacci ened");

        int result = fib_1.getBody() + fib_2.getBody();

        return result;
    }

}
