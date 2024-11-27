package com.teos.school.school_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.teos.school.school_management.Model.Alumno;
import com.teos.school.school_management.Model.SesionesAlumnos;
import com.teos.school.school_management.Service.AlumnoService;
import com.teos.school.school_management.Service.SessionsDynamoService;
import com.teos.school.school_management.Service.UploadObject;

import jakarta.validation.Valid;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

        if (!alumnoFromDb.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Alumno existingAlumno = alumnoFromDb.get();
        existingAlumno.setNombres(alumno.getNombres());
        existingAlumno.setApellidos(alumno.getApellidos());
        existingAlumno.setMatricula(alumno.getMatricula());
        existingAlumno.setPromedio(alumno.getPromedio());
        Alumno updatedAlumno = alumnoService.save(existingAlumno);
        return new ResponseEntity<>(updatedAlumno, HttpStatus.OK);
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
    
        if (!alumnoFromDb.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Alumno existingAlumno = alumnoFromDb.get();
        String fileObjKeyName = "profile-pictures/" + id + "/" + file.getOriginalFilename();
        String fotoPerfilUrl = UploadObject.uploadProfilePicture(fileObjKeyName, file);

        if (fotoPerfilUrl != null) {
            existingAlumno.setFotoPerfilUrl(fotoPerfilUrl);
            Alumno updatedAlumno = alumnoService.save(existingAlumno);
            return new ResponseEntity<>(updatedAlumno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{id}/session/login")
    public ResponseEntity<?> login(@PathVariable Integer id, @RequestBody Map<String, String> credentials) {
        Optional<Alumno> alumnoFromDb = alumnoService.findById(id);
        String password = credentials.get("password");

        if (!alumnoFromDb.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Alumno existingAlumno = alumnoFromDb.get();

        if(!existingAlumno.isPasswordCorrect(password)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        SesionesAlumnos sesion = new SesionesAlumnos();
        
        sesion.setId(UUID.randomUUID().toString());
        sesion.setAlumnoId(id);
        sesion.setActive(true);
        sesion.setFecha(System.currentTimeMillis());
        sesion.setSessionString(UUID.randomUUID().toString());

        SessionsDynamoService.createRecord(sesion);
        return new ResponseEntity<>(sesion, HttpStatus.OK);
    }

    @PostMapping("{id}/session/logout")
    public ResponseEntity<?> logout(@PathVariable Integer id, @RequestBody Map<String, String> session) {
        String sessionId = session.get("sessionString");
        Optional<SesionesAlumnos> sessionRecord = SessionsDynamoService.getRecord(sessionId);

        if (!sessionRecord.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SesionesAlumnos existingSesion = sessionRecord.get();

        if (existingSesion.getAlumnoId() != id) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        existingSesion.setActive(false);
        SessionsDynamoService.updateRecord(existingSesion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{id}/session/verify")
    public ResponseEntity<?> verifySession(@PathVariable Integer id, @RequestBody Map<String, String> session) {
        String sessionId = session.get("sessionString");

        Optional<Alumno> alumnoFromDb = alumnoService.findById(id);
        if (!alumnoFromDb.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<SesionesAlumnos> sessionRecord = SessionsDynamoService.getRecord(sessionId);
        if (!sessionRecord.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SesionesAlumnos existingSesion = sessionRecord.get();
        if (existingSesion.getAlumnoId() != id) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // correct case! 
        if (existingSesion.getActive()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}