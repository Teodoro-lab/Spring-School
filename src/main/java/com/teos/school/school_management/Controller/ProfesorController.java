package com.teos.school.school_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.teos.school.school_management.Model.Profesor;
import com.teos.school.school_management.Service.ProfesorService;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    @Autowired  
    private ProfesorService profesorService;

    @GetMapping
    public Iterable<Profesor> getAllProfesores() {
        return profesorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable Integer id) {
        Optional<Profesor> prof = profesorService.findById(id);

        if (prof.isPresent()) {
            return new ResponseEntity<>(prof.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Profesor> createProfesor(@Valid @RequestBody Profesor profesor) {
        Profesor savedProfesor = profesorService.save(profesor);
        return new ResponseEntity<>(savedProfesor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> updateProfesor(@PathVariable Integer id, @Valid @RequestBody Profesor profesor) {
        Optional<Profesor> profesorFromDb = profesorService.findById(id);

        if (profesorFromDb.isPresent()) {
            Profesor existingProfesor = profesorFromDb.get();
            existingProfesor.setNumeroEmpleado(profesor.getNumeroEmpleado());
            existingProfesor.setNombres(profesor.getNombres());
            existingProfesor.setApellidos(profesor.getApellidos());
            existingProfesor.setHorasClase(profesor.getHorasClase());

            Profesor updatedProfesor = profesorService.save(existingProfesor);
            return new ResponseEntity<>(updatedProfesor, HttpStatus.OK);
        }  

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfesor(@PathVariable Integer id) {
        if (profesorService.existsById(id)) {
            profesorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

