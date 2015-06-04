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
import android.util.Log;
import com.duhu.btshelper.BasicdataActivity;
import com.duhu.btshelper.entity.BasicdataEntity;

public class BasicDataServiceImpl implements BasicDataService {

	@Override
	public String[] chaxun(int btsid) throws Exception {
		//List<BasicdataEntity> basicdatalist = new ArrayList<BasicdataEntity>();
		String[] strings =new String[10];
		HttpClient client = new DefaultHttpClient();
		String uri = "http://duhu110.oicp.net/BTSHelper/BD.do";
		// HttpPost post = new HttpPost(uri);
		// HttpGet get = new HttpGet(uri);
		// HttpResponse response = client.execute(get);
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
//		[{"guagao":"33",
//			"fangweijiao2":"33",
//			"fangweijiao1":"333",
//			"btsid":"",
//			"fangweijiao0":"333",
//			"jindu":"333",
//			"haiba":"3333",
//			"xiaqingjiao2":"33",
//			"xiaqingjiao0":"33",
//			"xiaqingjiao1":"33",
//			"btsname":"",
//			"weidu":"333","id":0}]
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonbasicdatalist = array.getJSONObject(i);
			String jindu = jsonbasicdatalist.getString("jindu");
			String weidu = jsonbasicdatalist.getString("weidu");
			String fangweijiao0 = jsonbasicdatalist.getString("fangweijiao0");
			String fangweijiao1 = jsonbasicdatalist.getString("fangweijiao1");
			String fangweijiao2 = jsonbasicdatalist.getString("fangweijiao2");
			String guagao = jsonbasicdatalist.getString("guagao");
			String xiaqingjiao0 = jsonbasicdatalist.getString("xiaqingjiao0");
			String xiaqingjiao1 = jsonbasicdatalist.getString("xiaqingjiao1");
			String xiaqingjiao2 = jsonbasicdatalist.getString("xiaqingjiao2");
			String haiba = jsonbasicdatalist.getString("haiba");
			Log.d("解开JSON", jindu);
			Log.d("解开JSON", weidu);
			strings[0]=jindu;
			strings[1]=weidu;
			strings[2]=fangweijiao0;
			strings[3]=fangweijiao1;
			strings[4]=fangweijiao2;
			strings[5]=guagao;
			strings[6]=xiaqingjiao0;
			strings[7]=xiaqingjiao1;
			strings[8]=xiaqingjiao2;
			strings[9]=haiba;
//			basicdatalist.add(new BasicdataEntity(btsid, jindu, weidu,
//					fangweijiao0, fangweijiao1, fangweijiao2, guagao,
//					xiaqingjiao0, xiaqingjiao1, xiaqingjiao2, haiba));
		}

		/*
		 * if (result.equals("success")) {
		 * 
		 * } else { throw new
		 * ServiceRulesException(BtsCheckActivity.MSG_LOGIN_FAIL); }
		 */
		return strings;
	}

	@Override
	public void updata(int btsid, String jindu, String weidu,
			String fangweijiao0, String fangweijiao1, String fangweijiao2,
			String tagao, String xiaqingjiao0, String xiaqingjiao1,
			String xiaqingjiao2, String haiba) throws Exception {
		List<BasicdataEntity> basicdatalist = new ArrayList<BasicdataEntity>();
		BasicdataEntity basicdataEntity = new BasicdataEntity();
		basicdataEntity.setBtsid(btsid);
		basicdataEntity.setJindu(jindu);
		basicdataEntity.setFangweijiao0(fangweijiao0);
		basicdataEntity.setFangweijiao1(fangweijiao1);
		basicdataEntity.setWeidu(weidu);
		basicdataEntity.setFangweijiao2(fangweijiao2);
		basicdataEntity.setGuagao(tagao);
		basicdataEntity.setXiaqingjiao0(xiaqingjiao0);
		basicdataEntity.setXiaqingjiao1(xiaqingjiao1);
		basicdataEntity.setXiaqingjiao2(xiaqingjiao2);
		basicdataEntity.setHaiba(haiba);
		basicdatalist.add(basicdataEntity);

	}

	@Override
	public void baocun(BasicdataEntity basicdataEntity) throws Exception {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://duhu110.oicp.net/BTSHelper/BDSAVE.DO";
		HttpPost post = new HttpPost(uri);
		JSONObject object = new JSONObject();
		object.put("btsid", basicdataEntity.getBtsid());
		object.put("jindu", basicdataEntity.getJindu());
		Log.d("passtoserverjindubaocun", basicdataEntity.getJindu());
		object.put("weidu", basicdataEntity.getWeidu());
		object.put("fangweijiao0", basicdataEntity.getFangweijiao0());
		object.put("fangweijiao1", basicdataEntity.getFangweijiao1());
		object.put("fangweijiao2", basicdataEntity.getFangweijiao2());
		object.put("guagao", basicdataEntity.getGuagao());
		object.put("xiaqingjiao0", basicdataEntity.getXiaqingjiao0());
		object.put("xiaqingjiao1", basicdataEntity.getXiaqingjiao1());
		object.put("xiaqingjiao2", basicdataEntity.getXiaqingjiao2());
		object.put("haiba", basicdataEntity.getHaiba());
		NameValuePair nameValuePair = new BasicNameValuePair("data", object.toString());
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(nameValuePair);
		post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
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
