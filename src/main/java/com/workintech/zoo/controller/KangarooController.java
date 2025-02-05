package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KangarooController {
    public Map<Integer, Kangaroo> kangaroos;

    @Autowired
    public KangarooController(){
        this.kangaroos = new HashMap<>();
    }

    public void init(){
        kangaroos = new HashMap<>();
    }

    @GetMapping ("/kangaroos")
    public List<Kangaroo> getAllKangaroos(){
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/kangaroos/{id}")
    public Kangaroo getById (@PathVariable("id") int id) {
        Kangaroo kangaroo = kangaroos.get(id);
        if (kangaroo == null) {
            throw new ZooException("Kangaroo with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        return kangaroo;
    }

    @PostMapping("/kangaroos")
    public Kangaroo addKangaroo(@RequestBody Kangaroo kangaroo){
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping ("/kangaroos/{id}")
    public Kangaroo updateKangaroo(@PathVariable("id") int id, @RequestBody Kangaroo kangaroo){
        return kangaroos.put(id, kangaroo);
    }

    @DeleteMapping ("/developers/{id}")
    public void deleteKangaroo(@PathVariable("id") int id){
        kangaroos.remove(id);
    }
}
