package com.duhu.btshelper;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class CheckBtsActivity extends Activity{
	private TextView tvWelcome,tvnowtimes;
    private int BTSID;
    private Button btconfim;
    private EditText etcheckbtsproblem;
    private String checkbtsproblem,nowtime;
    private float fengduscore,weishscore,biaoqianscore,shebeiscore,problemscore,totalscore;
    private RatingBar rbfengdu,rbshebei,rbweish,rbbiaoqian;
    @SuppressLint("SimpleDateFormat") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bts);
        tvWelcome = (TextView) findViewById(R.id.checkbtsWelcome);
        tvnowtimes = (TextView) findViewById(R.id.checkbtsNOWTIME);
        btconfim = (Button) findViewById(R.id.checkbtsconfirm);
        rbbiaoqian = (RatingBar) findViewById(R.id.ratingBarbiaoqian);
        rbfengdu = (RatingBar) findViewById(R.id.ratingBarfengdu);
        rbshebei = (RatingBar) findViewById(R.id.ratingBarshebei);
        rbweish = (RatingBar) findViewById(R.id.ratingBarweish);
        etcheckbtsproblem = (EditText) findViewById(R.id.checkbtsproblem);

        Bundle bundle = this.getIntent().getExtras();
        BTSID = bundle.getInt("BTSID");
        tvWelcome.setText("欢迎来到" + BTSID + "号站！");

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());
        nowtime = formatter.format(curDate);
        tvnowtimes.setText(nowtime);



        btconfim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weishscore = rbweish.getRating();
                shebeiscore = rbshebei.getRating();
                fengduscore = rbfengdu.getRating();
                biaoqianscore = rbbiaoqian.getRating();
                if (etcheckbtsproblem.getText() != null){
                    problemscore = 20;
                }else {problemscore = 0;}

                totalscore = weishscore*4 + shebeiscore*4
                        + fengduscore*4 + biaoqianscore*4 +problemscore;

                checkbtsproblem = etcheckbtsproblem.getText().toString();

                new AlertDialog.Builder(CheckBtsActivity.this).setTitle("确认")
                        .setItems(new String[]{"巡检时间为"+nowtime,"得分为："+totalscore,
                                "卫生："+weishscore,"封堵："+fengduscore,
                                "设备："+shebeiscore,"标签："+biaoqianscore,
                                "其他问题："+checkbtsproblem},null)
                        .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("确定上传", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast toast = Toast.makeText(CheckBtsActivity.this,
                                        "上传到：" + BTSID + "号站",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }).show();
            }
        });
    }
}
