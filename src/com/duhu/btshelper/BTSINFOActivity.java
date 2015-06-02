package com.duhu.btshelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import com.duhu.btshelper.SlideCutListView.RemoveDirection;
import com.duhu.btshelper.service.BtsinfoService;
import com.duhu.btshelper.service.BtsinfoServiceImpl;
import com.duhu.btshelper.service.ServiceRulesException;
import com.duhu.btshelper.SlideCutListView.RemoveListener;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BTSINFOActivity extends Activity implements RemoveListener {
	private SlideCutListView slideCutListView;
	private ArrayAdapter<String> adapter;
	private IHandler handler = new IHandler(this);
//	private ListView listView;
//	private TextView tvwelcome;
	private EditText etaddEditText;
	private Button  btsave;
	private ImageButton btadd;
	private int Btsid;
	private static ProgressDialog dialog;
	private static final int FLAG_LOAD_SUCCESS = 1;
	private static final int FLAG_BAOCUN_SUCCESS =2;
	private static final String FLAG_LOAD_ERROR = "加载出错";
	public static final String MSG_LOAD_FAIL = "加载錯誤";
	public static final String MSG_HTTP_FAIL = "連接服務器錯誤";
	private List<String> list = new ArrayList<String>();
	private BtsinfoService btsinfoService = new BtsinfoServiceImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bts_info);
//		tvwelcome = (TextView) findViewById(R.id.btsinfo_Welcome);
		etaddEditText = (EditText) findViewById(R.id.btsinfoedadd);
		btadd = (ImageButton) findViewById(R.id.btsinfobtadd);
		btsave = (Button) findViewById(R.id.btsinfobtsave);
//		listView = (ListView) findViewById(R.id.btsinfo_INFO);
		
		Bundle bundle = this.getIntent().getExtras();
		Btsid = bundle.getInt("BTSID");
//		tvwelcome.setText("欢迎来到" + Btsid + "号站！");
//		if (dialog == null) {
//			dialog = new ProgressDialog(BTSINFOActivity.this);
//		}
//		dialog.setTitle("wait");
//		dialog.setMessage("正在加载");
//		dialog.setCancelable(false);
//		dialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					list = btsinfoService.getbtsinfolist(Btsid);
					handler.sendEmptyMessage(FLAG_LOAD_SUCCESS);
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
		btadd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etaddEditText.getText() != null) {

					list.add(etaddEditText.getText().toString());
					etaddEditText.setText(null);
					adapter.notifyDataSetChanged();
				}
			}
		});
		btsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final List<String> list1 = list;
//				if (dialog == null) {
//					dialog = new ProgressDialog(BTSINFOActivity.this);
//				}
//				dialog.setTitle("wait");
//				dialog.setMessage("正在加载");
//				dialog.setCancelable(false);
//				dialog.show();
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							btsinfoService.savebtsinfolist(list1, Btsid);
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
	private void init() {
		slideCutListView = (SlideCutListView) findViewById(R.id.btsinfo_INFO);
		slideCutListView.setRemoveListener(this);
		adapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.list_item, list);
		slideCutListView.setAdapter(adapter);
		Log.d("shanqian", list.toString());
		}
	public void removeItem(RemoveDirection direction, int position) {
		adapter.remove(adapter.getItem(position));
		
		
		switch (direction) {
		case RIGHT:
		//	Toast.makeText(this, "向右删除  "+ position, Toast.LENGTH_SHORT).show();
			Log.d("shanhouyou", list.toString());
			break;
		case LEFT:
		//	Toast.makeText(this, "向左删除  "+ position, Toast.LENGTH_SHORT).show();
			Log.d("shanhouzuo", list.toString());
			break;

		default:
			break;
		}
		
	}	
	private void showtip(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

//	private void displaylist() {
//		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
//				list);
//		listView.setAdapter(adapter);
//	}

	private static class IHandler extends Handler {
		private final WeakReference<Activity> mActivity;

		public IHandler(BTSINFOActivity activity) {
			mActivity = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			if (dialog != null) {
				dialog.dismiss();
			}
			int flag = msg.what;
			switch (flag) {
			case FLAG_LOAD_SUCCESS:
				((BTSINFOActivity) mActivity.get()).init();
				break;
			case 0:
				String msgString = (String) msg.getData().getSerializable(
						"LOGIN_ERROR");
				 ((BTSINFOActivity) mActivity.get()).showtip(msgString);

				break;
			case FLAG_BAOCUN_SUCCESS:
				((BTSINFOActivity) mActivity.get()).showtip("savesuccess");
				break;
			default:
				break;
			}
		}
	}
}
