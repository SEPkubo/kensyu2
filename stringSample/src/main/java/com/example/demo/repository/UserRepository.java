package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;


/**
 * ユーザー情報 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
//	 @Query(value = "Select * from user where delete_flg = 0", nativeQuery = true) // SQL
//	  List<User> findAll();



//	 @Query(value = "Select * from user where delete_flg = 0 AND address LIKE  %?1%", nativeQuery = true) // SQL
//	  List<User> listserch(String keyword);

	@Query(value = "Select * from user where delete_flg = 0", nativeQuery = true)
	 public Page<User> findAll(Pageable pageable);



	 @Query(value = "Select * from user where delete_flg = 0 AND address LIKE  %?1%", nativeQuery = true)
	 public Page<User> findAddress(String keyword,Pageable pageable);		// 住所検索



}