package lovvey.util;

import java.awt.geom.RectangularShape;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class httpConnection {
	public static final String ENCODING = "UTF-8";

	private httpConnection() {
	}

	private static class httpConnection_Singieton {
		private static final httpConnection instance = new httpConnection();
	}

	public static httpConnection getInstance() {
		return httpConnection_Singieton.instance;
	}

	public String getAccessToken(String code) {
		
		String accessToken = "";
		String refreshToken = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		
		try {
			/**
			 * 요청하기.
			 */
			URL url = new URL(reqURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			
			/**
			 * 
			 */
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
			StringBuffer sb = new StringBuffer();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=0c12f01c99a0a4f073267e6067275788");
			sb.append("&redirect_uri=http://localhost:8080/lovvey/kakaologin");
			sb.append("&code="+code);
			// 보안 설정에서 client_secret가 ON일태 값을 넣어두시오.
			//sb.append("&redirect_uri");
			
			bw.write(sb.toString());
			bw.flush();
			
			int responseCode = con.getResponseCode();
			System.out.println("response code =" + responseCode);
			
			/**
			 * 읽기.
			 */
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line = "";
			String result ="";
			while((line = br.readLine())!= null) {
				result += line;
			}
			System.out.println("response body= "+result);
			
			JsonParser parser = new JsonParser();
			JsonElement element =parser.parse(result);
			
			accessToken=element.getAsJsonObject().get("access_token").getAsString();
			refreshToken=element.getAsJsonObject().get("refresh_token").getAsString();
			
			br.close();
			bw.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return accessToken;
	}
	

	public void HttpPostLogOut(String  access_token) throws Exception {
		String reqURL = "https://kapi.kakao.com/v1/user/logout";
		String id;
		try {
			URL url = new URL(reqURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "Bearer "+ access_token);
		
			
//			System.out.println(con.getResponseMessage());
//			
//			
//			System.out.println("con.toString() =" +con.toString()+", "+con.getRequestProperties()+"," + con.getHeaderFields());
			int responseCode = con.getResponseCode();
			System.out.println("resposeCode  = "+ responseCode);
			
			BufferedReader br= new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String result = "";
			String line = "";
			while((line= br.readLine())!= null) {
				result+=line;
			}
			
			JsonParser parser = new JsonParser();
			JsonElement element =parser.parse(result);
			id=element.getAsJsonObject().get("id").getAsString();
			System.out.println(result);
			
			br.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}

	}
	
	
	
	
	

	// get방식 rest 호출시 사용
	public StringBuffer HttpGetConnection(String apiURL) throws IOException {

		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		return responseHttp(con);
	}

	// post방식 rest 호출시 사용
	public StringBuffer HttpPostConnection(String apiURL, Map<String, String> params) throws IOException {
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);

		// 만약에 파라메터에 Authorization가 있다면 헤더로 추가 후 params에서 제거
		if (params.get("Authorization") != null) {
			con.setRequestProperty("Authorization", params.get("Authorization"));
			params.remove("Authorization");
		}

		// post request
		// 해당 string은 UTF-8로 encode 후 MS949로 재 encode를 수행한 값
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		StringBuilder sb = new StringBuilder();

		int amp = 0;
		for (String key : params.keySet()) {
			// 2번째 파라메터부터는 구분자 &가 있어야한다.
			if (amp >= 1)
				sb.append("&");
			amp += 1;

			sb.append(key + params.get(key));

		}

		System.out.println("파라메터 : " + sb.toString());

		bw.write(sb.toString());
		bw.flush();
		bw.close();

		return responseHttp(con);
	}

	// 서버에 요청하는 메소드
	public StringBuffer responseHttp(HttpURLConnection con) throws IOException {
		StringBuffer response = new StringBuffer();

		int responseCode = con.getResponseCode();
		BufferedReader br;
		if (responseCode == 200) { // 정상 호출
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else { // 에러 발생
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}

		String inputLine;
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();

		return response;
	}

	// 파라메터 URL 인코딩
	public String URLencoder(String contents) throws UnsupportedEncodingException {
		return URLEncoder.encode(URLEncoder.encode(contents, ENCODING), "MS949");
	}
}
