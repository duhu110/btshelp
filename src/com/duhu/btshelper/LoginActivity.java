package com.duhu.btshelper;

import java.lang.ref.WeakReference;

import com.duhu.btshelper.service.ServiceRulesException;
import com.duhu.btshelper.service.UserService;
import com.duhu.btshelper.service.UserServiceImpl;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button btnlogin, btnreset;
	private CheckBox checkBox;
	private SharedPreferences sp = null;
	static String YES = "yes";
	static String NO = "no";
	private String isMemory = "";
	private String FILE = "saveUserNamePwd";
	private EditText etUserName, etUserPasswordEditText;
	private UserService userService = new UserServiceImpl();
	private static final int FLAG_LOGIN_SUCCESS = 1;
	private static final String FLAG_LOGIN_ERROR = "登陆出错";
	public static final String MSG_LOGIN_FAIL = "用戶名或密碼錯誤";
	public static final String MSG_HTTP_FAIL = "連接服務器錯誤";
	private static ProgressDialog dialog;
	private Runnable mRunnable;
	static String name, password;

	private void init() {
		btnlogin = (Button) findViewById(R.id.btnlogin);
		btnreset = (Button) findViewById(R.id.btnreset);
		etUserName = (EditText) findViewById(R.id.etusername);
		etUserPasswordEditText = (EditText) findViewById(R.id.etuserpassword);
		checkBox = (CheckBox) findViewById(R.id.ck_remember);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().hide();
		sp = this.getSharedPreferences(FILE, MODE_PRIVATE);
		this.init();
		isMemory = sp.getString("isMemory", NO);
		// 进入界面时，这个if用来判断SharedPreferences里面name和password有没有数据，有的话则直接打在EditText上面
		if (isMemory.equals(YES)) {
			name = sp.getString("name", "");
			password = sp.getString("password", "");
			etUserName.setText(name);
			etUserPasswordEditText.setText(password);
			checkBox.setChecked(true);
		}
		Editor editor = sp.edit();
		editor.putString(name, etUserName.toString());
		editor.putString(password, etUserPasswordEditText.toString());
		editor.commit();

		btnlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String loginName = etUserName.getText().toString();
				final String loginPassword = etUserPasswordEditText.getText().toString();
				remenber();
				// if (dialog == null) {
				dialog = new ProgressDialog(LoginActivity.this);
				// }

				dialog.setMessage("正在登陸");
				dialog.setCancelable(false);
				dialog.show();
				mRunnable = new Runnable() {

					@Override
					public void run() {
						try {
							userService.userlogin(loginName, loginPassword);
							handler.sendEmptyMessage(FLAG_LOGIN_SUCCESS);
						} catch (ServiceRulesException e) {
							e.printStackTrace();
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putSerializable("LOGIN_ERROR", MSG_LOGIN_FAIL);
							msg.setData(data);
							handler.sendMessage(msg);
						} catch (Exception e) {
							e.printStackTrace();
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putSerializable("LOGIN_ERROR",
									FLAG_LOGIN_ERROR);
							msg.setData(data);
							handler.sendMessage(msg);
						}

					}
				};
				Thread thread = new Thread(mRunnable);
				thread.start();

			}

			private void remenber() {
				if (checkBox.isChecked()) {
					if (sp == null) {
						sp = getSharedPreferences(FILE, MODE_PRIVATE);
					}
					Editor edit = sp.edit();
					edit.putString("name", etUserName.getText().toString());
					edit.putString("password", etUserPasswordEditText.getText()
							.toString());
					edit.putString("isMemory", YES);
					edit.commit();
				} else if (!checkBox.isChecked()) {
					if (sp == null) {
						sp = getSharedPreferences(FILE, MODE_PRIVATE);
					}
					Editor edit = sp.edit();
					edit.putString("isMemory", NO);
					edit.commit();
				}
			}
		});
		btnreset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				etUserName.setText("");
				etUserPasswordEditText.setText("");
			}
		});

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		handler.removeCallbacks(mRunnable);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		handler.removeCallbacks(mRunnable);
	}

	private void showtip(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	private void gotomainactivity() {
		String string = etUserName.getText().toString();
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", string);
		intent.putExtras(bundle);
		intent.setClass(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private static class IHandler extends Handler {
		private final WeakReference<Activity> mActivity;

		public IHandler(LoginActivity activity) {
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
				((LoginActivity) mActivity.get()).gotomainactivity();
				break;
			case 0:
				String msgString = (String) msg.getData().getSerializable(
						"LOGIN_ERROR");
				((LoginActivity) mActivity.get()).showtip(msgString);

				break;

			default:
				break;
			}
		}

	}

	private IHandler handler = new IHandler(this);

}
