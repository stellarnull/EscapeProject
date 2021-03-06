package com.example.stellar.escapeproject_stellar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.HashMap;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button back_btn;
    private ImageView leftUpWin;
    private ImageView middleUpWin;
    private ImageView rightUpWin;
    private ImageView leftDownWin;
    private ImageView middleDownWin;
    private ImageView rightDownWin;
    private Bitmap leftUpBitmap;
    private Bitmap middleUpBitmap;
    private Bitmap rightUpBitmap;
    private Bitmap leftDownBitmap;
    private Bitmap middleDownBitmap;
    private Bitmap rightDownBitmap;
    private static final String TAG = "MainActivity";

    public static SoundPool pool;
    public static HashMap<String, int[]> searchTable;

    private int leftUpImgPath, leftDownImgPath, rightUpImgPath, rightDownImgPath, middleUpImgPath, middleDownImgPath;

    private int []state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //setContentView(R);
        back_btn = (Button) findViewById(R.id.back2HelloBtn);

        //soundpool init
        pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 2, 2, 0, 0, 1);
            }
        });
        //init state
        Intent intent = getIntent();
        state = intent.getIntArrayExtra("state");
        /*
        state = new int[5];
        state[0] = 1;
        state[1] = 1;
        state[2] = 0;
        state[3] = 0;
        state[4] = 0;
        */
        // init image for scan windows
        searchTableInit();
        imgInit();
        leftUpWin.setOnClickListener(this);
        leftDownWin.setOnClickListener(this);
        rightUpWin.setOnClickListener(this);
        rightDownWin.setOnClickListener(this);
        middleUpWin.setOnClickListener(this);
        middleDownWin.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        //init searchTable

    }

    private void imgInit() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        //test init
        String key = "" + state[2] + "_" + state[3] + "_" + state[4];
        //Log.v("main", key);
        int value[] = MainActivity.searchTable.get(key);
        leftUpImgPath = value[1];
        middleUpImgPath = value[2];
        rightUpImgPath = value[3];
        leftDownImgPath = value[4];
        middleDownImgPath = value[5];
        rightDownImgPath = value[6];

        /*
        leftUpImgPath = R.drawable.p1_initial;
        middleUpImgPath = R.drawable.p2_initial;
        rightUpImgPath = R.drawable.p3_initial;
        leftDownImgPath = R.drawable.p4_initial;
        middleDownImgPath = R.drawable.p5_initial;
        rightDownImgPath = R.drawable.p6_initial;
        */
        leftUpBitmap = BitmapFactory.decodeResource(getResources(), leftUpImgPath, options);
        leftUpWin = (ImageView) findViewById(R.id.leftUpImg);
        leftUpWin.setImageDrawable(new RoundImageDrawable(leftUpBitmap));

        rightUpWin = (ImageView) findViewById(R.id.rightUpImg);
        rightUpBitmap = BitmapFactory.decodeResource(getResources(), rightUpImgPath, options);
        rightUpWin.setImageDrawable(new RoundImageDrawable(rightUpBitmap));

        leftDownWin = (ImageView) findViewById(R.id.leftDownImg);
        leftDownBitmap = BitmapFactory.decodeResource(getResources(), leftDownImgPath, options);
        leftDownWin.setImageDrawable(new RoundImageDrawable(leftDownBitmap));

        rightDownWin = (ImageView) findViewById(R.id.rightDownImg);
        rightDownBitmap = BitmapFactory.decodeResource(getResources(), rightDownImgPath, options);
        rightDownWin.setImageDrawable(new RoundImageDrawable(rightDownBitmap));

        middleUpWin = (ImageView) findViewById(R.id.middleUpImg);
        middleUpBitmap = BitmapFactory.decodeResource(getResources(), middleUpImgPath, options);
        middleUpWin.setImageDrawable(new RoundImageDrawable(middleUpBitmap));

        middleDownWin = (ImageView) findViewById(R.id.middleDownImg);
        middleDownBitmap = BitmapFactory.decodeResource(getResources(), middleDownImgPath, options);
        middleDownWin.setImageDrawable(new RoundImageDrawable(middleDownBitmap));

    }

    private void imgSetting() {
        String key = "" + state[2] + "_" + state[3] + "_" + state[4];
        //Log.v("main", key);
        int value[] = MainActivity.searchTable.get(key);
        if (leftUpBitmap != null && !leftUpBitmap.isRecycled()) {
            leftUpBitmap.recycle();
            leftUpBitmap = null;
        }
        if (middleUpBitmap != null && !middleUpBitmap.isRecycled()) {
            middleUpBitmap.recycle();
            middleUpBitmap = null;
        }
        if (rightUpBitmap != null && !rightUpBitmap.isRecycled()) {
            rightUpBitmap.recycle();
            rightUpBitmap = null;
        }
        if (leftDownBitmap != null && !leftDownBitmap.isRecycled()) {
            leftDownBitmap.recycle();
            leftDownBitmap = null;
        }
        if (middleDownBitmap != null && !middleDownBitmap.isRecycled()) {
            middleDownBitmap.recycle();
            middleDownBitmap = null;
        }
        if (rightDownBitmap != null && !rightDownBitmap.isRecycled()) {
            rightDownBitmap.recycle();
            rightDownBitmap = null;
        }

        System.gc();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;

        leftUpBitmap = BitmapFactory.decodeResource(getResources(), value[1], options);
        leftUpWin.setImageDrawable(new RoundImageDrawable(leftUpBitmap));

        rightUpBitmap = BitmapFactory.decodeResource(getResources(), value[3], options);
        rightUpWin.setImageDrawable(new RoundImageDrawable(rightUpBitmap));

        leftDownBitmap = BitmapFactory.decodeResource(getResources(), value[4], options);
        leftDownWin.setImageDrawable(new RoundImageDrawable(leftDownBitmap));

        rightDownBitmap = BitmapFactory.decodeResource(getResources(), value[6], options);
        rightDownWin.setImageDrawable(new RoundImageDrawable(rightDownBitmap));

        middleUpBitmap = BitmapFactory.decodeResource(getResources(), value[2], options);
        middleUpWin.setImageDrawable(new RoundImageDrawable(middleUpBitmap));

        middleDownBitmap = BitmapFactory.decodeResource(getResources(), value[5], options);
        middleDownWin.setImageDrawable(new RoundImageDrawable(middleDownBitmap));

        //value: state of the six rooms: drawable
        //value[0]: audio to be play
        leftUpImgPath = value[1];
        middleUpImgPath = value[2];
        rightUpImgPath = value[3];
        leftDownImgPath = value[4];
        middleDownImgPath = value[5];
        rightDownImgPath = value[6];

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HelloActivity.class);
        intent.putExtra("state", state);
        Log.d(TAG, "state sent from Main to Pause: " + Util.itIntArray(state));
        startActivityForResult(intent, 1);
    }
    @Override
    public void onClick(View v) {


        int id = v.getId();

        if (id == R.id.back2HelloBtn) {
            Intent intent = new Intent(this, PauseActivity.class);
            intent.putExtra("state", state);
            Log.d(TAG, "state sent from Main to Pause: " + Util.itIntArray(state));
            startActivityForResult(intent, 1);
            //setResult(RESULT_OK, intent);
            //finish();
        }
        else
        {
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("state", state);
            if (id == R.id.leftUpImg) {
                intent.putExtra("mainImgPath", leftUpImgPath);
                intent.putExtra("clickedRoom", 1);
                Log.d(TAG, "state sent from Main to Play: " + Util.itIntArray(state));
                startActivityForResult(intent, 1);
            }
            else if (id == R.id.middleUpImg) {
                intent.putExtra("mainImgPath", middleUpImgPath);
                intent.putExtra("clickedRoom", 2);
                startActivityForResult(intent, 1);
            }
            else if (id == R.id.rightUpImg) {
                intent.putExtra("mainImgPath", rightUpImgPath);
                intent.putExtra("clickedRoom", 3);
                startActivityForResult(intent, 1);
            }
            else if (id == R.id.leftDownImg) {
                intent.putExtra("mainImgPath", leftDownImgPath);
                intent.putExtra("clickedRoom", 4);
                startActivityForResult(intent, 1);
            }
            else if (id == R.id.middleDownImg) {
                intent.putExtra("mainImgPath", middleDownImgPath);
                intent.putExtra("clickedRoom", 5);
                startActivityForResult(intent, 1);
            }
            else if (id == R.id.rightDownImg) {
                intent.putExtra("mainImgPath", rightDownImgPath);
                intent.putExtra("clickedRoom", 6);

                startActivityForResult(intent, 1);
            }
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 1) {
                state = data.getIntArrayExtra("state");
                Log.d(TAG, "state got from Play to Main: " + Util.itIntArray(state));
                //update images
                imgSetting();
            }
        }
    }

    private void searchTableInit() {
        searchTable = new HashMap<String, int[]>();
        searchTable.put("0_0_0", new int[]{0, R.drawable.p1_initial, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_1_1", new int[]{R.raw.v1_1_1, R.drawable.p1_1_to2, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_2_0", new int[]{R.raw.v1_2_0, R.drawable.p1_2_others, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_2_1", new int[]{R.raw.v1_2_1, R.drawable.p1_2_to3, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_3_0", new int[]{R.raw.v1_3_0, R.drawable.p1_3_others, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_3_1", new int[]{R.raw.v1_3_1, R.drawable.p1_3_right, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_3_3", new int[]{R.raw.v1_3_3, R.drawable.p1_3_to4, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_4_0", new int[]{R.raw.v1_4_0, R.drawable.p1_4_others, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_4_1", new int[]{R.raw.v1_4_1, R.drawable.p1_4_to5, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_5_0", new int[]{R.raw.v1_5_0, R.drawable.p1_5_others, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_5_1", new int[]{R.raw.v1_5_1, R.drawable.p1_5_to6, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_6_0", new int[]{R.raw.v1_6_0, R.drawable.p1_6_others, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_6_1", new int[]{R.raw.v1_6_1, R.drawable.p1_6_to7, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_7_0", new int[]{R.raw.v1_7_0, R.drawable.p1_7_others, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_7_1", new int[]{R.raw.v1_7_1, R.drawable.p1_7_toroom2_1, R.drawable.p1_7_toroom2_2, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_1_1", new int[]{R.raw.v2_1_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_1_to2, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_2_0", new int[]{R.raw.v2_2_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_2_others, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_2_1", new int[]{R.raw.v2_2_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_2_to3, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_2_3", new int[]{R.raw.v2_2_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_2_light, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_2_5", new int[]{R.raw.v2_2_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_2_body, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_3_0", new int[]{R.raw.v2_3_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_3_others, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_3_1", new int[]{R.raw.v2_3_1, R.drawable.p1_8_others, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_8_0", new int[]{R.raw.v1_8_0, R.drawable.p1_8_others, R.drawable.p2_initial, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("1_8_1", new int[]{R.raw.v1_8_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_3_others, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_3_3", new int[]{R.raw.v2_3_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_3_light, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_3_5", new int[]{R.raw.v2_3_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_3_body, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_3_7", new int[]{R.raw.v2_3_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_be, R.drawable.p3_initial,
                R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_3_9", new int[]{R.raw.v2_3_9, R.drawable.p1_7_toroom2_1, R.drawable.p2_3_tocorrider_2,
                R.drawable.p2_3_tocorrider_3, R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("3_1_1", new int[]{R.raw.v3_1_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_3_tocorrider_2,
                R.drawable.p3_1_to2, R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("3_2_0", new int[]{R.raw.v3_2_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_3_tocorrider_2,
                R.drawable.p3_2_others, R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("3_2_1", new int[]{R.raw.v3_2_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_3_tocorrider_2,
                R.drawable.p2_be, R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("3_2_3", new int[]{R.raw.v3_2_3, R.drawable.p1_7_toroom2_1, R.drawable.p3_2_toroom2_2,
                R.drawable.p3_2_toroom2_3, R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_4_0", new int[]{R.raw.v2_4_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_4_others,
                R.drawable.p3_2_toroom2_3, R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_4_1", new int[]{R.raw.v2_4_1, R.drawable.p1_8_others, R.drawable.p2_initial,
                R.drawable.p3_2_toroom2_3, R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_4_3", new int[]{R.raw.v2_4_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_4_light,
                R.drawable.p3_2_toroom2_3, R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_4_5", new int[]{R.raw.v2_4_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_4_to5_room2,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_4_to5_room3, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_4_7", new int[]{R.raw.v2_4_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_be,
                R.drawable.p3_2_toroom2_3, R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_4_9", new int[]{R.raw.v2_4_9, R.drawable.p1_7_toroom2_1, R.drawable.p2_4_tocorrider_2,
                R.drawable.p2_4_tocorrider_3, R.drawable.p4_initial, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_5_0", new int[]{R.raw.v2_5_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_5_others,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_4_to5_room3, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_5_1", new int[]{R.raw.v2_5_1, R.drawable.p1_8_others, R.drawable.p2_5_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_4_to5_room3, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_5_3", new int[]{R.raw.v2_5_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_5_light,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_4_to5_room3, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_5_5", new int[]{R.raw.v2_5_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_5_others,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_4_to5_room3, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_5_7", new int[]{R.raw.v2_5_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_be,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_4_to5_room3, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_5_9", new int[]{R.raw.v2_5_9, R.drawable.p1_7_toroom2_1, R.drawable.p2_5_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_5_toroom3_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("4_1_1", new int[]{R.raw.v4_1_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_5_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_1_to2, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("4_2_0", new int[]{R.raw.v4_2_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_5_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_2_others, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("4_2_1", new int[]{R.raw.v4_2_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_5_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_be, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("4_2_3", new int[]{R.raw.v4_2_3, R.drawable.p1_7_toroom2_1, R.drawable.p4_2_toroom2_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_2_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("4_2_5", new int[]{R.raw.v4_2_5, R.drawable.p1_7_toroom2_1, R.drawable.p4_2_to3_room2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_2_to3_room3, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("4_3_0", new int[]{R.raw.v4_3_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_5_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_others, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("4_3_1", new int[]{R.raw.v4_3_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_5_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_be, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("4_3_3", new int[]{R.raw.v4_3_3, R.drawable.p1_7_toroom2_1, R.drawable.p4_3_toroom2_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("4_3_5", new int[]{R.raw.v4_3_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_5_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_ladder, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_7_0", new int[]{R.raw.v2_7_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_others,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_7_1", new int[]{R.raw.v2_7_1, R.drawable.p1_8_others, R.drawable.p2_7_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_7_3", new int[]{R.raw.v2_7_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p2_7_toroom4_5, R.drawable.p6_initial});
        searchTable.put("2_7_5", new int[]{R.raw.v2_7_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_others,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_7_7", new int[]{R.raw.v2_7_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_be,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_7_9", new int[]{R.raw.v2_7_9, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_7_toroom3_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("5_1_1", new int[]{R.raw.v5_1_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_1_to2, R.drawable.p6_initial});
        searchTable.put("5_2_0", new int[]{R.raw.v5_2_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_1_to2, R.drawable.p6_initial});
        searchTable.put("5_2_1", new int[]{R.raw.v5_2_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_2_to3, R.drawable.p6_initial});
        searchTable.put("5_2_3", new int[]{R.raw.v5_2_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_2_to8, R.drawable.p6_initial});
        searchTable.put("5_2_5", new int[]{R.raw.v5_2_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_others,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_8_0", new int[]{R.raw.v2_8_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_others,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_8_1", new int[]{R.raw.v2_8_1, R.drawable.p1_8_others, R.drawable.p2_7_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_8_3", new int[]{R.raw.v2_8_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_1_to2, R.drawable.p6_initial});
        searchTable.put("2_8_5", new int[]{R.raw.v2_8_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_others,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_8_7", new int[]{R.raw.v2_8_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_be,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("2_8_9", new int[]{R.raw.v2_8_9, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom3_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p2_7_toroom3_4, R.drawable.p5_initial, R.drawable.p6_initial});
        searchTable.put("5_3_0", new int[]{R.raw.v5_3_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_2_to3, R.drawable.p6_initial});
        searchTable.put("5_3_1", new int[]{R.raw.v5_3_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_2_to3, R.drawable.p6_initial});
        searchTable.put("5_3_3", new int[]{R.raw.v5_3_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_3_to4, R.drawable.p6_initial});
        searchTable.put("5_3_5", new int[]{R.raw.v5_3_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_2_to3, R.drawable.p6_initial});
        searchTable.put("5_3_7", new int[]{R.raw.v5_3_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_be, R.drawable.p6_initial});
        searchTable.put("5_4_0", new int[]{R.raw.v5_4_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_3_to4, R.drawable.p6_initial});
        searchTable.put("5_4_1", new int[]{R.raw.v5_4_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_3_to4, R.drawable.p6_initial});
        searchTable.put("5_4_3", new int[]{R.raw.v5_4_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_4_to5, R.drawable.p6_initial});
        searchTable.put("5_4_5", new int[]{R.raw.v5_4_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_3_to4, R.drawable.p6_initial});
        searchTable.put("5_4_7", new int[]{R.raw.v5_4_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_be, R.drawable.p6_initial});
        searchTable.put("5_5_0", new int[]{R.raw.v5_5_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_4_to5, R.drawable.p6_initial});
        searchTable.put("5_5_1", new int[]{R.raw.v5_5_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_4_to5, R.drawable.p6_initial});
        searchTable.put("5_5_3", new int[]{R.raw.v5_5_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_4_to5, R.drawable.p6_initial});
        searchTable.put("5_5_5", new int[]{R.raw.v5_5_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_4_to5, R.drawable.p6_initial});
        searchTable.put("5_5_7", new int[]{R.raw.v5_5_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_be, R.drawable.p6_initial});
        searchTable.put("5_5_9", new int[]{R.raw.v5_5_9, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_5_to6, R.drawable.p6_initial});
        searchTable.put("5_6_0", new int[]{R.raw.v5_6_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_5_to6, R.drawable.p6_initial});
        searchTable.put("5_6_1", new int[]{R.raw.v5_6_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_5_to6, R.drawable.p6_initial});
        searchTable.put("5_6_3", new int[]{R.raw.v5_6_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_5_to6, R.drawable.p6_initial});
        searchTable.put("5_6_5", new int[]{R.raw.v5_6_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_5_to6, R.drawable.p6_initial});
        searchTable.put("5_6_7", new int[]{R.raw.v5_6_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_he, R.drawable.p6_initial});
        searchTable.put("5_6_9", new int[]{R.raw.v5_6_9, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_5_to6, R.drawable.p6_initial});
        searchTable.put("5_8_0", new int[]{R.raw.v5_8_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_2_to8, R.drawable.p6_initial});
        searchTable.put("5_8_1", new int[]{R.raw.v5_8_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_2_to8, R.drawable.p6_initial});
        searchTable.put("5_8_3", new int[]{R.raw.v5_8_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_8_to9, R.drawable.p6_initial});
        searchTable.put("5_8_5", new int[]{R.raw.v5_8_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_2_to8, R.drawable.p6_initial});
        searchTable.put("5_9_0", new int[]{R.raw.v5_9_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_8_to9, R.drawable.p6_initial});
        searchTable.put("5_9_1", new int[]{R.raw.v5_9_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_9_to5, R.drawable.p6_initial});
        searchTable.put("5_9_3", new int[]{R.raw.v5_9_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_8_to9, R.drawable.p6_initial});
        searchTable.put("5_9_5", new int[]{R.raw.v5_9_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_8_to9, R.drawable.p6_initial});
        searchTable.put("5_9_7", new int[]{R.raw.v5_9_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_8_to9, R.drawable.p6_initial});
        searchTable.put("5_9_9", new int[]{R.raw.v5_9_9, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_9_10, R.drawable.p6_initial});
        searchTable.put("5_10_0", new int[]{R.raw.v5_10_0, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_9_10, R.drawable.p6_initial});
        searchTable.put("5_10_1", new int[]{R.raw.v5_10_1, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_10_to6, R.drawable.p6_initial});
        searchTable.put("5_10_3", new int[]{R.raw.v5_10_3, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_9_10, R.drawable.p6_initial});
        searchTable.put("5_10_5", new int[]{R.raw.v5_10_5, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_9_10, R.drawable.p6_initial});
        searchTable.put("5_10_7", new int[]{R.raw.v5_10_7, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_9_10, R.drawable.p6_initial});
        searchTable.put("5_10_9", new int[]{R.raw.v5_10_9, R.drawable.p1_7_toroom2_1, R.drawable.p2_7_toroom4_2,
                R.drawable.p2_4_to5_corrider, R.drawable.p4_3_toroom2_4, R.drawable.p5_9_10, R.drawable.p6_initial});

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}

