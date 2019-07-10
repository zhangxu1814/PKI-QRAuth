package com.Vshows.PKI;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.Vshows.PKI.util.StringToPKey;
import com.Vshows.PKI.util.SystemUtil;
import com.Vshows.PKI.util.URLUtil;
import com.Vshows.PKI.util.keyManager;

import java.security.PublicKey;

import io.tomahawkd.pki.api.client.Connecter;
import io.tomahawkd.pki.api.client.util.SecurityFunctions;
import io.tomahawkd.pki.api.client.util.Utils;

public class welcome extends AppCompatActivity {
    private ImageView welcome;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Context context = getBaseContext();
                    Connecter connecter = new Connecter();
                    keyManager manager = new keyManager();
                    String ua = SystemUtil.getSystemModel();
                    String TpubURL = URLUtil.getTpubURL(context);
                    String SpubURL = URLUtil.getSpubURL(context);

                    String Tpub = connecter.getAuthenticationServerPublicKey(TpubURL,ua);
                    Log.d("getTpub",Tpub);
                    PublicKey TPub = StringToPKey.getPublicKey(Tpub);
                    Log.d("TpublicKey",TPub.toString());
                    String Spub = connecter.getServerPublicKey(SpubURL,ua);
                    Log.d("getSpub",Spub);
                    PublicKey SPub = StringToPKey.getPublicKey(Spub);
                    Log.d("SpublicKey",SPub.toString());

//                    String Tpub = Utils.base64Encode(SecurityFunctions.generateKeyPair().getPublic().getEncoded());
//                    String Spub = Utils.base64Encode(SecurityFunctions.generateKeyPair().getPublic().getEncoded());
                    manager.restoreServerKey(context,Tpub,Spub);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        handler.sendEmptyMessageDelayed(0,3000);
        setContentView(R.layout.welcome);
    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
