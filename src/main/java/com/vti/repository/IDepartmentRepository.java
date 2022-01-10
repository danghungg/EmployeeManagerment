package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vti.entity.Department;

public interface IDepartmentRepository
		extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
	Department findByName(String name);

	boolean existsByName(String name);

	//boolean existsById(int id);

//	@Modifying
//	@Transactional
//	@Query("DELETE FROM Department WHERE id IN(:ids)")
//	public void deleteDepartmentByIds(@Param("ids") List<Integer> ids);

}
