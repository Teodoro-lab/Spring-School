package com.teos.school.school_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.teos.school.school_management.Model.Alumno;
import com.teos.school.school_management.Service.AlumnoService;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    
    private Integer currentId = 1;
    private ArrayList<Alumno> alumnos = new ArrayList<>();

    @GetMapping
    public List<Alumno> getAllAlumnos() {
        return alumnos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable Integer id) {
        Optional<Alumno> alum = alumnos.stream()
            .filter(alumno -> alumno.getId().equals(id))
            .findFirst();

        if (alum.isPresent()) {
            return new ResponseEntity<>(alum.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Alumno> createAlumno(@Valid @RequestBody Alumno alumno) {
        alumnos.add(alumno);
        return new ResponseEntity<>(alumno, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> updateAlumno(@PathVariable Integer id, @Valid @RequestBody Alumno alumno) {
        Optional<Alumno> alumnoFromDb = alumnos.stream()
            .filter(alum -> alum.getId().equals(id))
            .findFirst();

        if (alumnoFromDb.isPresent()) {
            Alumno existingAlumno = alumnoFromDb.get();
            existingAlumno.setNombres(alumno.getNombres());
            existingAlumno.setApellidos(alumno.getApellidos());
            existingAlumno.setMatricula(alumno.getMatricula());
            existingAlumno.setPromedio(alumno.getPromedio());
            return new ResponseEntity<>(existingAlumno, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlumno(@PathVariable Integer id) {
        if (alumnos.removeIf(alumno -> alumno.getId().equals(id))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}