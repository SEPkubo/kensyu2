package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;


/**
 * ユーザー情報 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 @Query(value = "Select * from user where delete_flg = 0", nativeQuery = true) // SQL
	  List<User> findAll();



	 @Query(value = "Select * from user where delete_flg = 0 AND address LIKE  %?1%", nativeQuery = true) // SQL
	  List<User> listserch(String keyword);



}