package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//스프링이 com.cos.blog패키지 이하를 스캔해서
//모든 파일을 메모리에 new하는 것은 아니고
//특정 어노테이션이 붙어있는 클래스 파일들을
//new해서 (IOC)스프링 컨테이너에서 관리
@RestController
public class BlogControllerTest {

	@RequestMapping("/test/hello")
	public String con() {
		return  "hello";
	}
	
}
