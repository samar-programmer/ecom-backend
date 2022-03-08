package com.revature.project.amazon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project.amazon.model.VarientValue;

@Repository
public interface VarientValueRepository extends JpaRepository<VarientValue, Long>{

	List<VarientValue> findByVarientId(long varientId);

	void deleteByVarientId(Long varientId);

	List<VarientValue> findAllByEmail(String email);

}
