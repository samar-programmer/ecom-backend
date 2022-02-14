package com.revature.project.amazon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.project.amazon.model.User;

@Repository
public interface HomeRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	@Modifying
	@Query("update com.revature.project.amazon.model.User eu set eu.otp=:otp where email=:to")
	public void updateUserByOtp(@Param("to")String to, @Param("otp")long otp);


	User findByOtp(long tempOtp);

}
