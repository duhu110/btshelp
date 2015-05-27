package com.duhu.btshelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.duhu.btshelper.SlideCutListView.RemoveDirection;
import com.duhu.btshelper.SlideCutListView.RemoveListener;
import com.duhu.btshelper.service.ChuanshuService;
import com.duhu.btshelper.service.ChuanshuServiceImpl;
import com.duhu.btshelper.service.ServiceRulesException;

public class ChuanshuActivity extends Activity implements RemoveListener{
	private SlideCutListView slideCutListView ;
	private ArrayAdapter<String> adapter;
	private IHandler handler = new IHandler(this);
	private List<String> dataSourceList = new ArrayList<String>();
	private TextView tvwelcome;
	private EditText etaddEditText;
	private Button btadd,btsave;
	private int Btsid;
	private static ProgressDialog dialog;
	private static final int FLAG_LOGIN_SUCCESS = 1;
	private static final int FLAG_BAOCUN_SUCCESS =2;
	private static final String FLAG_LOAD_ERROR = "加载出错";
	public static final String MSG_LOAD_FAIL = "加载錯誤";
	public static final String MSG_HTTP_FAIL = "連接服務器錯誤";
	private ChuanshuService chuanshuService = new ChuanshuServiceImpl();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chuanshu);
		tvwelcome = (TextView) findViewById(R.id.chuanshu_Welcome);
		etaddEditText=(EditText) findViewById(R.id.chuanshuedadd);
		btadd=(Button) findViewById(R.id.chuanshubtadd);
		btsave=(Button) findViewById(R.id.chuanshubtsave);
		Bundle bundle = this.getIntent().getExtras();
		Btsid = bundle.getInt("BTSID");
		tvwelcome.setText("欢迎来到" + Btsid + "号站！");
		if (dialog == null) {
			dialog = new ProgressDialog(ChuanshuActivity.this);
		}
		dialog.setTitle("wait");
		dialog.setMessage("正在加载");
		dialog.setCancelable(false);
		dialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					dataSourceList = chuanshuService.getchuanshulist(Btsid);
					handler.sendEmptyMessage(FLAG_LOGIN_SUCCESS);
				} catch (ServiceRulesException e) {
					e.printStackTrace();
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putSerializable("LOGIN_ERROR", e.getMessage());
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putSerializable("LOGIN_ERROR", FLAG_LOAD_ERROR);
					msg.setData(data);
					handler.sendMessage(msg);
				}
			}
		}).start();
	//	init();
		btadd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etaddEditText.getText() != null) {

					dataSourceList.add(etaddEditText.getText().toString());
					etaddEditText.setText(null);
					adapter.notifyDataSetChanged();
				}
			}
		});
		btsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final List<String> list1 = dataSourceList;
				if (dialog == null) {
					dialog = new ProgressDialog(ChuanshuActivity.this);
				}
				dialog.setTitle("wait");
				dialog.setMessage("正在加载");
				dialog.setCancelable(false);
				dialog.show();
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							chuanshuService.savechuanshulist(list1, Btsid);
							handler.sendEmptyMessage(FLAG_BAOCUN_SUCCESS);
						} catch (ServiceRulesException e) {
							e.printStackTrace();
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putSerializable("LOGIN_ERROR", e.getMessage());
							msg.setData(data);
							handler.sendMessage(msg);
						} catch (Exception e) {
							e.printStackTrace();
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putSerializable("LOGIN_ERROR", FLAG_LOAD_ERROR);
							msg.setData(data);
							handler.sendMessage(msg);
						}
					}
				}).start();
			}
		});
	}
	private void showtip(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	private static class IHandler extends Handler {
		private final WeakReference<Activity> mActivity;

		public IHandler(ChuanshuActivity activity) {
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
				((ChuanshuActivity) mActivity.get()).init();
				break;
			case 0:
				String msgString = (String) msg.getData().getSerializable(
						"LOGIN_ERROR");
				 ((ChuanshuActivity) mActivity.get()).showtip(msgString);

				break;
			case FLAG_BAOCUN_SUCCESS:
				((ChuanshuActivity) mActivity.get()).showtip("savesuccess");
				break;
			default:
				break;
			}
		}
	}

	private void init() {
		slideCutListView = (SlideCutListView) findViewById(R.id.slideCutListView);
		slideCutListView.setRemoveListener(this);
		adapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.list_item, dataSourceList);
		slideCutListView.setAdapter(adapter);
		Log.d("shanqian", dataSourceList.toString());
//		slideCutListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Toast.makeText(ChuanshuActivity.this, dataSourceList.get(position), Toast.LENGTH_SHORT).show();
//			}
//		});
	
	}

	
	//滑动删除之后的回调方法
	@Override
	public void removeItem(RemoveDirection direction, int position) {
		adapter.remove(adapter.getItem(position));
		
		
		switch (direction) {
		case RIGHT:
			Toast.makeText(this, "向右删除  "+ position, Toast.LENGTH_SHORT).show();
			Log.d("shanhouyou", dataSourceList.toString());
			break;
		case LEFT:
			Toast.makeText(this, "向左删除  "+ position, Toast.LENGTH_SHORT).show();
			Log.d("shanhouzuo", dataSourceList.toString());
			break;

		default:
			break;
		}
		
	}	


}
