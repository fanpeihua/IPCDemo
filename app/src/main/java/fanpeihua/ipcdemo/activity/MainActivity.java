package fanpeihua.ipcdemo.activity;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import fanpeihua.ipcdemo.MyUtils;
import fanpeihua.ipcdemo.R;
import fanpeihua.ipcdemo.User;

public class MainActivity extends AppCompatActivity implements MyIntentService.UpdateUI {

    private Button button;
    private static ImageView mImageView;
    private String url[] = {
            "https://img-blog.csdn.net/20160903083245762",
            "https://img-blog.csdn.net/20160903083252184",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083311972",
            "https://img-blog.csdn.net/20160903083319668",
            "https://img-blog.csdn.net/20160903083326871"
    };

    private static final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mImageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

    @Override
    public void updateUI(Message message) {
        mHandler.sendMessageDelayed(message, message.what * 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initView();

        threadHandle();
    }


    private void threadHandle() {

        mImageView = (ImageView) findViewById(R.id.image_one);
        Intent intent = new Intent(this, MyIntentService.class);
        for (int i = 0; i < 7; i++) {//循环启动任务
            intent.putExtra(MyIntentService.DOWNLOAD_URL, url[i]);
            intent.putExtra(MyIntentService.INDEX_FLAG, i);
            startService(intent);
        }
        MyIntentService.setUpdateUI(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        persistToFile();
    }

    private void initView() {
        button = findViewById(R.id.btn_to_second);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SecondActivity.class);
                User user = new User(0, "jake", true);
                intent.putExtra("extra_user", (Serializable) user);
                startActivity(intent);
            }
        });
    }

    private void persistToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(1, "hello world", false);
                File dir = new File(Environment.getExternalStorageDirectory().getPath() +
                        "/fanpeihua");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File cachedFile = new File(Environment.getExternalStorageDirectory().getPath()
                        + "/fanpeihua/" + "usercache");
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(user);
                    Log.d("hehehe", "persisit user:" + user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.close(objectOutputStream);
                }
            }
        }).start();
    }
}
