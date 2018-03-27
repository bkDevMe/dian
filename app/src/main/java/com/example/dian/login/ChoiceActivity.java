package com.example.dian.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dian.R;

public class ChoiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button teacherBtn;
    private Button studentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        init();

        teacherBtn.setOnClickListener(this);
        studentBtn.setOnClickListener(this);
    }


    private void init() {
        teacherBtn = (Button) findViewById(R.id.teacher);
        studentBtn = (Button) findViewById(R.id.student);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.teacher:
                Intent teacherIntent = new Intent(ChoiceActivity.this, LoginActivity.class);
                startActivity(teacherIntent);
                finish();
                break;
            case R.id.student:
                Intent studentIntent = new Intent(ChoiceActivity.this, LoginActivity.class);
                startActivity(studentIntent);
                finish();
                break;
        }
    }
}
