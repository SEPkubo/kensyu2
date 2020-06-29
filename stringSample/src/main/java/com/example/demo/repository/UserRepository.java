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
//	  public static final String _querybase
//	  = "SELECT"
//			     + "id"
//			     + ",name"
//			     + ",address"
//			     + " ,phone"
//			     + "  FROM"
//			     + "  user"
//			     + "  delete_flg"
//			     + "  where"
//			     + "  delete_flg = 0";
//
//	  @Query(value = _querybase, nativeQuery = true)
//	    List<User> findAll();
//

}