package com.duhu.btshelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import com.duhu.btshelper.adapter.BtsnameAdapter;
import com.duhu.btshelper.entity.BtsnameEntity;
import com.duhu.btshelper.service.BtsnamelistService;
import com.duhu.btshelper.service.BtsnamelistServiceImpl;
import com.duhu.btshelper.service.ServiceRulesException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class BTSNameListActivity extends Activity {

	private ListView listView;
	private int BTSID;
	private String btsname;
	private List<BtsnameEntity> btsnameList;
	private BtsnameAdapter adapter;
	private static ProgressDialog dialog;
	private static final int FLAG_LOGIN_SUCCESS = 1;
	private static final String FLAG_LOAD_ERROR = "���س���";
	public static final String MSG_LOAD_FAIL = "�����e�`";
	public static final String MSG_HTTP_FAIL = "�B�ӷ������e�`";
	private BtsnamelistService btsnamelistService =  new BtsnamelistServiceImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_btsname_list);

		this.listView = (ListView) findViewById(R.id.btsnamelistView);
		this.btsnameList = new ArrayList<BtsnameEntity>();
		// this.btsnameList.add(new BtsnameEntity(100, "����1"));
		// this.btsnameList.add(new BtsnameEntity(101, "����2"));
		// this.btsnameList.add(new BtsnameEntity(102, "����3"));
		// this.btsnameList.add(new BtsnameEntity(103, "����4"));
		
		if (dialog == null) {
			dialog = new ProgressDialog(BTSNameListActivity.this);
		}
		dialog.setTitle("wait");
		dialog.setMessage("���ڼ���");
		dialog.setCancelable(false);
		//dialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					btsnameList = btsnamelistService.getbtsnamelist();
					handler.sendEmptyMessage(FLAG_LOGIN_SUCCESS);
				}catch(ServiceRulesException e){
					e.printStackTrace();
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putSerializable("LOGIN_ERROR",
							e.getMessage());
					msg.setData(data);
					handler.sendMessage(msg);
				} 
				catch (Exception e) {
					e.printStackTrace();
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putSerializable("LOGIN_ERROR",
							FLAG_LOAD_ERROR);
					msg.setData(data);
					handler.sendMessage(msg);
				}
			}
		}).start();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				BtsnameEntity btsnameEntity = adapter.getItem(arg2);
				BTSID = btsnameEntity.getBtsid();
				btsname = btsnameEntity.getBtsname();
//				ListView list = (ListView)arg0;
//				Map<String,String> item = (Map<String,String>) list.getItemAtPosition(arg2);
//				 BTSID = Integer.parseInt(item.get());
				 if (BTSID != 0){
				 Intent intent = new Intent();
				 intent.putExtra("BTSIDfromLIST",BTSID);
				 intent.putExtra("BTSNAME",btsname);
				 setResult(RESULT_OK,intent);
				 //finishActivity(RESULT_OK);
				 finish();
				 }else {
				 Intent intent = new Intent();
				 setResult(RESULT_CANCELED,intent);
				 finish();
				 }
				 
				// SimpleAdapter adapter = new SimpleAdapter(this,getData(),,
					// new String[]{"name","id"},new int[]{R.id.listitemid,R.id.listitemname});
					// listView.setAdapter(adapter);
					// listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					// @Override
					// public void onItemClick(AdapterView<?> parent, View view, int i, long l)
					// {
					// ListView list = (ListView)parent;
					// @SuppressWarnings("unchecked")
					// Map<String,String> item = (Map<String,String>) list.getItemAtPosition(i);
					// BTSID = Integer.parseInt(item.get("id"));
					// if (BTSID != 0){
					// Intent intent = new Intent();
					// intent.putExtra("BTSIDfromLIST",BTSID);
					// setResult(RESULT_OK,intent);
					// finish();
					// }else {
					// Intent intent = new Intent();
					// setResult(RESULT_CANCELED,intent);
					// finish();
					// }
					// }
					// });
					//
					// }
					
			}
		});
	}

	private static class IHandler extends Handler {
		private final WeakReference<Activity> mActivity;

		public IHandler(BTSNameListActivity activity) {
			mActivity = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			if (dialog != null) {
				dialog.dismiss();
			}
			int flag = msg.what;
			switch (flag) {
			case FLAG_LOGIN_SUCCESS:
				((BTSNameListActivity) mActivity.get()).LoadDataListView();
				break;
			case 0:
				String msgString = (String) msg.getData().getSerializable(
						"LOGIN_ERROR");
				((BTSNameListActivity) mActivity.get()).showtip(msgString);

				break;

			default:
				break;
			}
		}

	}
	
	// private List<Map<String,String>> getData(){
	// List<Map<String,String>> list = new ArrayList<>();
	// Map<String,String> map = new HashMap<>();
	//
	// map.put("name","һ�Ż�վ");
	// map.put("id","1");
	// list.add(map);
	// map = new HashMap<>();
	// map.put("name","���Ż�վ");
	// map.put("id","2");
	// list.add(map);
	// map = new HashMap<>();
	// map.put("name","���Ż�վ");
	// map.put("id","3");
	// list.add(map);
	// map = new HashMap<>();
	// map.put("name","�ĺŻ�վ");
	// map.put("id","4");
	// list.add(map);
	// map = new HashMap<>();
	// map.put("name","��Ż�վ");
	// map.put("id","5");
	// list.add(map);
	// map = new HashMap<>();
	// map.put("name","���Ż�վ");
	// map.put("id","6");
	// list.add(map);
	// map = new HashMap<>();
	// map.put("name","�ߺŻ�վ");
	// map.put("id","7");
	// list.add(map);
	// map = new HashMap<>();
	// map.put("name","�˺Ż�վ");
	// map.put("id","8");
	// list.add(map);
	// map = new HashMap<>();
	// map.put("name","�źŻ�վ");
	// map.put("id","9");
	// list.add(map);
	// map = new HashMap<>();
	// map.put("name","ʮ�Ż�վ");
	// map.put("id","10");
	// list.add(map);
	// return list;
	private void LoadDataListView() {
		this.adapter = new BtsnameAdapter(this, R.layout.btsname_item,
				this.btsnameList);
		this.listView.setAdapter(this.adapter);
	}
	private void showtip(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	private IHandler handler = new IHandler(this);

	
}
