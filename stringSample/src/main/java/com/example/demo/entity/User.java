package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;



/**
 * ユーザー情報 Entity
 */
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"id", "name","address","phone","delete_flg"}))
@NamedNativeQueries({
@NamedNativeQuery(name = "selectquery",
query = "Select id, name, address, phone,delete_flg from user where delete_flg = 0",
resultClass = User.class)

})


// private EntityManager em;

public class User implements Serializable {


   /**
    * ID
    */
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;


	/**
	 * 名前
	 */
	@Column(name="name")
	private String name;

	/**
	 * 住所
	 */
	@Column(name="address")
	private String address;

	/**
	 * 電話番号
	 */
	@Column(name="phone")
	private String phone;

	/**
	 * 削除フラグ
	 */
	@Column(name="delete_flg")
	private int delete_flg;

}