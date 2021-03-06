package com.duhu.btshelper;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainWIN extends TabActivity {

	int BTSID;
	private String username,btsname;
	SlidingMenu slidingMenu;
	Button buttonrelog, buttonrecheck, buttonabout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainwin);
		getActionBar().hide();
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

		Bundle bundle = this.getIntent().getExtras();
		BTSID = bundle.getInt("BTSID");
		username = bundle.getString("username");
		btsname = bundle.getString("BTSNAME");
		Bundle bundle1 = new Bundle();
		bundle1.putInt("BTSID", BTSID);
		bundle1.putString("username", username);
		bundle1.putString("BTSNAME", btsname);

		TabSpec tabSpec1 = tabHost
				.newTabSpec("tab1")
				.setIndicator("数据")
				.setContent(
						new Intent(this, BasicdataActivity.class)
								.putExtras(bundle1));
		tabHost.addTab(tabSpec1);
		TabSpec tabSpec2 = tabHost
				.newTabSpec("tab2")
				.setIndicator("信息")
				.setContent(
						new Intent(this, BTSINFOActivity.class)
								.putExtras(bundle1));
		tabHost.addTab(tabSpec2);
		TabSpec tabSpec3 = tabHost
				.newTabSpec("tab3")
				.setIndicator("巡检")
				.setContent(new Intent(this, BTSCheck.class).putExtras(bundle1));
		tabHost.addTab(tabSpec3);
		TabSpec tabSpec4 = tabHost
				.newTabSpec("tab4")
				.setIndicator("传输")
				.setContent(
						new Intent(this, ChuanshuActivity.class)
								.putExtras(bundle1));
		tabHost.addTab(tabSpec4);

		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setBehindOffsetRes(R.dimen.slidin_menu_offset);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.slidinmenu);
		buttonrelog = (Button) findViewById(R.id.slidingmenuloging);
		buttonabout = (Button) findViewById(R.id.slidingmenuabout);
		buttonrecheck = (Button) findViewById(R.id.slidingmenuidcheck);
		buttonabout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainWIN.this, About.class));
			}
		});
		buttonrecheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("username", username);
				intent.putExtras(bundle);
				intent.setClass(MainWIN.this, MainActivity.class);
				startActivity(intent);
			}
		});
		buttonrelog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainWIN.this, LoginActivity.class));
			}
		});
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(MainWIN.this).setTitle("确定退出？")
				.setMessage("确定退出吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
						System.exit(0);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			slidingMenu.toggle(true);
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			new AlertDialog.Builder(MainWIN.this)
					.setTitle("确定退出？")
					.setMessage("确定退出吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									finish();
									System.exit(0);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();
		}
		return super.onKeyDown(keyCode, event);
	}

}
