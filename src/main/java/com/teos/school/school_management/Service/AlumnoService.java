package com.teos.school.school_management.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teos.school.school_management.Model.Alumno;
import com.teos.school.school_management.Repository.AlumnoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> findAll() {
        Iterable<Alumno> alumnosIterable = alumnoRepository.findAll();
        return StreamSupport.stream(alumnosIterable.spliterator(), false)
                            .collect(Collectors.toList());
    }   

    public Optional<Alumno> findById(Integer id) {
        return alumnoRepository.findById(id);
    }

    public Alumno save(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    public void deleteById(Integer id) {
        alumnoRepository.deleteById(id);
    }

    public Boolean existsById(Integer id) {
        return alumnoRepository.existsById(id);
    }
}
