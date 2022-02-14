package com.revature.project.amazon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project.amazon.model.VarientValues;

@Repository
public interface VarientValueRepository extends JpaRepository<VarientValues, Long>{

	List<VarientValues> findByVarientId(long varientId);

	void deleteByVarientId(Long varientId);

	List<VarientValues> findAllByEmail(String email);

}
