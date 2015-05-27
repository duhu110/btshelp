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
        tvWelcome.setText("��ӭ����" + BTSID + "��վ��");

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy��MM��dd�� HH:mm:ss ");
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

                new AlertDialog.Builder(CheckBtsActivity.this).setTitle("ȷ��")
                        .setItems(new String[]{"Ѳ��ʱ��Ϊ"+nowtime,"�÷�Ϊ��"+totalscore,
                                "������"+weishscore,"��£�"+fengduscore,
                                "�豸��"+shebeiscore,"��ǩ��"+biaoqianscore,
                                "�������⣺"+checkbtsproblem},null)
                        .setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("ȷ���ϴ�", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast toast = Toast.makeText(CheckBtsActivity.this,
                                        "�ϴ�����" + BTSID + "��վ",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }).show();
            }
        });
    }
}
