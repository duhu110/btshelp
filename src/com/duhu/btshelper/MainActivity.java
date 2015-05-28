package com.duhu.btshelper;

import com.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
	private int BTSID = 0;
	private String username;
	private final int RC_ERWEIMA = 1, RC_LISTACTIVITY = 2;

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
				btsid = etBtsid.getText().toString();
				if (btsid == null) {
					BTSID = 0;
				} else {
					BTSID = Integer.parseInt(btsid);
				}
				tvBtsidcheck.setText("获取到的基站ID是" + BTSID);
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
				if (BTSID == 0) {
					Toast.makeText(MainActivity.this, "ID读取不成功",
							Toast.LENGTH_SHORT).show();
				}
				tvBtsidcheck.setText("获取到的基站ID是" + BTSID);
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(MainActivity.this, "ID获取不成功", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		default:
			break;
		}

	}
}
