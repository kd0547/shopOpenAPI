package com.movie.createOpenAPIConnect;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public interface OpenAPIConnector {
	 public String OpenAPIRequest(String Method,String url);
	 public  InputStreamReader  createInputStreamReader(InputStream inputstream);
	 public  String readInputStreamData(InputStream inputstream);
	public void closeConnection();
	 
	 
}
