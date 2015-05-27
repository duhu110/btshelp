package com.duhu.btshelper;
import java.lang.ref.WeakReference;
import com.duhu.btshelper.entity.BtsCheckEntity;
import com.duhu.btshelper.service.BtsCheckService;
import com.duhu.btshelper.service.BtsCheckServiceImpl;
import com.duhu.btshelper.service.ServiceRulesException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LastBtsCheck extends Activity {

	private TextView spPeople, spshebei_biaoshi, spshebei_banka, spshebei_zouxian,
			spchuanshu_gaojing, spfangleijiedi, spkongtiao_gaojing, sptieta,
			spshebei_jietou, spshebei_gaopin, spshebei_fengshan,
			spkongtiao_yunxing, spchuanshu_jietou, spshebei_gaojing,
			spdonglixitong, spshebei_qingjie, spchuanshu_banka,
			spjichu_zhanzhi, spkongtiao_qingjie, spjichu_tiankui,
			spjichu_shigong, sphuanjing_wendu, sphuanjing_shidu,
			sphuanjing_qingjie, sphuanjing_xiaofang, sphuanjing_qita,
			sptiankui_xian, sptiankui_mifeng, sptiankui_jiedi, sptiankui_wanqu;

	private int btsid;
	private Button returnButton;
	BtsCheckEntity btsCheckEntity;
	private static ProgressDialog dialog;
	private static final int FLAG_SAVE_SUCCESS = 1;
	private static final int FLAG_SAVE_ERROR = 0;
	BtsCheckService btsCheckService = new BtsCheckServiceImpl();
	private IHandler handler = new IHandler(this);
	private void init() {
		returnButton = (Button) findViewById(R.id.lsbcbtreturn);
		spPeople = (TextView) findViewById(R.id.lsbctvPeople);
		spshebei_biaoshi = (TextView) findViewById(R.id.lsbctvshebei_biaoshi);
		spshebei_banka = (TextView) findViewById(R.id.lsbctvshebei_banka);
		spshebei_biaoshi = (TextView) findViewById(R.id.lsbctvshebei_biaoshi);
		spshebei_zouxian = (TextView) findViewById(R.id.lsbctvshebei_zouxian);
		spshebei_jietou = (TextView) findViewById(R.id.lsbctvshebei_jietou);
		spshebei_gaopin = (TextView) findViewById(R.id.lsbctvshebei_gaopin);
		spshebei_fengshan = (TextView) findViewById(R.id.lsbctvshebei_fengshan);
		spshebei_gaojing = (TextView) findViewById(R.id.lsbctvshebei_gaojing);
		spshebei_qingjie = (TextView) findViewById(R.id.lsbctvshebei_qingjie);
		spjichu_zhanzhi = (TextView) findViewById(R.id.lsbctvjichu_zhanzhi);
		spjichu_tiankui = (TextView) findViewById(R.id.lsbctvjichu_tiankui);
		spjichu_shigong = (TextView) findViewById(R.id.lsbctvjichu_shigong);
		sphuanjing_wendu = (TextView) findViewById(R.id.lsbctvhuanjing_wendu);
		sphuanjing_shidu = (TextView) findViewById(R.id.lsbctvhuanjing_shidu);
		sphuanjing_qingjie = (TextView) findViewById(R.id.lsbctvhuanjing_qingjie);
		sphuanjing_xiaofang = (TextView) findViewById(R.id.lsbctvhuanjing_xiaofang);
		sphuanjing_qita = (TextView) findViewById(R.id.lsbctvhuanjing_qita);
		sptiankui_xian = (TextView) findViewById(R.id.lsbctvtiankui_xian);
		sptiankui_mifeng = (TextView) findViewById(R.id.lsbctvtiankui_mifeng);
		sptiankui_jiedi = (TextView) findViewById(R.id.lsbctvtiankui_jiedi);
		sptiankui_wanqu = (TextView) findViewById(R.id.lsbctvtiankui_wanqu);
		sptieta = (TextView) findViewById(R.id.lsbctvtieta);
		spkongtiao_gaojing = (TextView) findViewById(R.id.lsbctvkongtiao_gaojing);
		spkongtiao_yunxing = (TextView) findViewById(R.id.lsbctvkongtiao_yunxing);
		spkongtiao_qingjie = (TextView) findViewById(R.id.lsbctvkongtiao_qingjie);
		spdonglixitong = (TextView) findViewById(R.id.lsbctvdonglixitong);
		spfangleijiedi = (TextView) findViewById(R.id.lsbctvfangleijiedi);
		spchuanshu_banka = (TextView) findViewById(R.id.lsbctvchuanshu_banka);
		spchuanshu_gaojing = (TextView) findViewById(R.id.lsbctvchuanshu_gaojing);
		spchuanshu_jietou = (TextView) findViewById(R.id.lsbctvchuanshu_jietou);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lastbtscheck);
		this.init();
		Bundle bundle = this.getIntent().getExtras();
		btsid = bundle.getInt("BTSID");
		btsCheckEntity = new BtsCheckEntity();
		
		if (dialog == null) {
			dialog = new ProgressDialog(LastBtsCheck.this);
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
					btsCheckEntity = btsCheckService.download(btsid);
					handler.sendEmptyMessage(FLAG_SAVE_SUCCESS);
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
					data.putSerializable("LOGIN_ERROR", FLAG_SAVE_ERROR);
					msg.setData(data);
					handler.sendMessage(msg);
					Log.d("thread", "3");
				}
			}
		}).start();
		returnButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (btsCheckEntity != null){
					 Intent intent = new Intent();
					 intent.putExtra("result","loadsuccess");
					 setResult(RESULT_OK,intent);
					 finish();
					 }else {
					 Intent intent = new Intent();
					 setResult(RESULT_CANCELED,intent);
					 finish();
					 }
			}
		});
	}
	private void showtip(String string) {
		Toast.makeText(LastBtsCheck.this, string, Toast.LENGTH_SHORT).show();
	}
	private void settext() {
		spPeople.setText(btsCheckEntity.getPeople());
		spshebei_banka.setText(btsCheckEntity.getShebei_banka());
		spshebei_biaoshi.setText(btsCheckEntity.getShebei_biaoshi());
		spshebei_zouxian.setText(btsCheckEntity.getShebei_zouxian());
		spshebei_jietou.setText(btsCheckEntity.getShebei_jietou());
		spshebei_gaopin.setText(btsCheckEntity.getShebei_gaopin());
		spshebei_fengshan.setText(btsCheckEntity.getShebei_fengshan());
		spshebei_gaojing.setText(btsCheckEntity.getShebei_gaojing());
		spshebei_qingjie.setText(btsCheckEntity.getShebei_qingjie());
		spjichu_zhanzhi.setText(btsCheckEntity.getJichu_zhanzhi());
		spjichu_tiankui.setText(btsCheckEntity.getJichu_tiankui());
		spjichu_shigong.setText(btsCheckEntity.getJichu_shigong());
		sphuanjing_wendu.setText(btsCheckEntity.getHuanjing_wendu());
		sphuanjing_shidu.setText(btsCheckEntity.getHuanjing_shidu());
		sphuanjing_qingjie.setText(btsCheckEntity.getHuanjing_qingjie());
		sphuanjing_xiaofang.setText(btsCheckEntity.getHuanjing_xiaofang());
		sphuanjing_qita.setText(btsCheckEntity.getHuanjing_qita());
		sptiankui_xian.setText(btsCheckEntity.getTiankui_xian());
		sptiankui_mifeng.setText(btsCheckEntity.getTiankui_mifeng());
		sptiankui_jiedi.setText(btsCheckEntity.getTiankui_jiedi());
		sptiankui_wanqu.setText(btsCheckEntity.getTiankui_wanqu());
		sptieta.setText(btsCheckEntity.getTieta());
		spkongtiao_gaojing.setText(btsCheckEntity.getKongtiao_gaojing());
		spkongtiao_yunxing.setText(btsCheckEntity.getKongtiao_yunxing());
		spkongtiao_qingjie.setText(btsCheckEntity.getKongtiao_qingjie());
		spdonglixitong.setText(btsCheckEntity.getDonglixitong());
		spfangleijiedi.setText(btsCheckEntity.getFangleijiedi());
		spchuanshu_banka.setText(btsCheckEntity.getChuanshu_banka());
		spchuanshu_gaojing.setText(btsCheckEntity.getChuanshu_gaojing());
		spchuanshu_jietou.setText(btsCheckEntity.getChuanshu_jietou());
	}
	private static class IHandler extends Handler {
		private final WeakReference<Activity> mActivity;

		public IHandler(LastBtsCheck activity) {
			mActivity = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			if (dialog != null) {
				dialog.dismiss();
			}
			int flag = msg.what;
			switch (flag) {
			case FLAG_SAVE_SUCCESS:
				Log.d("flagsuccess", "1");
				((LastBtsCheck) mActivity.get()).showtip("加载成功");
				((LastBtsCheck) mActivity.get()).settext();
				Log.d("settext", "1");
				break;
			case 0:
//				String msgString = (String) msg.getData().getSerializable(
//						"LOGIN_ERROR");
				((LastBtsCheck) mActivity.get()).showtip("ERROR");

				break;

			default:
				break;
			}
		}
	}

}
