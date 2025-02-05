package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
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
    private int koalaIdCounter = 1;

    @Autowired
    public KoalaController(){
        this.koalas =new HashMap<>();
    }

    @PostConstruct
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

    @PostMapping
    public Koala addKoala(@RequestBody Koala koala) {
        koala.setId(koalaIdCounter++);
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala updateKoala(@PathVariable("id") int id, @RequestBody Koala koala) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        koala.setId(id);
        koalas.put(id, koala);
        return koala;
    }

    @DeleteMapping("/{id}")
    public String deleteKoala(@PathVariable("id") int id) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        koalas.remove(id);
        return "Koala ID " + id + " silindi.";
    }
}
