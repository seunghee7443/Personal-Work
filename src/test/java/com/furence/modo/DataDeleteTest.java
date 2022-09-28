package com.furence.modo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.furence.modo.mapper.UserinfoMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class DataDeleteTest {
	@Autowired
	UserinfoMapper userinfoMapper;
	
	//@Test
	public void deleteTest() {
		//userinfoMapper.clearTable("HKT");
	}
}
