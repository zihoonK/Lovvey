package lovvey.web;




import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	

	
}
