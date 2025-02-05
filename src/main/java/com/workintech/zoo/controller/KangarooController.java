package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KangarooController {
    public Map<Integer, Kangaroo> kangaroos;
    private int idCounter = 1;

    @Autowired
    public KangarooController(){
        this.kangaroos = new HashMap<>();
    }

    @PostConstruct
    public void init(){
        kangaroos = new HashMap<>();
    }

    @GetMapping ("/kangaroos")
    public List<Kangaroo> find(){
        return this.kangaroos.values().stream().toList();
    }

    @GetMapping("/kangaroos/{id}")
    public Kangaroo find (@PathVariable("id") int id) {
        Kangaroo kangaroo = kangaroos.get(id);
        if (kangaroo == null) {
            throw new ZooException("Kangaroo with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        return kangaroo;
    }

    @PostMapping("/kangaroos")
           public ResponseEntity<Kangaroo> addKangaroo(@RequestBody Kangaroo kangaroo) {
            // Manuel Validation
            if (kangaroo.getName() == null || kangaroo.getName().isBlank()) {
                throw new ZooException("Name cannot be empty", HttpStatus.BAD_REQUEST);
            }
            if (kangaroo.getHeight() <= 0) {
                throw new ZooException("Height must be greater than 0", HttpStatus.BAD_REQUEST);
            }
            if (kangaroo.getWeight() <= 0) {
                throw new ZooException("Weight must be greater than 0", HttpStatus.BAD_REQUEST);
            }
            if (kangaroo.getGender() == null || kangaroo.getGender().isBlank()) {
                throw new ZooException("Gender cannot be empty", HttpStatus.BAD_REQUEST);
            }

            // ID Atama
            kangaroo.setId(idCounter++);
            kangaroos.put(kangaroo.getId(), kangaroo);
            return ResponseEntity.ok(kangaroo);
        }



    @PutMapping("/kangaroos/{id}")
    public Kangaroo updateKangaroo(@PathVariable("id") int id, @RequestBody Kangaroo kangaroo) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        kangaroo.setId(id); // ID değiştirilmemesi için
        kangaroos.put(id, kangaroo);
        return kangaroo;
    }

    @DeleteMapping("/kangaroos/{id}")
    public ResponseEntity<Kangaroo> deleteKangaroo(@PathVariable("id") int id) {
        Kangaroo removedKangaroo = kangaroos.get(id);

        if (removedKangaroo == null) {
            throw new ZooException("Kangaroo with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }

        kangaroos.remove(id);
        return ResponseEntity.ok(removedKangaroo);
    }

}
