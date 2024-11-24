package com.teos.school.school_management.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.teos.school.school_management.Model.Alumno;

public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Integer>, CrudRepository<Alumno, Integer>  {
}
