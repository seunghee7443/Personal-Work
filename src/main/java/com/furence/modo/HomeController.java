package com.furence.modo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.furence.modo.domain.Userinfo;
import com.furence.modo.mapper.UserinfoMapper;

@Controller
public class HomeController {
	@Autowired
	private UserinfoMapper userinfoMapper;	// query를 사용하기 위하여 interface의 의존성 주입을 위해 사용

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		userinfoMapper.clearTable("id");	// Test 환경을 위해 추가함 - 실제 라이브 상황에서는 사용하여서는 안됨(Table의 초기화를 위해 사용)
		
		return "home";
	}
	
	String filename;						// File Name을 저장하기 위해 선언
	String fileExt;							// File의 확장자를 구하여서 저장하기 위해 선언
	
	final String extCheck = "dbfile";		// File의 확장자 체크를 위해 선언하였으며, 변경하면 안되기에 final 선언
	final String path = "C:\\upload";		// final : 변수나 메서드, 클래스 앞에 선언시 변수의 변경을 제한하게 됨(변수 → 상수로 바뀌게 됨)
	
	File filePath;							// File이 저장할 경로를 저장하기 위해 선언
	
	// fileupload의 화면을 불러오기 위한 Mapping
	@GetMapping("/fileupload")
	public void fileupload() {}
	
	// fileupload에서 요청한 작업을 처리 하기 위한 Mapping(JSP의 <form> Tag의 action="upload"의 요청)
	@PostMapping("/upload")
	public String fileUpload(@RequestParam("filename") MultipartFile request, Model model) {
		// File의 원래 이름을 얻기 위해 명시 - 해당 변수는 상위 @RequestParam에서 받아옴 (JSP(fileupload.jsp)의 <input type="file" name="filename">의 name값)
		filename = request.getOriginalFilename();
		// File의 확장자명을 얻기 위해 사용 - 상위 filename의 전체 길이중 "."부터 시작한 값, "."이후 부터 검수하기 위해 + 1을 시행
		fileExt = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		
		// File의 저장 경로 선언 - 초기 선언한 path의 이후 경로 지정 및 File의 이름 지정
		filePath = new File(path + "\\" + filename);

		try {
			/*
			 *  fileExt(실제 Upload 요청한 File에서 구한 확장자명)과 extCheck(처음 확장자명을 선언한 final 변수)를 비교하기 위한 조건문
			 *  양쪽의 String을 비교하기 위해 equalsIgnoreCase를 사용(대,소문자를 무시한 문자와 길이를 비교)
			 */
			if (fileExt.equalsIgnoreCase(extCheck)) {
				// 두 문자 비교 후 일치할 경우 저장 실행
				request.transferTo(filePath);
			} else {
				// 아닐경우 return되는 JSP로 반환
				return "uploadfault";
			}
			
		} catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

		/*
		 *  MVC에서 일반적인 Return을 시행할 경우 view의 화면으로 이동
		 *  redirect를 이용하여 Return해줄 경우 view가 아닌 다른 controller를 호출하여 이동한다
		 */
		return "/result";
	}
	
	
	// 위의 /upload의 작업 후 요청 받아 처리 하기 위한 Method
	@PostMapping("/fileRead")
	public String fileRead(Model model) {
		int clearCnt = 0;						// 성공한 횟수를 저장하기 위한 선언
		int faultCnt = 0;						// 실패한 횟수를 저장하기 위한 선언
		int lineCnt = 0;						// 수행한 라인의 번호를 저장하기 위한 선언
		
		// 실패한 라인을 저장하기 위한 List선언 - ArrayList로 받은 이유는 Data의 양(line의 수)이 변할수 있기 때문에 고정적으로 선언할 때 보다 유동적으로 사용할 수 있음
		List<String> faultLine = new ArrayList<String>();
		
		// userinfo Table에서 user의 id만을 찾기 위한 Query실행 후, 이를 list형태로 받음
		List<Userinfo> findId = userinfoMapper.findId();
		
		try {
			FileReader fileReader = new FileReader(filePath);
			
			BufferedReader bufReader = new BufferedReader(fileReader);
			
			String line = "";					// 초기 저장할 Text의 Line을 저장하기 위한 선언 - 변수가 저장된 값이 없다는걸 선언하기위해 큰따옴표로 명시(문자열이라 정수값은 사용불가)
			
			while ((line = bufReader.readLine()) != null) {
				// 문자 저장을 위한 String배열 선언
				String[] lineText = line.split("/");
				
				try {
					// 각 String배열을 Query에서 Insert할 경우의 각각의 주소값을 대입)
					Userinfo userinfo = new Userinfo(lineText[0], lineText[1], lineText[2], lineText[3], lineText[4], lineText[5]);
					
					/*
					 *  각 if ~ else if문은 각 Query의 해당되는 값(id, pwd, name, level, desc, reg_date)중 desc를 제외한
					 *  나머지 5개의 Column을 Null 제약조건에 맞추어서 공백과 null값이 있을 경우 해당 Line은 Error가 포함된 Line으로 인식하여
					 *  Error 처리 하도록 정의
					 *  ※ DataBase에서는 null과 달리 공백의 경우 공백의 수만큼을 문자로 볼 수 있음
					 */
					if ((userinfo.getId() == null) || (userinfo.getId().isEmpty())) {
						faultCnt = faultCnt + 1;
						faultLine.add(String.valueOf(lineCnt + 1) + "번 라인 : " + line);
						lineCnt = lineCnt + 1;
						continue;				// 위의 if문의 작업 처리 후, 해당 if문을 빠저나와 다음 반복문을 실행하기 위해 보낸다(while문으로 돌려 보냄)
					}
					
					else if ((userinfo.getPwd() == null) || (userinfo.getPwd().isEmpty())) {
						faultCnt = faultCnt + 1;
						faultLine.add(String.valueOf(lineCnt + 1) + "번 라인 : " + line);
						lineCnt = lineCnt + 1;
						continue;
					}
					
					else if ((userinfo.getName() == null) || (userinfo.getName().isEmpty())) {
						faultCnt = faultCnt + 1;
						faultLine.add(String.valueOf(lineCnt + 1) + "번 라인 : " + line);
						lineCnt = lineCnt + 1;
						continue;
					}
					
					else if ((userinfo.getLevel() == null) || (userinfo.getLevel().isEmpty())) {
						faultCnt = faultCnt + 1;
						faultLine.add(String.valueOf(lineCnt + 1) + "번 라인 : " + line);
						lineCnt = lineCnt + 1;
						continue;
					}
					
					else if ((userinfo.getReg_date() == null) || (userinfo.getReg_date().isEmpty())) {
						faultCnt = faultCnt + 1;
						faultLine.add(String.valueOf(lineCnt + 1) + "번 라인 : " + line);
						lineCnt = lineCnt + 1;
						continue;
					}
					
					// 위의 조건을 모두 위배하지 않는 Column의 Query를 실행 및 정상 처리로 Count된다
					else {
						userinfoMapper.insertUserinfo(userinfo);
						clearCnt = clearCnt + 1;
						lineCnt = lineCnt + 1;
					}
					
				}
				
				// 위의 조건에 부합하지 않는 경우 Error 처리(Table의 Column의 수와 File의 라인별 Data수의 불일치)
				catch (Exception e) {
					faultCnt = faultCnt + 1;
					faultLine.add(String.valueOf(lineCnt + 1) + "번 라인 : " + line);
					lineCnt = lineCnt + 1;
					
					// console에서 Error 내용을 별도로 확인하기 위해 출력
					System.out.println(e.getLocalizedMessage());
				}
				
			}
			// 실제 console에서 정상적으로 처리 되나 확인하기 위해 삽입됨
			System.out.println("성공 : " + clearCnt);
			System.out.println("실패 : " + faultCnt);
			System.out.println("실패 : " + faultLine);
			
			bufReader.close();					// 작업을 위해 열어둔 File을 닫음
		}
		
		// 해당 위치(fileReader 변수에 선언된 파일 경로)에 File이 없을 경우 실행할 메세지
		catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		// 입력(Input)및 출력(Output)이 실패했을 경우(파일 읽는 중 강제 종료 등) 실행할 메세지
		catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		/*
		 *  저장된 변수중 실패한 라인 배열 안의 값이 비어있을 경우 성공 횟수만 model을 통해 view에 전달
		 *  아닌 경우 성공, 실패 횟수 및 실패한 라인, 실패한 라인과 비교할 쿼리 실행 값을 model을 통해 view에 전달
		 */
		if (faultLine.isEmpty()) {
			model.addAttribute("clearCnt", clearCnt);	
		} else {
			model.addAttribute("clearCnt", clearCnt);
			model.addAttribute("faultCnt", faultCnt);
			model.addAttribute("findId", findId);
			model.addAttribute("faultLine", faultLine);
		}
		
		// 위의 model값을 전달 받을 주소(JSP)
		return "result";
	}
	
}
