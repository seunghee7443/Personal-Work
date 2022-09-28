package com.furence.modo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.furence.modo.domain.Userinfo;
import com.furence.modo.mapper.UserinfoMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class DataInsertTest {
	@Autowired(required=true)
	UserinfoMapper userinfoMapper;
	
	Userinfo userinfo;
	
	/*
	@Autowired(required=true)
	Userinfo userinfo
	*/;

	@Test
	public void mapperTest() {
		/*
		Userinfo userinfo = new Userinfo("HKT", "1234", "홍길동", "A", "호부호형못함", "2020-10-01 10:00:00");
		int cnt = userinfoMapper.insertUserinfo(userinfo);
		System.out.println(cnt);
		*/
		
		
		/*
		List<Userinfo> user = userinfoMapper.findId();
		for (Userinfo userinfo : user) {
			if(!(user.isEmpty())) {
				userinfoMapper.clearTable();
			}
			System.out.println(userinfo);
		}
		*/
		
		//List<Userinfo> list = userinfoMapper.getUserinfo();
		//System.out.println(list);
		
		//userinfoMapper.clearTable("id");
			
		}
	
	@Test
	public void fileRead() {
		try {
			FileReader fileReader = new FileReader("C:\\upload\\data.dbfile");
			
			BufferedReader bufReader = new BufferedReader(fileReader);
			
			String line = "";
			
			while ((line = bufReader.readLine()) != null) {
				String[] linetext = line.split("/");
				
				Userinfo userinfo = new Userinfo(linetext[0], linetext[1], linetext[2], linetext[3], linetext[4], linetext[5]);
				if ((userinfo.getId() == null) || (userinfo.getId().isEmpty())) {
					//notAddCnt = notAddCnt + 1;
					//notAddText.add(String.valueOf(lineCnt + 1) + "번라인" + line);
					//lineCnt = lineCnt + 1;
					continue;
				}
				
				else if ((userinfo.getPwd() == null) || (userinfo.getPwd().isEmpty())) {
					//notAddCnt = notAddCnt + 1;
					//notAddText.add(String.valueOf(lineCnt + 1) + "번 라인 :" + line);
					//lineCnt = lineCnt + 1;
					continue;
				}
				
				else if ((userinfo.getName() == null) || (userinfo.getName().isEmpty())) {
					//notAddCnt = notAddCnt + 1;
					//notAddText.add(String.valueOf(lineCnt + 1) + "번 라인 :" + line);
					//lineCnt = lineCnt + 1;
					continue;
				}
				
				else if ((userinfo.getLevel() == null) || (userinfo.getLevel().isEmpty())) {
					//notAddCnt = notAddCnt + 1;
					//notAddText.add(String.valueOf(lineCnt + 1) + "번 라인 :" + line);
					//lineCnt = lineCnt + 1;
					continue;
				}
				
				else if ((userinfo.getReg_date() == null) || (userinfo.getReg_date().isEmpty())) {
					//notAddCnt = notAddCnt + 1;
					//notAddText.add(String.valueOf(lineCnt + 1) + "번 라인 :" + line);
					//lineCnt = lineCnt + 1;
					continue;
				}
				
				else {
					userinfoMapper.insertUserinfo(userinfo);
					//addCnt = addCnt + 1;
					//lineCnt = lineCnt + 1;
				}
				
			}
			
			//System.out.println("성공 : " + addCnt);
			//System.out.println("실패 : " + notAddCnt);
			//System.out.println("실패 : " + notAddText);
			
			bufReader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		} finally {}
		
		
		/*
		try {
			File file = new File("C:\\upload\\data.dbfile");
			
			FileReader fileReader = new FileReader(file);
			
			BufferedReader bufReader = new BufferedReader(fileReader);
			
			String line = null;
			
			while ((line = bufReader.readLine()) != null) {
				System.out.println(line);

				List<String> textList = Arrays.asList(line.split("\\/"));
				//textList.removeAll(Arrays.asList(""));
				System.out.println(textList);
				
				
				Userinfo userinfo = new Userinfo(textList.get(0), textList.get(1), textList.get(2), textList.get(3), textList.get(4), textList.get(5));
				int cnt = userinfoMapper.insertUserinfo(userinfo);
				System.out.println(cnt);
				/*
				String[] lineSplit = line.split("\\/");
				for (int i = 0; i < lineSplit.length; i = i + 1) {
					System.out.print(lineSplit[i] + " ");
				}
				System.out.println(Arrays.toString(line.split("\\/")));
				Userinfo userinfo = new Userinfo()
				
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		*/
	}
}
