package com.furence.modo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furence.modo.domain.Userinfo;
import com.furence.modo.mapper.UserinfoMapper;

//결과를 JSON으로 받기 위해 별도로 생성한 Class
@RestController
public class JSONController {
	@Autowired
	UserinfoMapper userinfoMapper;

	@RequestMapping("data.json")
	public List<Userinfo> getDB() {
		// Table의 모든 Data를 List로 저장하기 위한 query실행
		List<Userinfo> list = userinfoMapper.getUserinfo();
		return list;						// 저장될 값을 전달할때는 list 자체를 전달(자료의 문자 자체만 전달)
	}
}
