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
import com.duhu.btshelper.BTSINFOActivity;
import com.duhu.btshelper.BasicdataActivity;
import com.duhu.btshelper.entity.BtsCheckEntity;
public class BtsCheckServiceImpl implements BtsCheckService {

	

	@Override
	public void upload(BtsCheckEntity btsCheck) throws Exception {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://192.168.1.101/BTSHelper/BtsCheck.do";
		HttpPost post = new HttpPost(uri);
		JSONObject object = new JSONObject();
		object.put("btsid", btsCheck.getBtsid());
		object.put("setChecktime", btsCheck.getChecktime());
		object.put("setPeople", btsCheck.getPeople());
		object.put("setShebei_banka", btsCheck.getShebei_banka());
		object.put("setShebei_biaoshi", btsCheck.getShebei_biaoshi());
		object.put("setShebei_zouxian", btsCheck.getShebei_zouxian());
		object.put("setShebei_jietou", btsCheck.getShebei_jietou());
		object.put("setShebei_gaopin", btsCheck.getShebei_gaopin());
		object.put("setShebei_fengshan", btsCheck.getShebei_fengshan());
		object.put("setShebei_gaojing", btsCheck.getShebei_gaojing());
		object.put("setShebei_qingjie", btsCheck.getShebei_qingjie());
		object.put("setJichu_zhanzhi", btsCheck.getJichu_zhanzhi());
		object.put("setJichu_tiankui", btsCheck.getJichu_tiankui());
		object.put("setJichu_shigong", btsCheck.getJichu_shigong());
		object.put("setHuanjing_wendu", btsCheck.getHuanjing_wendu());
		object.put("setHuanjing_shidu", btsCheck.getHuanjing_shidu());
		object.put("setHuanjing_qingjie", btsCheck.getHuanjing_qingjie());
		object.put("setHuanjing_xiaofang", btsCheck.getHuanjing_xiaofang());
		object.put("setHuanjing_qita", btsCheck.getHuanjing_qita());
		object.put("setTiankui_xian", btsCheck.getTiankui_xian());
		object.put("setTiankui_mifeng", btsCheck.getTiankui_mifeng());
		object.put("setTiankui_jiedi", btsCheck.getTiankui_jiedi());
		object.put("setTiankui_wanqu", btsCheck.getTiankui_wanqu());
		object.put("setTieta", btsCheck.getTieta());
		object.put("setKongtiao_gaojing", btsCheck.getKongtiao_gaojing());
		object.put("setKongtiao_yunxing", btsCheck.getKongtiao_yunxing());
		object.put("setKongtiao_qingjie", btsCheck.getKongtiao_qingjie());
		object.put("setDonglixitong", btsCheck.getDonglixitong());
		object.put("setFangleijiedi", btsCheck.getFangleijiedi());
		object.put("setChuanshu_banka", btsCheck.getChuanshu_banka());
		object.put("setChuanshu_gaojing", btsCheck.getChuanshu_gaojing());
		object.put("setChuanshu_jietou", btsCheck.getChuanshu_jietou());
		NameValuePair nameValuePair = new BasicNameValuePair("data", object.toString());
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(nameValuePair);
		post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
		HttpResponse response = client.execute(post);
		int statuscode = response.getStatusLine().getStatusCode();
		if (statuscode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(BTSINFOActivity.MSG_HTTP_FAIL);
		}
		String result = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
		if (result.equals("success")) {
			
		}else {
			throw new ServiceRulesException(BasicdataActivity.MSG_SAVE_FAIL);
		}

	}

	
	@Override
	public BtsCheckEntity download(int btsid) throws Exception {
		BtsCheckEntity btsCheck = new BtsCheckEntity();
		HttpClient client = new DefaultHttpClient();
		String uri = "http://192.168.1.101/BTSHelper/BtsCheckSelect";
		HttpPost post = new HttpPost(uri);
		JSONObject object = new JSONObject();
		object.put("btsid", btsid);
		NameValuePair nameValuePair = new BasicNameValuePair("data", object.toString());
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(nameValuePair);
		post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
		HttpResponse response = client.execute(post);
		int statuscode = response.getStatusLine().getStatusCode();
		if (statuscode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(BTSINFOActivity.MSG_HTTP_FAIL);
		}
		String result = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
		JSONObject object2 = new JSONObject(result);
		btsCheck.setChecktime(object2.getString("getChecktime"));
		btsCheck.setPeople(object2.getString("getPeople"));
		btsCheck.setShebei_banka(object2.getString("getShebei_banka"));
		btsCheck.setShebei_biaoshi(object2.getString("getShebei_biaoshi"));
		btsCheck.setShebei_zouxian(object2.getString("getShebei_zouxian"));
		btsCheck.setShebei_jietou(object2.getString("getShebei_jietou"));
		btsCheck.setShebei_gaopin(object2.getString("getShebei_gaopin"));
		btsCheck.setShebei_fengshan(object2.getString("getShebei_fengshan"));
		btsCheck.setShebei_gaojing(object2.getString("getShebei_gaojing"));
		btsCheck.setShebei_qingjie(object2.getString("getShebei_qingjie"));
		btsCheck.setJichu_zhanzhi(object2.getString("getJichu_zhanzhi"));
		btsCheck.setJichu_tiankui(object2.getString("getJichu_tiankui"));
		btsCheck.setJichu_shigong(object2.getString("getJichu_shigong"));
		btsCheck.setHuanjing_wendu(object2.getString("getHuanjing_wendu"));
		btsCheck.setHuanjing_shidu(object2.getString("getHuanjing_shidu"));
		btsCheck.setHuanjing_qingjie(object2.getString("getHuanjing_qingjie"));
		btsCheck.setHuanjing_xiaofang(object2.getString("getHuanjing_xiaofang"));
		btsCheck.setHuanjing_qita(object2.getString("getHuanjing_qita"));
		btsCheck.setTiankui_xian(object2.getString("getTiankui_xian"));
		btsCheck.setTiankui_mifeng(object2.getString("getTiankui_mifeng"));
		btsCheck.setTiankui_jiedi(object2.getString("getTiankui_jiedi"));
		btsCheck.setTiankui_wanqu(object2.getString("getTiankui_wanqu"));
		btsCheck.setTieta(object2.getString("getTieta"));
		btsCheck.setKongtiao_gaojing(object2.getString("getKongtiao_gaojing"));
		btsCheck.setKongtiao_yunxing(object2.getString("getKongtiao_yunxing"));
		btsCheck.setKongtiao_qingjie(object2.getString("getKongtiao_qingjie"));
		btsCheck.setDonglixitong(object2.getString("getDonglixitong"));
		btsCheck.setFangleijiedi(object2.getString("getFangleijiedi"));
		btsCheck.setChuanshu_banka(object2.getString("getChuanshu_banka"));
		btsCheck.setChuanshu_gaojing(object2.getString("getChuanshu_gaojing"));
		btsCheck.setChuanshu_jietou(object2.getString("getChuanshu_jietou"));
		return btsCheck;
		
	}

}
