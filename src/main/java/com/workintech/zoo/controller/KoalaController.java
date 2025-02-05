package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/koalas")
public class KoalaController {
    public Map<Integer, Koala> koalas;

    @Autowired
    public KoalaController(){
        this.koalas =new HashMap<>();
    }

    public void init(){
        koalas = new HashMap<>();
    }

    @GetMapping
    public List <Koala> getAllKoalas(){
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala getKoalaById(@PathVariable("id") int id) {
        Koala koala = koalas.get(id);
        if (koala == null) {
            throw new ZooException("Koala with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        return koala;
    }

    @PostMapping ("")
    public Koala addKoala(Koala koala){
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping ("/{id}")
    public Koala updateKoala(@PathVariable("id") int id, Koala koala){
        return koalas.put(id, koala);
    }

    @DeleteMapping ("/{id}")
    public void deleteKoala(@PathVariable("id") int id){
        koalas.remove(id);
    }
}
