package com.duhu.btshelper;

import java.lang.ref.WeakReference;

import com.duhu.btshelper.service.SelectName;
import com.duhu.btshelper.service.SelectNameImpl;
import com.duhu.btshelper.service.ServiceRulesException;
import com.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText etBtsid;
	RelativeLayout btGotoerweima;
	private RelativeLayout btGotomenu;
	private ImageButton btIdinput;
	RelativeLayout btGotolist;
	private TextView tvBtsidcheck;
	private String btsid;
	private String btsname;
	private int BTSID = 0;
	private String username;
	private static final String FLAG_LOAD_ERROR = "加载出错";
	private static final int FLAG_SELECT_SUCCESS = 1;
	private final int RC_ERWEIMA = 1, RC_LISTACTIVITY = 2;
	private SelectName selectName = new SelectNameImpl();
	private IHandler handler = new IHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle bundle = this.getIntent().getExtras();
		username = bundle.getString("username");

		etBtsid = (EditText) findViewById(R.id.main_btsid);
		btGotoerweima = (RelativeLayout) findViewById(R.id.main_erweima);
		btGotomenu = (RelativeLayout) findViewById(R.id.main_gotohelper);
		btGotolist = (RelativeLayout) findViewById(R.id.main_sarchlist);
		tvBtsidcheck = (TextView) findViewById(R.id.main_idchek);
		btIdinput = (ImageButton) findViewById(R.id.main_idinputsure);
		btIdinput.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TextUtils.isEmpty(etBtsid.getText())) {
					Toast.makeText(MainActivity.this, "请输入正确站号~",
							Toast.LENGTH_SHORT).show();
				} else {
					btsid = etBtsid.getText().toString();
					BTSID = Integer.parseInt(btsid);
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								btsname = selectName.getname(BTSID);
							
								handler.sendEmptyMessage(FLAG_SELECT_SUCCESS);
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
					tvBtsidcheck.setText("获取到的基站ID是" + BTSID);
				}

			}
		});

		btGotoerweima.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this,
						CaptureActivity.class);
				startActivityForResult(intent, RC_ERWEIMA);
			}
		});

		btGotomenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (BTSID == 0) {
					Toast toast = Toast.makeText(MainActivity.this,
							"没有获取到正确的BTSID，请重试，必须有ID才能进入！", Toast.LENGTH_SHORT);
					toast.show();
				} else {

					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putInt("BTSID", BTSID);
					bundle.putString("username", username);
					bundle.putString("BTSNAME", btsname);
					intent.putExtras(bundle);
					intent.setClass(MainActivity.this, MainWIN.class);
					startActivity(intent);
					// finish();
				}
			}
		});
		btGotolist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent(MainActivity.this,
						BTSNameListActivity.class);
				// intent.putExtra("BTSIDtoLIST",BTSID);
				startActivityForResult(intent, RC_LISTACTIVITY);
				// startActivity(intent);
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RC_ERWEIMA:
			if (resultCode == RESULT_OK) {
				String str = data.getExtras().getString("result");
				if (str == null) {
					BTSID = 0;
				} else {
					BTSID = Integer.parseInt(str);
				}
				tvBtsidcheck.setText("获取到的基站ID是" + BTSID);
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(MainActivity.this, "二维码扫描不成功",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case RC_LISTACTIVITY:
			if (resultCode == RESULT_OK) {
				BTSID = data.getIntExtra("BTSIDfromLIST", 0);
				btsname = data.getStringExtra("BTSNAME");
				if (BTSID == 0) {
					Toast.makeText(MainActivity.this, "ID读取不成功",
							Toast.LENGTH_SHORT).show();
				}
				tvBtsidcheck.setText(BTSID+"号站："+btsname);
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(MainActivity.this, "ID获取不成功", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		default:
			break;
		}
	}
	private void settext() {
		tvBtsidcheck.setText(BTSID+"号站："+btsname);
	}
	private void showtip(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	private static class IHandler extends Handler {
		private final WeakReference<Activity> mActivity;

		public IHandler(MainActivity activity) {
			mActivity = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			
			int flag = msg.what;
			switch (flag) {
			case FLAG_SELECT_SUCCESS:
				((MainActivity) mActivity.get()).settext();
				break;
			case 0:
				String msgString = (String) msg.getData().getSerializable(
						"LOGIN_ERROR");
				((MainActivity) mActivity.get()).showtip(msgString);

				break;

			default:
				break;
			}
		}

	}
}
