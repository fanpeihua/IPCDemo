package fanpeihua.ipcdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import fanpeihua.ipcdemo.MyUtils;
import fanpeihua.ipcdemo.R;
import fanpeihua.ipcdemo.User;

public class SecondActivity extends AppCompatActivity {


    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();
        recoverFromFile();
    }

    private void initView() {
        button = findViewById(R.id.btn_to_third);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        Log.d("hehehe","onResume");
        super.onResume();
        User user = (User)getIntent().getSerializableExtra("extra_user");
        Log.d("hehehe","user:"+user.toString());
    }

    private void recoverFromFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                File cachedFile = new File(Environment.getExternalStorageDirectory().getPath()
                        + "/fanpeihua/" + "usercache");
                if (cachedFile.exists()) {
                    ObjectInputStream objectInputStream = null;
                    try {
                        objectInputStream = new ObjectInputStream(new FileInputStream(cachedFile));
                        user = (User) objectInputStream.readObject();
                        Log.d("hehehe", "recover user:" + user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        MyUtils.close(objectInputStream);
                    }
                }
            }
        }).start();
    }
}
