package com.example.multhreaddown;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.download.DownloadProgressListener;
import net.download.FileDownloader;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private EditText et_url;
    private Button btn_down;
    private ProgressBar progressBar;
    private TextView tv_percentage;

    //handler 对象的作用是  用于往创建handler对象的线程所绑定的消息队列中发送消息
    private Handler handler = new UIHandler();

    private final class UIHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    int size = msg.getData().getInt("size");
                    progressBar.setProgress(size);//设置进度条当前刻度
                    float num = (float) progressBar.getProgress() / (float) progressBar.getMax();
                    int result = (int)(num*100);
                    tv_percentage.setText(result+"%");
                    if (progressBar.getProgress() == progressBar.getMax()){
                        Toast.makeText(getApplicationContext(),"下载成功",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(),"下载失败",Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_url = (EditText) findViewById(R.id.et_url);
        btn_down = (Button) findViewById(R.id.btn_down);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_percentage = (TextView) findViewById(R.id.tv_percentage);

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = et_url.getText().toString();
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/FileDownload");
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    down(url, dir);
                }
            }


        });
    }

    public void down(String url,File dir) {
        DownTask downTask = new DownTask(url,dir);
        new Thread(downTask).start();

    }
    private class DownTask implements Runnable{

        private String url;
        private File dir;
        public DownTask(String url,File dir){
            this.url = url;
            this.dir = dir;
        }

        @Override
        public void run() {
            try {
                FileDownloader loader = new FileDownloader(getApplicationContext(),url,dir,3);
                progressBar.setMax(loader.getFileSize());//设置进度条最大刻度
                loader.download(new DownloadProgressListener() {
                    @Override
                    // UI控件的重绘 是由主线程负责的 如果在子线程中更新UI控件的值，更新后的值不会重绘到UI控件上
                    // 必须在主线程中更新UI空间的值
                    public void onDownloadSize(int size) {

                        Message msg  = new Message();
                        msg.what = 1; //设置消息标识
                        msg.getData().putInt("size",size);
                        handler.sendMessage(msg);

                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
                handler.sendMessage(handler.obtainMessage(-1));
            }
        }
    }

}
