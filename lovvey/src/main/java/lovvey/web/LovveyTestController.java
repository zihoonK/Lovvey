package lovvey.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.fabric.xmlrpc.base.Params;

import lovvey.domain.Test;
import lovvey.service.LovveyTestService;
import lovvey.util.httpConnection;

@Controller
public class LovveyTestController {

	httpConnection conn = httpConnection.getInstance();

	@Resource(name = "lovveyTestService")
	private LovveyTestService lovveyTestService;

	// Jackson
	private ObjectMapper objectMapper = new ObjectMapper();

	@ResponseBody
	@RequestMapping(value = "/testInsert", method = RequestMethod.GET)
	public ResponseEntity<String> InsertTest(@RequestBody String messageBody) throws Exception {

		System.out.println("test" + messageBody);

		// json->String
		Test test = objectMapper.readValue(messageBody, Test.class);

		// 로직 작성하는곳.
		System.out.println("test: " + test);

		lovveyTestService.insertTest(test);

		// 객체 -> json
		String jsonString = objectMapper.writeValueAsString(test);

		return new ResponseEntity<>(jsonString, HttpStatus.OK);

	}

	@RequestMapping(value = "/home")
	public String home() {

		return "/kakaologin";
	}

	/**
	 * @Method 카카오 로그인 페이지
	 * @return 카카오 로그인으로 이동
	 * @throws Exception
	 */
	@RequestMapping(value = "/kakaologinpage", method = RequestMethod.GET)
	public String kakaologin() throws Exception {

		String id = "0c12f01c99a0a4f073267e6067275788";
		String redirect_uri = "http://localhost:8080/lovvey/kakaologin";
		String url = "https://kauth.kakao.com/oauth/authorize?client_id=" + id + "&redirect_uri=" + redirect_uri
				+ "&response_type=code";
		return "redirect:" + url;
	}

	/**
	 * 
	 * @param code 카카로 로그인 코드를 가지고 있다.
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/kakaologin", method = RequestMethod.GET)
	public String kakaologinTest3(@RequestParam String code, HttpSession session) throws Exception {

		System.out.println("testCode = " + code);
		
		String access_token= conn.getAccessToken(code);
		
		System.out.println("access_token"+ access_token);
		session.setAttribute("access_token", access_token);

		return "/kakaologin";
	}

	/**
	 * 
	 * @param session 계정 토큰을 가지고 있다.
	 * @return /kakaologin 페이지로 이동.
	 * @throws Exception
	 */

	@RequestMapping(value = "/logoutredirect", method=RequestMethod.POST)
	public String logout(HttpSession session) throws Exception {

		String access_token = (String) session.getAttribute("access_token");
		String reqURL = "https://kapi.kakao.com/v1/user/logout";

	

		/**
		 * 2번
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("Authorization", "Bearer " + access_token);

		conn.HttpPostLogOut(reqURL, map, access_token);
		session.removeAttribute(access_token);
		System.out.println(access_token);

		return "/kakaologin";

	}

}
