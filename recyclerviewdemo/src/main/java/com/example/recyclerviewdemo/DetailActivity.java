package com.example.recyclerviewdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

public class DetailActivity extends Activity {

    private ImageView imageView;
    private ImageView drawImg;
    private String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = (ImageView) findViewById(R.id.img);
        drawImg = (ImageView) findViewById(R.id.draw_img);

        text =  getIntent().getCharSequenceExtra("itemData").toString();
        imageView.setImageBitmap(getBitmap(0));
        drawImg.setImageBitmap(getBitmap(1));
        Log.i("text:",text);
    }



    private Bitmap getBitmap(int drawId) {
        //获取屏幕尺寸
        WindowManager windowManager = this.getWindowManager();
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        switch (drawId){
            case 0:
                // 创建渲染位图
                Bitmap bitmap = Bitmap.createBitmap(300,300, Bitmap.Config.ARGB_8888);
                //创建画布
                Canvas canvas = new Canvas(bitmap);
                //创建绘制矩形的画笔
                Paint rectPaint = new Paint();
                //设置画笔颜色
                rectPaint.setColor(Color.YELLOW);
                rectPaint.setColor(Color.YELLOW);
                rectPaint.setColor(Color.YELLOW);
                //绘制矩形
                canvas.drawRect(0,0,300,300,rectPaint);


                //创建文字画笔
                Paint textPaint = new Paint();
                textPaint.setColor(Color.BLUE);
                textPaint.setTextSize(50);
                textPaint.setTextAlign(Paint.Align.CENTER);
                //设置文字样式  加粗|倾斜
                textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                //绘制文字
                canvas.drawText(text,150,160,textPaint);
                return bitmap;

            case 1:
                // 绘制点和线
                Bitmap bitmap1 = Bitmap.createBitmap(width ,height-100, Bitmap.Config.ARGB_8888);
                //创建画布  设置颜色为  红色
                Canvas canvas1 = new Canvas(bitmap1);
                canvas1.drawColor(Color.RED);
                //画点的笔
                Paint pPaint = new Paint();
                pPaint.setColor(Color.WHITE);
                pPaint.setStrokeWidth(10);
                //数组存储的是 3 个点坐标
                float[] potsP = new float[]{
                        100,100,
                        200,200,
                        300,300
                };
                // 将点绘制到画布上
                canvas1.drawPoints(potsP,pPaint);

                //划线的笔
                Paint lPaint = new Paint();
                lPaint.setStrokeWidth(3);
                lPaint.setColor(Color.BLACK);

                // 存储线上的 端点()
                float[] potsL = new float[]{
                    0,110,width,110,
                    width,110,width,width+110,
                    width,width+110,0,width+110,
                    0,width+110,0,110
                };


                //将线绘制到画布上
                canvas1.drawLines(potsL,lPaint);
                return  bitmap1;

            default:return null;

        }


    }
}
