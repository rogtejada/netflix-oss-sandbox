package com.poc.eureka;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/teste")
    public ResponseEntity teste(){
        Music music = new Music();
        music.setId(1);
        music.setName("La Bamba");
        return new ResponseEntity(music, HttpStatus.OK);
    }
}
