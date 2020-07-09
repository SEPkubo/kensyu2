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

	@Query(value = "Select * from user where delete_flg = 0 ORDER BY address ASC,phone ASC", nativeQuery = true)
	 public Page<User> findAll(Pageable pageable);		// 検索条件がない場合



	 @Query(value = "Select * from user where delete_flg = 0 AND address LIKE  %?1% ORDER BY address ASC,phone ASC", nativeQuery = true)
	 public Page<User> findAddress(String keyword,Pageable pageable);		// 住所検索



}