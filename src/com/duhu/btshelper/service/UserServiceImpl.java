package com.duhu.btshelper.service;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.duhu.btshelper.LoginActivity;

import android.util.Log;

public class UserServiceImpl implements UserService {
	private static final String TAG = "USERSERVICEIMPL";

	@Override
	public void userlogin(String loginName, String loginPassword)
			throws Exception {
		Log.d(TAG, loginName);
		Log.d(TAG, loginPassword);
	//post	
//		HttpClient client = new DefaultHttpClient();
//		String uri = "http://135.193.109.15:8080/BTSHelper/Login.do";
//		HttpPost post = new HttpPost(uri);
//		NameValuePair parmloginname = new BasicNameValuePair("LoginName", loginName);
//		NameValuePair parmloginpassword = new BasicNameValuePair("LoginPassword", loginPassword);
//		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//		parameters.add(parmloginname);
//		parameters.add(parmloginpassword);
//		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
//		HttpResponse response = client.execute(post);
		//get
		HttpClient client = new DefaultHttpClient();
		String uri = "http://192.168.1.101:8080/BTSHelper/Login.do?LoginName="
				+ loginName + "&LoginPassword=" + loginPassword;
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		int statuscode = response.getStatusLine().getStatusCode();
		if (statuscode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(LoginActivity.MSG_HTTP_FAIL);
		} 
		String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
		
		if (responseString.equals("chenggong")) {
			
		} else {
			throw new ServiceRulesException(LoginActivity.MSG_LOGIN_FAIL);
		}
	}

}
