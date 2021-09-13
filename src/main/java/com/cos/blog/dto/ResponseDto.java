package com.cos.blog.dto;

import org.springframework.http.HttpStatus;

public class ResponseDto<T> {

	int status;
	T dataT;
	
	
	
	public ResponseDto() {
		// TODO Auto-generated constructor stub
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getDataT() {
		return dataT;
	}

	public void setDataT(T dataT) {
		this.dataT = dataT;
	}

	public ResponseDto(int status, T dataT) {
		super();
		this.status = status;
		this.dataT = dataT;
	}
	
	
	
	
	
}