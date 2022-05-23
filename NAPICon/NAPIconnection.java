package com.movie.NAPICon;

import com.movie.createOpenAPIConnect.OpenAPIConnector;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Stream;



public class NAPIconnection implements OpenAPIConnector{

	private HttpURLConnection httpURLConnection= null;
	/*
	 * 	inputStream에서 데이터를 받아올 때 인코딩 타입
	 *  	디폴트는 EUC-KR
	 */
	private String inputStreamEncodingType ="EUC-KR";
	
	private String StringDataEncodingType = "UTF-8";

	 /*
	  * 
	  * 	생성자
	  * 
	 */
	
	
	public NAPIconnection(){
		
	}
	public NAPIconnection(HttpURLConnection httpURLConnection){
		this.httpURLConnection = httpURLConnection;
	}
	
	
	/*
	 * 
	 * 	메서드 
	 * 
	 */
	
	/*
	 * 
	 * 	*********gitHub에 작성할 알고리즘 흐름*********
	 * 	1. HttpURLConnection 생성 
	 * 	2. NAPIconnection클래스를 생성할 때 HttpURLConnection을 매개변수로 활용
	 * 	3. OPENAPI에서 추가적인 헤더가 필요하면 setHttpHeader() 활용
	 * 	4. OpenAPIRequest(String Method,String url) 메서드를 사용해 서버에서 요청 -> responseCode가 200일 때 : getInputStream();
	 * 	5.  InputStream ->  InputStreamReader -> BufferedReader -> xml or json 데이터 반환
	 * 
	 */
	
	
	
	public void setHttpURLConnection(HttpURLConnection httpURLConnection) {
		this.httpURLConnection = httpURLConnection;
	}
	
	
	
	public void setHttpHeader(Map<String, String> requestHeader) {
		 
	 	if(requestHeader != null) {
	 		for (Map.Entry<String, String> header : requestHeader.entrySet()) {
				 httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
				
			}
	 	}

	}
	/*
	 * 	서버에서 데이터를 받아올 때 inputStreamType을 
	 * 	
	 * 
	 */
	public void setInputStreamEncodingType(String inputStreamEncodingType) {
		this.inputStreamEncodingType = inputStreamEncodingType;
	}
	
	/*
	 * 	
	 * 	
	 * 
	 */
	public void setStringDataEncodingType(String StringDataEncodingType) {
		this.StringDataEncodingType = StringDataEncodingType;
	}
	
	
	/*
	 * 
	 * 	열려있는 Connection을 닫아주는 함수
	 * 	OpenAPIRequest() 함수를 사용하면 필수적으로 넣어야된다. 
	 */
	@Override
	public void closeConnection() {
		httpURLConnection.disconnect();
	}
	
	 /*
	  * 
	  * 	
	  * 
	  */
	@Override
	 public String OpenAPIRequest(String Method,String url) {
		 //HttpURLConnection httpURLConnection = connect(url);
		 
		 try {
				httpURLConnection.setRequestMethod(Method);
				
				int responseCode = httpURLConnection.getResponseCode();
				
				if(responseCode == 200) {
					return readInputStreamData(httpURLConnection.getInputStream());
				}	else if(responseCode == 400){
					return readInputStreamData(httpURLConnection.getErrorStream()) ;
				} else if(responseCode == 404) {
					return readInputStreamData(httpURLConnection.getErrorStream());
				} else {
					return readInputStreamData(httpURLConnection.getErrorStream());
				}
			} catch (ProtocolException e) {
				
				e.printStackTrace();
			} catch (IOException  e) {
				e.printStackTrace();
			} 
		 
		return null;
	 }
	/*
	 * 
	 * 
	 */
	@Override
	 public  String readInputStreamData(InputStream inputstream){
	        InputStreamReader streamReader = null;
			try {
				streamReader = new InputStreamReader(inputstream,this.inputStreamEncodingType);
			} catch (UnsupportedEncodingException e1) {
				
				e1.printStackTrace();
			}

	        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
	            StringBuilder responseBody = new StringBuilder();

	            String line = new String(this.StringDataEncodingType);
	            while ((line = lineReader.readLine()) != null) {
	                responseBody.append(line);
	            }

	            return responseBody.toString();
	        } catch (IOException e) {
	            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
	        }
	 }
	
	 /*
	  * 
	  * 
	  * 
	  */
	@Override
	public InputStreamReader createInputStreamReader(InputStream inputstream) {
		InputStreamReader streamReader = null;
		
		try {
			streamReader = new InputStreamReader(inputstream,this.inputStreamEncodingType);
			
			return streamReader;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return streamReader;
	}
	
	 /*
	  * 
	  * 
	  * 
	  */
	public String extractInputStreamData(InputStreamReader streamReader) {
		
		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line = new String(this.StringDataEncodingType);
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
	}
	
	
	
	
	
	 
	 /*
	  * 	사용 안할 예정
	  * 
	  *
	 public HttpURLConnection connect(String apiUrl) {
		 try {
			URL url = new URL(apiUrl);
			
			return (HttpURLConnection) url.openConnection();
			
		} catch (MalformedURLException e) {
			throw new RuntimeException("API 연결 실패 :" + apiUrl,e);
		} catch (IOException e) {
			throw new RuntimeException("IO 연결 실패 :" + apiUrl,e);
		} 
	 }
	*
	 * 
	 * 
	 */
	
	/*
	 * 
	 */
	 
	
	
	
	 
	 
	 
	 
	 
	 
}
