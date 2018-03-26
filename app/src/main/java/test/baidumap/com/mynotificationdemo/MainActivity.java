package test.baidumap.com.mynotificationdemo;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;

import static test.baidumap.com.mynotificationdemo.R.id.sendNotification;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button takephoto;
    private ImageView picture;
    private Uri uri;
    private static final int TACK_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takephoto = (Button)findViewById(sendNotification);
        picture = (ImageView)findViewById(R.id.picture);
        takephoto.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case sendNotification:
                takePicture();
                break;
            default:
                break;
        }

    }

    private void takePicture(){
        //创建一个文件用来存储拍的照片
        File outPutImage = new File(getExternalCacheDir(),"outpictur.jpg");
        if(outPutImage.exists()){
            outPutImage.delete();
        }
        //判断版本
        if(Build.VERSION.SDK_INT>=24){
            uri = FileProvider.getUriForFile(MainActivity.this,"test.baidumap.com.mynotificationdemo.fileprovider",outPutImage);
        }else {
            uri = Uri.fromFile(outPutImage);
        }
        //启动意图拍照片
        Intent intent   = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,TACK_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TACK_PICTURE:
                if(requestCode==TACK_PICTURE){
                    //如果是刚才拍的那张图片就显示出来
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
