package com.duhu.btshelper.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.duhu.btshelper.BasicdataActivity;

public class SelectNameImpl implements SelectName {

	public String getname(int btsid) throws Exception {
		String name;
		HttpClient client = new DefaultHttpClient();
		String uri = "http://duhu110.oicp.net/BTSHelper/GetBtsname.do";
		HttpPost post = new HttpPost(uri);
		JSONObject object = new JSONObject();
		object.put("btsid", btsid);
		NameValuePair nameValuePair = new BasicNameValuePair("data",
				object.toString());
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(nameValuePair);
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse response = client.execute(post);
		int statuscode = response.getStatusLine().getStatusCode();
		if (statuscode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(BasicdataActivity.MSG_HTTP_FAIL);
		}

		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
		JSONObject object2 = new JSONObject(result);
		name = object2.getString("BTSNAME");
		
	
		return name;
	}

}
