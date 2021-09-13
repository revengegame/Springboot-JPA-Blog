package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;




//html파일이 아니라 data를 리턴해주는 controller=RestController
@RestController
public class DummyControllerTest {
	
	@Autowired //UserRepository타입의 객체가 있다면 객체 주입 의존성주입
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String Delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다 해당id는 DB에 없습니다";
		}
		return "삭제되었습니다 : " + id;
	}
	
	
	//save함수는 id를 전달하지 않으면 insert를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다
	//email,password
	@Transactional//save함수 사용하지 않아도 자동 update,함수 종료시 자동 commit
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
		System.out.println("id : "+id);
		System.out.println("password : " + requestUser.getPassword());
		//json데이터를 요청 =>Java Object(MessageConverter의 Jackson라이브러리가 변환해서 받아줌)
		System.out.println("email : " + requestUser.getEmail());
		
		//DB에 있는 user테이블 id값에 맞는 값을 영속성 컨텍스트 1차 캐시에 영속화 후
		//controller로 가져온다
		User user=userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다");
		});
		//user객체 password,email값 변경
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//여기까지 함수 종료, 하지만 @Transactional로 인해 commit실행
		//user객체는 영속화 된 상태이기 때문에 commit후 비교해서 변화된 값 변경후
		//controller종료시 DB update수행 -> 더티 체킹
		//userRepository.save(user);
		
		return user;
	}

	//{id}주소로 파라미터를 전달 받을 수 있음
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user/4을 찾으면 내가 DB에서 못 찾아오면 user가 null이 될 것 아냐?
		//그럼 return 할때 null이 리턴이 되잖아 그럼 프로그램에 문제가 생김
		//Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해
		User user=userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다 id : "+id);
			}
		});
		//요청 : 웹브라우저
		//user객체 = 자바 오브젝트
		//변환(웹브라우저가 이해할 수 있는 데이터)->json(Gson 라이브러리)
		//스프링부트 = MassageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MassageConverter가 Jackson이라는
		//라이브러리를 호출해서 user오브젝트를 json으로 변환해서 브라우저에 던져준다
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건에 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> PagedList(@PageableDefault(size = 2,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser=userRepository.findAll(pageable);
		
		List<User> users=pagingUser.getContent();
		return users;
		
	}
	
	//http://localhost:8000/blog/dummy/join(요청)
	//http의 body에 username,password,email데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) {//key=value(약속된 규칙)
		System.out.println("id " +user.getId());
		System.out.println("role " +user.getRole());
		System.out.println("date " +user.getCreateDate());
		System.out.println("username "+ user.getUsername());
		System.out.println("password "+ user.getPassword());
		System.out.println("email "+ user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	
	
	
}
