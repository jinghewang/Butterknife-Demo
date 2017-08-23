package com.hbdworld.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_ok)
    public Button btnOk;

    @BindView(R.id.btn_cancel)
    public Button btnCancel;

    @BindView(R.id.tv_result)
    public TextView tvResult;

    @BindView(R.id.btn_good)
    Button btnGood;

    @BindView(R.id.btn_bad)
    Button btnBad;
    @BindView(R.id.tx_url)
    EditText txUrl;
    @BindView(R.id.btn_call)
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_ok, R.id.btn_cancel})
    public void onClick(View view) {
        //this.btnOk = btnOk;
        Button btn = (Button) view;
        Toast.makeText(this, btn.getText(), Toast.LENGTH_SHORT).show();

        tvResult.setText(btn.getText());
    }

    Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG,msg.toString());
        }
    };

    @OnClick({R.id.btn_good})
    public void onGoodClick(Button view)  {
        //this.btnOk = btnOk;


        new Thread(new Runnable() {
            @Override
            public void run() {

                Message message = new Message();
                message.obj = "----11-22----";
                mHandler.sendMessage(message);
            }
        }).start();


        //测试下

        //tvResult.setText(result);
    }


    @OnClick({R.id.btn_call})
    public void onCallClick(View view)  {
        //this.btnOk = btnOk;
        Button btn = (Button) view;
        Toast.makeText(this, btn.getText(), Toast.LENGTH_SHORT).show();

        String result = null;
        try {
            get(txUrl.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //tvResult.setText(result);
    }

    OkHttpClient client = new OkHttpClient();

    private String TAG = this.toString();

    private void get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        //Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("okHttp",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.d("okHttp", json);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "code: ");
                        //tvResult.setText(json);
                        responseCallback(json);
                    }
                });
            }
        });
    }

    public void responseCallback(String memo){
        Log.d(TAG, "code: ");
        tvResult.setText(memo);
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
