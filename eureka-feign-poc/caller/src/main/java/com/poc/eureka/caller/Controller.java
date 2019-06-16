package com.poc.eureka.caller;

import com.poc.eureka.caller.discovery.EurekaHttpMethods;
import com.poc.eureka.caller.discovery.EurekaOperations;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private EurekaOperations eurekaOperations = new EurekaOperations();

    @RequestMapping("/get")
    public ResponseEntity getHost(){
        return new ResponseEntity(eurekaOperations.getHost("teste"), HttpStatus.OK);
    }

    @RequestMapping("/music")
    public ResponseEntity getMusic(){
        EurekaHttpMethods eurekaGet = Feign.builder().decoder(new GsonDecoder()).target(EurekaHttpMethods.class, "http://"+ eurekaOperations.getHost("teste"));
        Music music = eurekaGet.getMusic();
        return new ResponseEntity(music, HttpStatus.OK);
    }

}
