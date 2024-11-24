package com.teos.school.school_management.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.teos.school.school_management.Model.Profesor;


public interface ProfesorRepository extends PagingAndSortingRepository<Profesor, Integer>, CrudRepository<Profesor, Integer>  {
}
