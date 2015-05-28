package com.duhu.btshelper;

import java.lang.ref.WeakReference;
import com.duhu.btshelper.entity.BasicdataEntity;
import com.duhu.btshelper.service.BasicDataService;
import com.duhu.btshelper.service.BasicDataServiceImpl;
import com.duhu.btshelper.service.ServiceRulesException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BasicdataActivity extends Activity {

	private TextView tvWelcome;
	private int BTSID;
	EditText etname, etjindu, etweidu, etfangweijiao0, etfangweijiao1,
			etfangweijiao2, etguagao, ethaiba, etxiaiqngjiao0, etxiaqingjiao1,
			etxiaqingjiao2;
	Button btsave;
	private String[] strings;
	private static ProgressDialog dialog;
	private static final int FLAG_LOGIN_SUCCESS = 1;
	private static final int FLAG_BAOCUN_SUCCESS = 2;
	private static final String FLAG_LOAD_ERROR = "加载出错";
	private static final String FLAG_BAOCUN_ERROR = "保存出错";
	public static final String MSG_SAVE_FAIL = "保存失败";
	public static final String MSG_LOAD_FAIL = "加载失败";
	public static final String MSG_HTTP_FAIL = "連接服務器失败";
	private IHandler handler = new IHandler(this);
	private BasicDataService basicDataService1 = new BasicDataServiceImpl();

	private void init() {
		etname = (EditText) findViewById(R.id.etbasicdatabtsname);
		etjindu = (EditText) findViewById(R.id.etbasicdatajindu);
		etweidu = (EditText) findViewById(R.id.etbasicdataweidu);
		etfangweijiao0 = (EditText) findViewById(R.id.etbasicdatafangweijiao0);
		etfangweijiao1 = (EditText) findViewById(R.id.etbasicdatafangweijiao1);
		etfangweijiao2 = (EditText) findViewById(R.id.etbasicdatafangweijiao2);
		etguagao = (EditText) findViewById(R.id.etbasicdataguagao);
		ethaiba = (EditText) findViewById(R.id.etbasicdatahaiba);
		etxiaiqngjiao0 = (EditText) findViewById(R.id.etbasicdataxiaqingjiao0);
		etxiaqingjiao1 = (EditText) findViewById(R.id.etbasicdataxiaqingjiao1);
		etxiaqingjiao2 = (EditText) findViewById(R.id.etbasicdataxiaqingjiao2);
		btsave = (Button) findViewById(R.id.basicdatabtsave);
//		btreset = (Button) findViewById(R.id.btbasicdatareset);
		tvWelcome = (TextView) findViewById(R.id.basicWelcome);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basicdata);
		this.init();
		
		this.strings = new String[10];
		Log.d("init", "initsucess");
		Bundle bundle = this.getIntent().getExtras();
		BTSID = bundle.getInt("BTSID");
		tvWelcome.setText("欢迎来到" + BTSID + "号站！");
		Log.d("tvwelcome", "setsucess");
		if (dialog == null) {
			dialog = new ProgressDialog(BasicdataActivity.this);
		}

		dialog.setTitle("wait");
		dialog.setMessage("正在加载");
		dialog.setCancelable(false);
		dialog.show();
		Log.d("dialog", "showsuccess");
		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					strings = basicDataService1.chaxun(BTSID);
					handler.sendEmptyMessage(FLAG_LOGIN_SUCCESS);
					Log.d("thread", "1");

				} catch (ServiceRulesException e) {
					e.printStackTrace();
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putSerializable("LOGIN_ERROR", e.getMessage());
					msg.setData(data);
					handler.sendMessage(msg);
					Log.d("thread", "2");
				} catch (Exception e) {
					e.printStackTrace();
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putSerializable("LOGIN_ERROR", FLAG_LOAD_ERROR);
					msg.setData(data);
					handler.sendMessage(msg);
					Log.d("thread", "3");
				}
			}
		}).start();
//		btreset.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				// TODO
//			}
//		});
		btsave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String jindu = etjindu.getText().toString();
				String weidu = etweidu.getText().toString();
				String fangweijiao_0 = etfangweijiao0.getText().toString();
				String fangweijiao_1 = etfangweijiao1.getText().toString();
				String fangweijiao_2 = etfangweijiao2.getText().toString();
				String guagao = etguagao.getText().toString();
				String xiaqingjiao_0 = etxiaiqngjiao0.getText().toString();
				String xiaqingjiao_1 = etxiaiqngjiao0.getText().toString();
				String xiaqingjiao_2 = etxiaiqngjiao0.getText().toString();
				String haiba = ethaiba.getText().toString();
				final BasicdataEntity basicdataEntity = new BasicdataEntity();
				 basicdataEntity.setBtsid(BTSID);
						 basicdataEntity.setJindu(jindu);
						 basicdataEntity.setWeidu(weidu);
						 basicdataEntity.setFangweijiao0(fangweijiao_0);
						 basicdataEntity.setFangweijiao1(fangweijiao_1);
						 basicdataEntity.setFangweijiao2(fangweijiao_2);
						 basicdataEntity.setGuagao(guagao);
						 basicdataEntity.setXiaqingjiao0(xiaqingjiao_0);
						 basicdataEntity.setXiaqingjiao1(xiaqingjiao_1);
						 basicdataEntity.setXiaqingjiao2(xiaqingjiao_2);
						 basicdataEntity.setHaiba(haiba);
						 
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							basicDataService1.baocun(basicdataEntity);
							handler.sendEmptyMessage(FLAG_BAOCUN_SUCCESS);
							Log.d("baocun", "success");
						} catch (ServiceRulesException e) {
							e.printStackTrace();
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putSerializable("baocun_ERROR", e.getMessage());
							msg.setData(data);
							handler.sendMessage(msg);
							Log.d("thread", "4");
						} catch (Exception e) {
							e.printStackTrace();
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putSerializable("LOGIN_ERROR",
									FLAG_BAOCUN_ERROR);
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

	private void settext() {

		etjindu.setText(strings[0]);
		etweidu.setText(strings[1]);
		etfangweijiao0.setText(strings[2]);
		etfangweijiao1.setText(strings[3]);
		etfangweijiao2.setText(strings[4]);
		etguagao.setText(strings[5]);
		etxiaiqngjiao0.setText(strings[6]);
		etxiaqingjiao1.setText(strings[7]);
		etxiaqingjiao2.setText(strings[8]);
		ethaiba.setText(strings[9]);
	}

	private static class IHandler extends Handler {
		private final WeakReference<Activity> mActivity;

		public IHandler(BasicdataActivity activity) {
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
				Log.d("flagsuccess", "1");
				((BasicdataActivity) mActivity.get()).showtip("loadsuccess");
				((BasicdataActivity) mActivity.get()).settext();
				Log.d("settext", "1");
				break;
			case 0:
				String msgString = (String) msg.getData().getSerializable(
						"LOGIN_ERROR");
				((BasicdataActivity) mActivity.get()).showtip(msgString);

				break;
			case FLAG_BAOCUN_SUCCESS:
				((BasicdataActivity) mActivity.get()).showtip("savesuccess");
				break;

			default:
				break;
			}
		}
	}
}
