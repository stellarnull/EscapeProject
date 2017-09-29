package com.example.stellar.escapeproject_stellar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.stellar.escapeproject_stellar.R.id.img01;

public class CGActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CGActivity";
    private ImageView play_btn;
    private int[] state;
    private ImageView img01;
    private ImageView img02;
    private ImageView img03;
    private ImageView img;
    private TextView txt;
    private Animation myAnimation_Translate01;
    private Animation myAnimation_Translate02;
    private Animation myAnimation_Translate03;
    private Animation myAnimation_Alpha;
    private Bitmap bitmap01;
    private Bitmap bitmap02;
    private Bitmap bitmap03;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cg);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.hide();
        }
        //button
        play_btn = (ImageView) findViewById(R.id.img_ult);
        play_btn.setOnClickListener(this);

        //intent
        Intent intent = getIntent();
        state = intent.getIntArrayExtra("state");

        img = (ImageView) findViewById(R.id.img_ult);
        txt = (TextView) findViewById(R.id.cancel_anim) ;
        img.setOnClickListener(this);
        txt.setOnClickListener(this);

        img01 = (ImageView) findViewById(R.id.img01);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        bitmap01 = BitmapFactory.decodeResource(getResources(), R.drawable.cg1, options);
        img01.setImageBitmap(bitmap01);

        img02 = (ImageView) findViewById(R.id.img02);
        options.inSampleSize = 2;
        bitmap02 = BitmapFactory.decodeResource(getResources(), R.drawable.cg2, options);
        img02.setImageBitmap(bitmap02);

        img03 = (ImageView) findViewById(R.id.img03);
        options.inSampleSize = 2;
        bitmap03 = BitmapFactory.decodeResource(getResources(), R.drawable.cg3, options);
        img03.setImageBitmap(bitmap03);

        //.setVisibility(View.VISIBLE);
        myAnimation_Translate01 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1,
                Animation.RELATIVE_TO_PARENT, 1,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0);
        myAnimation_Translate01.setDuration(5000);
        //myAnimation_Translate01.setZAdjustment(top);
        myAnimation_Translate01.setFillAfter(true);
        myAnimation_Translate01.setInterpolator(AnimationUtils
                .loadInterpolator(CGActivity.this,
                        android.R.anim.linear_interpolator));

        myAnimation_Translate02 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1,
                Animation.RELATIVE_TO_PARENT, 1,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0);
        myAnimation_Translate02.setDuration(5000);
        myAnimation_Translate02.setFillAfter(true);
        myAnimation_Translate02.setStartOffset(2500);
        //myAnimation_Translate02.setZAdjustment(top);
        myAnimation_Translate02.setInterpolator(AnimationUtils
                .loadInterpolator(CGActivity.this,
                        android.R.anim.linear_interpolator));

        myAnimation_Translate03 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1,
                Animation.RELATIVE_TO_PARENT, 1,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0);
        myAnimation_Translate03.setDuration(5000);
        myAnimation_Translate03.setFillAfter(true);
        myAnimation_Translate03.setStartOffset(5000);
        //myAnimation_Translate03.setZAdjustment(top);
        myAnimation_Translate03.setInterpolator(AnimationUtils
                .loadInterpolator(CGActivity.this,
                        android.R.anim.linear_interpolator));

        myAnimation_Alpha = new AlphaAnimation(0, 1);
        myAnimation_Alpha.setDuration(5000);
        myAnimation_Alpha.setFillAfter(true);
        //myAnimation_Alpha.setStartOffset(5000);
        img01.startAnimation(myAnimation_Translate01);
        img02.startAnimation(myAnimation_Translate02);
        img03.startAnimation(myAnimation_Translate03);

        myAnimation_Translate03.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.VISIBLE);
                img.setAnimation(myAnimation_Alpha);
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(CGActivity.this, MainActivity.class);
        switch (id)
        {
            case R.id.img_ult:

                intent.putExtra("state", state);
                Log.d(TAG, "new-state sent from CG to Main: " + Util.itIntArray(state));
                startActivityForResult(intent, 1);
                finish();
                break;
            case R.id.cancel_anim:

                intent.putExtra("state", state);
                Log.d(TAG, "new-state sent from CG to Main: " + Util.itIntArray(state));
                startActivityForResult(intent, 1);
                finish();
                break;
        }
    }

}
