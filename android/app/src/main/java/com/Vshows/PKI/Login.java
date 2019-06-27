package com.Vshows.PKI;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity implements View.OnClickListener  {

    private EditText username;
    private EditText password;
    private TextView register;
    private TextView forget;
    private ImageButton login;
    private ImageButton showBtn;

    private String name ;
    private String psw ;
    String TAG = Register.class.getCanonicalName();

    private JSONObject jsonObject;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();

    }
    private void initView() {
        username = (EditText)findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);
        register =(TextView)findViewById(R.id.register);
        forget = (TextView) findViewById(R.id.forget);
        forget.setOnClickListener(this);
        login = (ImageButton) findViewById(R.id.loginBtn);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                Intent intent = new Intent(this,Register.class);
                startActivity(intent);
                break;
            case R.id.forget:

                break;
            case R.id.loginBtn:
                name = username.getText().toString();
                psw = password.getText().toString();

                if(TextUtils.isEmpty(name))
                    Toast.makeText(this,"请输入用户名！", Toast.LENGTH_LONG).show();
                else if (TextUtils.isEmpty(psw))
                    Toast.makeText(this,"请输入密码！", Toast.LENGTH_LONG).show();
                else {
                    try {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("username",name);
                        jsonObject.put("password",psw);

                        String url ="http://192.168.43.159/user/login";

                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
//                        RequestBody body = new FormBody.Builder()
//                                .add("username",String.valueOf(name))
//                                .add("password",psw).build();

                        final Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            public void onFailure(Call call, IOException e) {
                                Log.d("error","<<<<e="+e);

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if(response.isSuccessful()) {
                                    String jsonString = response.body().string();

                                    try {
                                        JSONObject jsonObject1 = new JSONObject(jsonString);
                                        switch (jsonObject1.getInt("status")) {
                                            case 0:

                                                break;
                                            case 1:
                                                break;
                                            case -1:
                                                break;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("success","<<<<d="+jsonString);
                                }
                            }
                        });

                        //sendToUserServer(String.valueOf(jsonObject));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "登录成功！", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(this,index.class);
                    //intent2.putExtra("user_info",jsonObject.toString());
                    startActivity(intent2);
                }

                break;
            default:

        }
    }


}
