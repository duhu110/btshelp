package com.duhu.btshelper;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.duhu.btshelper.entity.BtsCheckEntity;
import com.duhu.btshelper.service.BtsCheckService;
import com.duhu.btshelper.service.BtsCheckServiceImpl;
import com.duhu.btshelper.service.ServiceRulesException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BTSCheck extends Activity {
	TextView tvPeople, tvshebei_biaoshi, tvshebei_banka, tvshebei_zouxian,
			tvtieta, tvshebei_jietou, tvshebei_gaopin, tvshebei_fengshan,
			tvshebei_gaojing, tvshebei_qingjie, tvjichu_zhanzhi,
			tvchuanshu_banka, tvdonglixitong, tvkongtiao_gaojing,
			tvjichu_tiankui, tvchuanshu_gaojing, tvjichu_shigong,
			tvfangleijiedi, tvhuanjing_wendu, tvchuanshu_jietou,
			tvkongtiao_yunxing, tvhuanjing_shidu, tvhuanjing_qingjie,
			tvhuanjing_xiaofang, tvkongtiao_qingjie, tvhuanjing_qita,
			tvtiankui_xian, tvtiankui_mifeng, tvtiankui_jiedi, tvtiankui_wanqu;
	Spinner spPeople, spshebei_biaoshi, spshebei_banka, spshebei_zouxian,
			spchuanshu_gaojing, spfangleijiedi, spkongtiao_gaojing, sptieta,
			spshebei_jietou, spshebei_gaopin, spshebei_fengshan,
			spkongtiao_yunxing, spchuanshu_jietou, spshebei_gaojing,
			spdonglixitong, spshebei_qingjie, spchuanshu_banka,
			spjichu_zhanzhi, spkongtiao_qingjie, spjichu_tiankui,
			spjichu_shigong, sphuanjing_wendu, sphuanjing_shidu,
			sphuanjing_qingjie, sphuanjing_xiaofang, sphuanjing_qita,
			sptiankui_xian, sptiankui_mifeng, sptiankui_jiedi, sptiankui_wanqu;
	String people, checktime, shebei_biaoshi, shebei_banka, shebei_zouxian,
			chuanshu_gaojing, fangleijiedi, shebei_jietou, shebei_gaopin,
			shebei_fengshan, shebei_gaojing, donglixitong, kongtiao_qingjie,
			shebei_qingjie, chuanshu_jietou, jichu_zhanzhi, jichu_tiankui,
			jichu_shigong, kongtiao_yunxing, huanjing_wendu, huanjing_shidu,
			huanjing_qingjie, tieta, kongtiao_gaojing, huanjing_xiaofang,
			huanjing_qita, tiankui_xian, tiankui_mifeng, tiankui_jiedi,
			tiankui_wanqu, chuanshu_banka;
	private String username;
	Button saveButton, viewButton;
	private int Btsid;
	private static ProgressDialog dialog;
	private static final int FLAG_UPLOAD_SUCCESS = 1;
	private static final int FLAG_UPLOAD_ERROR = 0;
	private final int RC_LAST = 1;
	BtsCheckService btsCheckService = new BtsCheckServiceImpl();
	private IHandler handler = new IHandler(this);

	private void init() {
		viewButton = (Button) findViewById(R.id.bcbtview);
		saveButton = (Button) findViewById(R.id.bcbtsave);
		tvPeople = (TextView) findViewById(R.id.bctvPeople);
		spPeople = (Spinner) findViewById(R.id.bcspPeople);
		tvshebei_biaoshi = (TextView) findViewById(R.id.bctvshebei_biaoshi);
		spshebei_biaoshi = (Spinner) findViewById(R.id.bcspshebei_biaoshi);
		tvshebei_banka = (TextView) findViewById(R.id.bctvshebei_banka);
		spshebei_banka = (Spinner) findViewById(R.id.bcspshebei_banka);
		tvshebei_biaoshi = (TextView) findViewById(R.id.bctvshebei_biaoshi);
		spshebei_biaoshi = (Spinner) findViewById(R.id.bcspshebei_biaoshi);
		tvshebei_zouxian = (TextView) findViewById(R.id.bctvshebei_zouxian);
		spshebei_zouxian = (Spinner) findViewById(R.id.bcspshebei_zouxian);
		tvshebei_jietou = (TextView) findViewById(R.id.bctvshebei_jietou);
		spshebei_jietou = (Spinner) findViewById(R.id.bcspshebei_jietou);
		tvshebei_gaopin = (TextView) findViewById(R.id.bctvshebei_gaopin);
		spshebei_gaopin = (Spinner) findViewById(R.id.bcspshebei_gaopin);
		tvshebei_fengshan = (TextView) findViewById(R.id.bctvshebei_fengshan);
		spshebei_fengshan = (Spinner) findViewById(R.id.bcspshebei_fengshan);
		tvshebei_gaojing = (TextView) findViewById(R.id.bctvshebei_gaojing);
		spshebei_gaojing = (Spinner) findViewById(R.id.bcspshebei_gaojing);
		tvshebei_qingjie = (TextView) findViewById(R.id.bctvshebei_qingjie);
		spshebei_qingjie = (Spinner) findViewById(R.id.bcspshebei_qingjie);
		tvjichu_zhanzhi = (TextView) findViewById(R.id.bctvjichu_zhanzhi);
		spjichu_zhanzhi = (Spinner) findViewById(R.id.bcspjichu_zhanzhi);
		tvjichu_tiankui = (TextView) findViewById(R.id.bctvjichu_tiankui);
		spjichu_tiankui = (Spinner) findViewById(R.id.bcspjichu_tiankui);
		tvjichu_shigong = (TextView) findViewById(R.id.bctvjichu_shigong);
		spjichu_shigong = (Spinner) findViewById(R.id.bcspjichu_shigong);
		tvhuanjing_wendu = (TextView) findViewById(R.id.bctvhuanjing_wendu);
		sphuanjing_wendu = (Spinner) findViewById(R.id.bcsphuanjing_wendu);
		tvhuanjing_shidu = (TextView) findViewById(R.id.bctvhuanjing_shidu);
		sphuanjing_shidu = (Spinner) findViewById(R.id.bcsphuanjing_shidu);
		tvhuanjing_qingjie = (TextView) findViewById(R.id.bctvhuanjing_qingjie);
		sphuanjing_qingjie = (Spinner) findViewById(R.id.bcsphuanjing_qingjie);
		tvhuanjing_xiaofang = (TextView) findViewById(R.id.bctvhuanjing_xiaofang);
		sphuanjing_xiaofang = (Spinner) findViewById(R.id.bcsphuanjing_xiaofang);
		tvhuanjing_qita = (TextView) findViewById(R.id.bctvhuanjing_qita);
		sphuanjing_qita = (Spinner) findViewById(R.id.bcsphuanjing_qita);
		tvtiankui_xian = (TextView) findViewById(R.id.bctvtiankui_xian);
		sptiankui_xian = (Spinner) findViewById(R.id.bcsptiankui_xian);
		tvtiankui_mifeng = (TextView) findViewById(R.id.bctvtiankui_mifeng);
		sptiankui_mifeng = (Spinner) findViewById(R.id.bcsptiankui_mifeng);
		tvtiankui_jiedi = (TextView) findViewById(R.id.bctvtiankui_jiedi);
		sptiankui_jiedi = (Spinner) findViewById(R.id.bcsptiankui_jiedi);
		tvtiankui_wanqu = (TextView) findViewById(R.id.bctvtiankui_wanqu);
		sptiankui_wanqu = (Spinner) findViewById(R.id.bcsptiankui_wanqu);
		tvtieta = (TextView) findViewById(R.id.bctvtieta);
		sptieta = (Spinner) findViewById(R.id.bcsptieta);
		tvkongtiao_gaojing = (TextView) findViewById(R.id.bctvkongtiao_gaojing);
		spkongtiao_gaojing = (Spinner) findViewById(R.id.bcspkongtiao_gaojing);
		tvkongtiao_yunxing = (TextView) findViewById(R.id.bctvkongtiao_yunxing);
		spkongtiao_yunxing = (Spinner) findViewById(R.id.bcspkongtiao_yunxing);
		tvkongtiao_qingjie = (TextView) findViewById(R.id.bctvkongtiao_qingjie);
		spkongtiao_qingjie = (Spinner) findViewById(R.id.bcspkongtiao_qingjie);
		tvdonglixitong = (TextView) findViewById(R.id.bctvdonglixitong);
		spdonglixitong = (Spinner) findViewById(R.id.bcspdonglixitong);
		tvfangleijiedi = (TextView) findViewById(R.id.bctvfangleijiedi);
		spfangleijiedi = (Spinner) findViewById(R.id.bcspfangleijiedi);
		tvchuanshu_banka = (TextView) findViewById(R.id.bctvchuanshu_banka);
		spchuanshu_banka = (Spinner) findViewById(R.id.bcspchuanshu_banka);
		tvchuanshu_gaojing = (TextView) findViewById(R.id.bctvchuanshu_gaojing);
		spchuanshu_gaojing = (Spinner) findViewById(R.id.bcspchuanshu_gaojing);
		tvchuanshu_jietou = (TextView) findViewById(R.id.bctvchuanshu_jietou);
		spchuanshu_jietou = (Spinner) findViewById(R.id.bcspchuanshu_jietou);
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.btscheck);
		init();
		
		Bundle bundle = this.getIntent().getExtras();
		Btsid = bundle.getInt("BTSID");
		username = bundle.getString("username");
		String[] delfultItems = getResources().getStringArray(
				R.array.btscheckdefultarray);
		ArrayAdapter<String> delfultAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, delfultItems);

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());
		checktime = formatter.format(curDate);
		String[] peopleItems;
		switch (username) {
		case "cx":
			peopleItems = getResources().getStringArray(R.array.checkpeoplecx);
			break;
		case "cb":
			peopleItems = getResources().getStringArray(R.array.checkpeoplecb);
			break;
		case "cd":
			peopleItems = getResources().getStringArray(R.array.checkpeoplecd);
			break;
		case "cz":
			peopleItems = getResources().getStringArray(R.array.checkpeoplecz);
			break;
		default:
			peopleItems = getResources().getStringArray(R.array.checkpeople);
			break;
		}
		ArrayAdapter<String> peopelAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, peopleItems);
		// 绑定 Adapter到控件
		spPeople.setAdapter(peopelAdapter);
		spPeople.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				people = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				people = "匿名";
			}
		});

		String[] shebei_biaoshiItems = getResources().getStringArray(
				R.array.shebei_biaoshi);
		ArrayAdapter<String> shebei_biaoshiAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, shebei_biaoshiItems);
		// 绑定 Adapter到控件
		spshebei_biaoshi.setAdapter(shebei_biaoshiAdapter);
		spshebei_biaoshi
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						shebei_biaoshi = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						shebei_biaoshi = "清晰完整";
					}
				});

		String[] shebei_bankaItems = getResources().getStringArray(
				R.array.shebei_banka);
		ArrayAdapter<String> shebei_bankaAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, shebei_bankaItems);
		// 绑定 Adapter到控件
		spshebei_banka.setAdapter(shebei_bankaAdapter);
		spshebei_banka.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				shebei_banka = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				shebei_banka = "清晰完整";
			}
		});

		String[] shebei_zouxianItems = getResources().getStringArray(
				R.array.shebei_zouxian);
		ArrayAdapter<String> shebei_zouxianAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, shebei_zouxianItems);
		// 绑定 Adapter到控件
		spshebei_zouxian.setAdapter(shebei_zouxianAdapter);
		spshebei_zouxian
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						shebei_zouxian = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						shebei_zouxian = "良好";
					}
				});

		spshebei_jietou.setAdapter(delfultAdapter);
		spshebei_jietou.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				shebei_jietou = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				shebei_jietou = "良好";
			}
		});
		spshebei_gaopin.setAdapter(delfultAdapter);
		spshebei_gaopin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				shebei_gaopin = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				shebei_gaopin = "良好";
			}
		});
		spshebei_fengshan.setAdapter(delfultAdapter);
		spshebei_fengshan
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						shebei_fengshan = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						shebei_fengshan = "良好";
					}
				});
		spshebei_gaojing.setAdapter(delfultAdapter);
		spshebei_gaojing
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						shebei_gaojing = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						shebei_gaojing = "良好";
					}
				});
		spshebei_qingjie.setAdapter(delfultAdapter);
		spshebei_qingjie
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						shebei_qingjie = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						shebei_qingjie = "良好";
					}
				});
		spjichu_zhanzhi.setAdapter(delfultAdapter);
		spjichu_zhanzhi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				jichu_zhanzhi = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				jichu_zhanzhi = "良好";
			}
		});
		spjichu_tiankui.setAdapter(delfultAdapter);
		spjichu_tiankui.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				jichu_tiankui = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				jichu_tiankui = "良好";
			}
		});
		spjichu_shigong.setAdapter(delfultAdapter);
		spjichu_shigong.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				jichu_shigong = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				jichu_shigong = "良好";
			}
		});
		sphuanjing_wendu.setAdapter(delfultAdapter);
		sphuanjing_wendu
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						huanjing_wendu = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						huanjing_wendu = "良好";
					}
				});
		sphuanjing_shidu.setAdapter(delfultAdapter);
		sphuanjing_shidu
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						huanjing_shidu = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						huanjing_shidu = "良好";
					}
				});
		sphuanjing_qingjie.setAdapter(delfultAdapter);
		sphuanjing_qingjie
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						huanjing_qingjie = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						huanjing_qingjie = "良好";
					}
				});
		sphuanjing_xiaofang.setAdapter(delfultAdapter);
		sphuanjing_xiaofang
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						huanjing_xiaofang = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						huanjing_xiaofang = "良好";
					}
				});
		sphuanjing_qita.setAdapter(delfultAdapter);
		sphuanjing_qita.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				huanjing_qita = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				huanjing_qita = "良好";
			}
		});
		sptiankui_xian.setAdapter(delfultAdapter);
		sptiankui_xian.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				tiankui_xian = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				tiankui_xian = "良好";
			}
		});
		sptiankui_mifeng.setAdapter(delfultAdapter);
		sptiankui_mifeng
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						tiankui_mifeng = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						tiankui_mifeng = "良好";
					}
				});
		sptiankui_jiedi.setAdapter(delfultAdapter);
		sptiankui_jiedi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				tiankui_jiedi = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				tiankui_jiedi = "良好";
			}
		});
		sptiankui_wanqu.setAdapter(delfultAdapter);
		sptiankui_wanqu.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				tiankui_wanqu = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				tiankui_wanqu = "良好";
			}
		});
		sptieta.setAdapter(delfultAdapter);
		sptieta.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				tieta = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				tieta = "良好";
			}
		});
		spkongtiao_gaojing.setAdapter(delfultAdapter);
		spkongtiao_gaojing
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						kongtiao_gaojing = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						kongtiao_gaojing = "良好";
					}
				});
		spkongtiao_yunxing.setAdapter(delfultAdapter);
		spkongtiao_yunxing
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						kongtiao_yunxing = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						kongtiao_yunxing = "良好";
					}
				});
		spkongtiao_qingjie.setAdapter(delfultAdapter);
		spkongtiao_qingjie
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						kongtiao_qingjie = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						kongtiao_qingjie = "良好";
					}
				});
		spdonglixitong.setAdapter(delfultAdapter);
		spdonglixitong.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				donglixitong = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				donglixitong = "良好";
			}
		});
		spfangleijiedi.setAdapter(delfultAdapter);
		spfangleijiedi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				fangleijiedi = arg0.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				fangleijiedi = "良好";
			}
		});
		spchuanshu_banka.setAdapter(delfultAdapter);
		spchuanshu_banka
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						chuanshu_banka = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						chuanshu_banka = "良好";
					}
				});
		spchuanshu_gaojing.setAdapter(delfultAdapter);
		spchuanshu_gaojing
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						chuanshu_gaojing = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						chuanshu_gaojing = "良好";
					}
				});
		spchuanshu_jietou.setAdapter(delfultAdapter);
		spchuanshu_jietou
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						chuanshu_jietou = arg0.getItemAtPosition(position)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						chuanshu_jietou = "良好";
					}
				});
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final BtsCheckEntity btsCheckEntity = new BtsCheckEntity();
				btsCheckEntity.setBtsid(Btsid);
				btsCheckEntity.setPeople(people);
				btsCheckEntity.setChecktime(checktime);
				btsCheckEntity.setShebei_biaoshi(shebei_biaoshi);
				btsCheckEntity.setShebei_banka(shebei_banka);
				btsCheckEntity.setShebei_zouxian(shebei_zouxian);
				btsCheckEntity.setShebei_jietou(shebei_jietou);
				btsCheckEntity.setShebei_gaopin(shebei_gaopin);
				btsCheckEntity.setShebei_fengshan(shebei_fengshan);
				btsCheckEntity.setShebei_gaojing(shebei_gaojing);
				btsCheckEntity.setShebei_qingjie(shebei_qingjie);
				btsCheckEntity.setJichu_zhanzhi(jichu_zhanzhi);
				btsCheckEntity.setJichu_tiankui(jichu_tiankui);
				btsCheckEntity.setJichu_shigong(jichu_shigong);
				btsCheckEntity.setHuanjing_wendu(huanjing_wendu);
				btsCheckEntity.setHuanjing_shidu(huanjing_shidu);
				btsCheckEntity.setHuanjing_qingjie(huanjing_qingjie);
				btsCheckEntity.setHuanjing_xiaofang(huanjing_xiaofang);
				btsCheckEntity.setHuanjing_qita(huanjing_qita);
				btsCheckEntity.setTiankui_xian(tiankui_xian);
				btsCheckEntity.setTiankui_mifeng(tiankui_mifeng);
				btsCheckEntity.setTiankui_jiedi(tiankui_jiedi);
				btsCheckEntity.setTiankui_wanqu(tiankui_wanqu);
				btsCheckEntity.setTieta(tieta);
				btsCheckEntity.setKongtiao_gaojing(kongtiao_gaojing);
				btsCheckEntity.setKongtiao_yunxing(kongtiao_yunxing);
				btsCheckEntity.setKongtiao_qingjie(kongtiao_qingjie);
				btsCheckEntity.setDonglixitong(donglixitong);
				btsCheckEntity.setFangleijiedi(fangleijiedi);
				btsCheckEntity.setChuanshu_banka(chuanshu_banka);
				btsCheckEntity.setChuanshu_gaojing(chuanshu_gaojing);
				btsCheckEntity.setChuanshu_jietou(chuanshu_jietou);
				if (dialog == null) {
					dialog = new ProgressDialog(BTSCheck.this);
				}

				dialog.setTitle("上传中。。。");
				// dialog.setMessage("正在加载");
				dialog.setCancelable(false);
				dialog.show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							btsCheckService.upload(btsCheckEntity);
							handler.sendEmptyMessage(FLAG_UPLOAD_SUCCESS);
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
									FLAG_UPLOAD_ERROR);
							msg.setData(data);
							handler.sendMessage(msg);
						}
					}
				}).start();
			}
		});
		 viewButton.setOnClickListener(new OnClickListener() {
		
		 @Override
		 public void onClick(View v) {
			 Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("BTSID", Btsid);
				intent.putExtras(bundle);
				intent.setClass(BTSCheck.this, LastBtsCheck.class);
				startActivityForResult(intent, RC_LAST);
		 }
		 });
	}

	private void showtip(String string) {
		Toast.makeText(BTSCheck.this, string, Toast.LENGTH_SHORT).show();
	}

	private static class IHandler extends Handler {
		private final WeakReference<Activity> mActivity;

		public IHandler(BTSCheck activity) {
			mActivity = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			if (dialog != null) {
				dialog.dismiss();
			}
			int flag = msg.what;
			switch (flag) {
			case FLAG_UPLOAD_SUCCESS:
				Log.d("flagsuccess", "1");
				((BTSCheck) mActivity.get()).showtip("保存成功");
				// ((BasicdataActivity) mActivity.get()).settext();
				Log.d("settext", "1");
				break;
			case 0:
				String msgString = (String) msg.getData().getSerializable(
						"LOGIN_ERROR");
				((BTSCheck) mActivity.get()).showtip(msgString);

				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RC_LAST:
			if (resultCode == RESULT_OK) {
				String str = data.getExtras().getString("result");
				
			} else if (resultCode == RESULT_CANCELED) {
		//		Toast.makeText(BTSCheck.this, "不成功",Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	public void onBackPressed() {
		new AlertDialog.Builder(BTSCheck.this).setTitle("确定退出？")
				.setMessage("确定退出吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}
}
