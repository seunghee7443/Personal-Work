package com.furence.modo.mapper;

import java.util.List;

import com.furence.modo.domain.Userinfo;

// src/main/resources/mapper/userinfo.xml에서 선언된 query를 사용하기 위한 interface
public interface UserinfoMapper {
	int insertUserinfo(Userinfo userinfo);		// data를 삽입하기 위한 Method 선언
	
	public List<Userinfo> getUserinfo();		// 저장된 table의 모든 data를 조회하기 위한 Method 선언
	
	public List<Userinfo> findId();				// 저장된 table중 id에 해당되는 값을 조회하기 위한 Method 선언
	
	void clearTable(String id);					// 저장된 table의 모든 data를 삭제하기 위한 Method 선언(해당 Method의 경우는 Test를 위해 삽입된 Method)

}