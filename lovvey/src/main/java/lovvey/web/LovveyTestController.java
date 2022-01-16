package lovvey.web;


import javax.annotation.Resource;

import org.apache.logging.slf4j.Log4jLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.fasterxml.jackson.databind.ObjectMapper;



import lovvey.domain.Test;
import lovvey.service.LovveyTestService;




@Controller
public class LovveyTestController {


	
	@Resource(name="lovveyTestService")
	private LovveyTestService lovveyTestService;
	
	//Jackson
	private ObjectMapper objectMapper = new ObjectMapper();

	@ResponseBody
	@RequestMapping(value="/testInsert", method=RequestMethod.GET )
	public ResponseEntity<String> InsertTest(@RequestBody String messageBody) throws Exception {
		
			System.out.println("test"+messageBody);
			
			//json->String
			Test test =objectMapper.readValue(messageBody, Test.class);
		       
			//로직 작성하는곳.
			System.out.println("test: " +test);
			
			lovveyTestService.insertTest(test);
			
			//객체 -> json
			String jsonString = objectMapper.writeValueAsString(test);
			
			return new ResponseEntity<>(jsonString, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/test",method=RequestMethod.GET )
	public String test() {
		return "/test";
	}
	
	//로그인 접속
	@RequestMapping(value="/kakaologinpage", method=RequestMethod.GET)
	public String kakaologin() throws Exception{
				
		String id="0c12f01c99a0a4f073267e6067275788";
		String redirect_uri="http://localhost:8080/lovvey/kakaologin";
		String url="https://kauth.kakao.com/oauth/authorize?client_id="+id+"&redirect_uri="+redirect_uri+"&response_type=code";
		return "redirect:"+url;
	}
	
	
//	//카카오 로그인
	@RequestMapping(value="/kakaologin", method=RequestMethod.GET)
	public String kakaologinTest3(@RequestParam String code) throws Exception{
		
		
		
		System.out.println("testCode = "+code );
				
		return "/kakaologin";
	}
	

	
	
	
	
	

	
}
