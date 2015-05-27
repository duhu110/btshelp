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
import org.json.JSONArray;
import org.json.JSONObject;

import com.duhu.btshelper.BasicdataActivity;

import android.util.Log;

public class ChuanshuServiceImpl implements ChuanshuService {

	@Override
	public List<String> getchuanshulist(int btsid) throws Exception {
		List<String> list =new ArrayList<String>();
		HttpClient client = new DefaultHttpClient();
		String uri = "http://192.168.1.101:8080/BTSHelper/GetChuanshulist.do";
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
		JSONArray array = new JSONArray(result);
		for (int i = 0; i < array.length(); i++) {
			
				list.add(array.getString(i));
			}
			Log.d("传过来的", list.toString());
		

		return list;
	}

	@Override
	public void savechuanshulist(List<String> list, int btsid) throws Exception {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://192.168.1.101:8080/BTSHelper/SaveChuanshu.do";
		HttpPost post = new HttpPost(uri);
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			//object.put("btsinfo", list.get(i));
			array.put(list.get(i));
		}
		object.put("list", array);
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
		String result = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
		if (result.equals("success")) {
			
		}else {
			throw new ServiceRulesException(BasicdataActivity.MSG_SAVE_FAIL);
		}
	}

}