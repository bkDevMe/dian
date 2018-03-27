package com.example.dian.verifyview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dian.R;
import com.tuo.customview.VerificationCodeView;

import java.util.Random;


/**
 * Created by dell3020mt-49 on 2018/3/11.
 */

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "VerifyActivity";

    private VerificationCodeView mVerificationCodeView;
    private Toolbar toolbarVerify;
    private TextView comfirmTv;
    private Button comfirmingBtn;
    private TextView comfirmingTv;

    private String mComfirmString;
    private String mNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        mVerificationCodeView = (VerificationCodeView) findViewById(R.id.vcv);
        toolbarVerify = (Toolbar) findViewById(R.id.toolbar_verify);
        setSupportActionBar(toolbarVerify);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        comfirmTv = (TextView) findViewById(R.id.comfirm_tv);
        comfirmTv.setOnClickListener(this);
        comfirmTv.setVisibility(View.GONE);

        comfirmingBtn = findViewById(R.id.comfirming_btn);
        comfirmingBtn.setOnClickListener(this);

        comfirmingTv = findViewById(R.id.comfirming_tv);

        mVerificationCodeView.setInputCompleteListener(new VerificationCodeView.InputCompleteListener() {

            @Override
            public void inputComplete() {
                mNumber = mVerificationCodeView.getInputContent();
                Log.d("Verify", mNumber);
                if (mNumber.length() == 4) {
                    comfirmTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void deleteContent() {
                String number = mVerificationCodeView.getInputContent();
                Log.d("Verify", number);
                if (number.length() < 4) {
                    comfirmTv.setVisibility(View.GONE);
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comfirming_btn:
                mComfirmString = getRandomString();
                Log.d(TAG, mComfirmString);
                comfirmingTv.setText(mComfirmString);
                break;
            case R.id.comfirm_tv:
                Log.d(TAG, "onCLick");
                if (mComfirmString != null && mNumber.equals(mComfirmString)) {
                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }
    }

    private String getRandomString() {
        Random random = new Random();
        StringBuilder append = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int randomInt = random.nextInt(9);
            append.append(randomInt);
        }
        return append.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

