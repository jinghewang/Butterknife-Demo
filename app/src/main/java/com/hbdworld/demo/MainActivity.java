package com.hbdworld.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_ok)
    public Button btnOk;

    @BindView(R.id.btn_cancel)
    public Button btnCancel;

    @BindView(R.id.tv_result)
    public TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_ok,R.id.btn_cancel})
    public void onClick(View view) {
        //this.btnOk = btnOk;
        Button btn = (Button)view;
        Toast.makeText(this, btn.getText(), Toast.LENGTH_SHORT).show();

        tvResult.setText(btn.getText());
    }
}
