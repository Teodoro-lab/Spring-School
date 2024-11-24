package com.teos.school.school_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.teos.school.school_management.Model.Alumno;
import com.teos.school.school_management.Model.Profesor;
import com.teos.school.school_management.Service.AlumnoService;
import com.teos.school.school_management.Service.UploadObject;

import jakarta.validation.Valid;                

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;


    @GetMapping
    public Iterable<Alumno> getAllAlumnos() {
        return alumnoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable Integer id) {
        Optional<Alumno> alum = alumnoService.findById(id);

        if (alum.isPresent()) {
            return new ResponseEntity<>(alum.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Alumno> createAlumno(@Valid @RequestBody Alumno alumno) {
        Alumno savedAlumno = alumnoService.save(alumno);
        return new ResponseEntity<>(savedAlumno, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> updateAlumno(@PathVariable Integer id, @Valid @RequestBody Alumno alumno) {
        Optional<Alumno> alumnoFromDb = alumnoService.findById(id);

        if (alumnoFromDb.isPresent()) {
            Alumno existingAlumno = alumnoFromDb.get();
            existingAlumno.setNombres(alumno.getNombres());
            existingAlumno.setApellidos(alumno.getApellidos());
            existingAlumno.setMatricula(alumno.getMatricula());
            existingAlumno.setPromedio(alumno.getPromedio());
            Alumno updatedAlumno = alumnoService.save(existingAlumno);
            return new ResponseEntity<>(updatedAlumno, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlumno(@PathVariable Integer id) {
        if (alumnoService.existsById(id)){
            alumnoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/fotoPerfil")
    public ResponseEntity<Alumno> uploadFotoPerfil(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        Optional<Alumno> alumnoFromDb = alumnoService.findById(id);
    
        if (alumnoFromDb.isPresent()) {
            Alumno existingAlumno = alumnoFromDb.get();
            String fileObjKeyName = "profile-pictures/" + id + "/" + file.getOriginalFilename();
            String fotoPerfilUrl = UploadObject.uploadProfilePicture(fileObjKeyName, file);
            System.out.println("fotoPerfilUrl: ");
            System.out.println(fotoPerfilUrl);
            if (fotoPerfilUrl != null) {
                existingAlumno.setFotoPerfilUrl(fotoPerfilUrl);
                Alumno updatedAlumno = alumnoService.save(existingAlumno);
                return new ResponseEntity<>(updatedAlumno, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}