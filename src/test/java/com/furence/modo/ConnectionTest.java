package com.furence.modo;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ConnectionTest {
	@Autowired
	private DataSource ds;
	
	/* @Test
	public void ConnectionTest1() {
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://localhost:5432/userdb";
		String id = "user8468";
		String pw = "718468";
		
		try {
			Class.forName(driver);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		try (Connection con = DriverManager.getConnection(url, id, pw)) {
			System.out.println(con);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	*/ 
	
	@Test
	public void ConnectionTest2() {
		System.out.println("DataBase : " + ds);
		
		try (Connection con = ds.getConnection()) {
			System.out.println(con);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	@Autowired
	private SqlSession sqlSession;
	
	@Test
	public void mybatisTest() {
		System.out.println("MyBatis 객체 : " + sqlSession);
	}
}
