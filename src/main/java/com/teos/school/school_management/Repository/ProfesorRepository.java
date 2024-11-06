package com.teos.school.school_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.teos.school.school_management.Model.Profesor;

public interface ProfesorRepository extends PagingAndSortingRepository<Profesor, Integer>, CrudRepository<Profesor, Integer>  {
}
