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
import org.springframework.ui.Model;
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

/**
 * 
 * @author EaBell
 *   
 *   컨트롤러 hashMAP으로 return값으로 받아서 값 넣는거 생각해두기.
 */


@Controller
public class LovveyTestController {

	httpConnection conn = httpConnection.getInstance();

	@Resource(name = "lovveyTestService")
	private LovveyTestService lovveyTestService;

	// Jackson
	private static ObjectMapper objectMapper = new ObjectMapper();

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

		return "redirect:/kakaologin";
	}

	/**
	 * @Method 카카오 로그인 페이지
	 * @return 카카오 로그인으로 이동
	 * @throws Exception
	 */
	@RequestMapping(value = "/kakaologinpage", method = RequestMethod.GET)
	public String KakaologinPage() throws Exception {

		String id = "0c12f01c99a0a4f073267e6067275788";
		String redirect_uri = "http://localhost:8080/lovvey/kakaologin";
		String url = "https://kauth.kakao.com/oauth/authorize?client_id=" + id + "&redirect_uri=" + redirect_uri
				+ "&response_type=code";
		return "redirect:" + url;
	}

	/**
	 * @Method AccessToken 얻기
	 * @param code 카카로 로그인 코드를 가지고 있다.
	 * @return /kakaologin 페이지로 이동.
	 * @throws Exception
	 */

	@RequestMapping(value = "/kakaologin", method = RequestMethod.GET)
	//@ResponseBody
	public String kakaologin(@RequestParam String code, HttpSession session) throws Exception {
		
	System.out.println("code = "+ code);
		
		System.out.println("===============kakaologin=====================");
		
		
		
		String access_token= conn.getAccessToken(code);
		System.out.println("access_token입니다 =" +access_token);
		session.setAttribute("access_token", access_token);
		
		System.out.println("===============kakaologin=====================");
		
		
		
		return "/kakaologin";
	}

	/**
	 * @Method 회원 탈퇴
	 * @param session 계정 토큰을 가지고 있다.
	 * @return /kakaologin 페이지로 이동.
	 * @throws Exception
	 * 
	 */

	@RequestMapping(value = "/kakaologout", method=RequestMethod.POST)
//	@ResponseBody
	public String Logout(HttpSession session) throws Exception {
		System.out.println("===============logoutredirect=====================");
		
		String access_token = (String) session.getAttribute("access_token");
		System.out.println("access_token입니다.");
		
//		/**
//		 * session.getAttribute("access_token")이 존재 확인
//		 */
		if(access_token != null) {
			String JsonString=conn.HttpPostLogout(access_token);
			//String JsonString=conn.HttpPostLogOut(access_token);
			System.out.println( "확인용 코드입니다."+JsonString);
			
			session.removeAttribute(access_token);
			session.invalidate();
			System.out.println("===============logoutredirect 성공역역입니다.=====================");
			
		}else {
			
			System.out.println("===============logoutredirectError영역입니다.=====================");	
		}

		return "/kakaologin";

	}
	
	@RequestMapping(value="/logout")
	public String AccountWithdrawal(HttpSession session, Model model) throws Exception{
		
		
		return "/kakaologin";
	}
	
	
	@RequestMapping(value ="/kakaouserinfo", method=RequestMethod.POST)
	@ResponseBody
	public String UserInfo( String access_token) throws Exception{
		
//		String access_token= (String)session.getAttribute("access_token");
		String userInfo = null;
		
		if(hasAccessToken(access_token)) {
		userInfo= conn.getUserInfo(access_token);
		}
		
		return userInfo;
	}
	
	
	
	
	
	
	
	public Boolean hasAccessToken(String access_token) {
		
		
		if(access_token !=null) {
			
			return true;
		}
		return false;
				
	}
	

}
