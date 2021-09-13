package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

//ORM -> java(다른언어)Object -> 테이블로 매핑해주는 기술
@Entity //User클래스가 MySQL에 자동으로 테이블이 생성이 된다
//@DynamicInsert insert시에 null인 필드 제외
public class User {

	@Id//기본키
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//프로젝트에서 연결된 연결된 DB의 넘버링 전략을 따라간다
	private int id;//시퀀스,auto_increment
	
	@Column(nullable = false,length = 30,unique = true)//null값x 
	private String username;//아이디
	
	@Column(nullable = false,length = 100)//null값x 123456=>해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable = false,length = 50)
	private String email;
	
	//DB는 RoleType이라는게 없다
	//@ColumnDefault("user")
	@Enumerated(EnumType.STRING)
	private RoleType role;//Enum을 쓰는게 좋다.//ADMIN,USER
	
	@CreationTimestamp//시간이 자동으로 입력
	private Timestamp createDate;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public User(int id, String username, String password, String email, RoleType role, Timestamp createDate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.createDate = createDate;
	}
	
	

	
}
