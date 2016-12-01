package com.yang.phoneguard.activitys;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.yang.phoneguard.R;
import com.yang.phoneguard.utils.StreamUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private TextView mTv_version_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv_version_code = (TextView) findViewById(R.id.tv_version_code);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            mTv_version_code.setText("version:" + packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        checkVersion();
    }

    /**
     * 检测服务器是否存在新版本
     */
    private void checkVersion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(getResources().getString(R.string.version_url));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(8000);
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        InputStream is = conn.getInputStream();
                        String result = StreamUtil.readStream(is);
                        if (TextUtils.isEmpty(result)) {
                            //流转换失败
                            Log.e(TAG, "流转换失败");
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String version = jsonObject.getString("version");
                                String code = jsonObject.getString("code");
                                String version_url = jsonObject.getString("version_url");
                                String desc = jsonObject.getString("desc");

                                Log.i(TAG, "version: " + version);
                                Log.i(TAG, "code: " + code);
                                Log.i(TAG, "version_url: " + version_url);
                                Log.i(TAG, "desc: " + desc);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "json解析失败");
                            }
                        }
                    } else {
                        Toast.makeText(SplashActivity.this, "failed to get version", Toast.LENGTH_SHORT).show();
                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "连接服务器失败");
                }
            }
        }.start();
    }
}
