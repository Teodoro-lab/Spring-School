package com.teos.school.school_management.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teos.school.school_management.Model.Alumno;
import com.teos.school.school_management.Model.Profesor;
import com.teos.school.school_management.Repository.ProfesorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProfesorService {

    private ProfesorRepository profesorRepository;

    public List<Profesor> findAll() {
        Iterable<Profesor> alumnosIterable = profesorRepository.findAll();
        return StreamSupport.stream(alumnosIterable.spliterator(), false)
                            .collect(Collectors.toList());
    }

    public Optional<Profesor> findById(Integer id) {
        return profesorRepository.findById(id);
    }

    public Profesor save(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    public void deleteById(Integer id) {
        profesorRepository.deleteById(id);
    }
}
