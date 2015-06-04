package com.duhu.btshelper.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import com.duhu.btshelper.BasicdataActivity;
import com.duhu.btshelper.entity.BtsnameEntity;

public class BtsnamelistServiceImpl implements BtsnamelistService {

	@Override
	public List<BtsnameEntity> getbtsnamelist() throws Exception {
		List<BtsnameEntity> btsnamelist = new ArrayList<BtsnameEntity>();

		HttpClient client = new DefaultHttpClient();
		String uri = "http://duhu110.oicp.net/BTSHelper/Btsnamelist.do";
		// HttpPost post = new HttpPost(uri);
		HttpGet get = new HttpGet(uri);

		HttpResponse response = client.execute(get);

		int statuscode = response.getStatusLine().getStatusCode();
		if (statuscode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(BasicdataActivity.MSG_HTTP_FAIL);
		}

		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
		JSONArray array = new JSONArray(result);
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonbtsnamelist = array.getJSONObject(i);
			int btsid = jsonbtsnamelist.getInt("btsid");
			String btsname = jsonbtsnamelist.getString("btsnameString");
			btsnamelist.add(new BtsnameEntity(btsid, btsname));
		}
		/*if (result.equals("success")) {

		} else {
			throw new ServiceRulesException(BtsCheckActivity.MSG_LOGIN_FAIL);
		}*/
		return btsnamelist;
	}
}
