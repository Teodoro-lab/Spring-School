package com.teos.school.school_management.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.teos.school.school_management.Model.Profesor;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    private ArrayList<Profesor> profesores = new ArrayList<>();

    @GetMapping
    public List<Profesor> getAllProfesores() {
        return profesores;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable Integer id) {
        Optional<Profesor> prof = profesores.stream()
            .filter(profesor -> profesor.getId().equals(id))
            .findFirst();

        if (prof.isPresent()) {
            return new ResponseEntity<>(prof.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Profesor> createProfesor(@Valid @RequestBody Profesor profesor) {
        profesores.add(profesor);
        return new ResponseEntity<>(profesor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> updateProfesor(@PathVariable Integer id, @Valid @RequestBody Profesor profesor) {
        Optional<Profesor> profesorFromDb = profesores.stream()
            .filter(prof -> prof.getId().equals(id))
            .findFirst();

        if (profesorFromDb.isPresent()) {
            Profesor existingProfesor = profesorFromDb.get();
            existingProfesor.setNumeroEmpleado(profesor.getNumeroEmpleado());
            existingProfesor.setNombres(profesor.getNombres());
            existingProfesor.setApellidos(profesor.getApellidos());
            existingProfesor.setHorasClase(profesor.getHorasClase());
            return new ResponseEntity<>(existingProfesor, HttpStatus.OK);
        }  

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfesor(@PathVariable Integer id) {
        if (profesores.removeIf(profesor -> profesor.getId().equals(id))){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

