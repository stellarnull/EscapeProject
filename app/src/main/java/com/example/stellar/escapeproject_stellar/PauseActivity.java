package com.example.stellar.escapeproject_stellar;

import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class PauseActivity extends AppCompatActivity implements View.OnClickListener{
    private int[] state;

    private static final String TAG = "PauseActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pause);
        Button resumeBtn = (Button) findViewById(R.id.resume_btn);
        Button backBtn = (Button) findViewById(R.id.back_btn);
        resumeBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.hide();
        }

        Intent intent = getIntent();
        state = intent.getIntArrayExtra("state");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch (id)
        {
            case R.id.resume_btn:
                Log.d(TAG, "PauseActivity onClick: resume");
                intent = new Intent(PauseActivity.this, MainActivity.class);
                intent.putExtra("state", state);
                Log.d(TAG, "state sent from Pause to Main: " + Util.itIntArray(state));
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.back_btn:
                Log.d(TAG, "PauseActivity onClick: back");
                intent = new Intent(PauseActivity.this, HelloActivity.class);
                intent.putExtra("state", state);
                Log.d(TAG, "state sent from Pause to Hello: " + Util.itIntArray(state));
                //startActivityForResult(intent, 1);
                startActivity(intent);
                finish();
                break;
            default:
        }

    }
}
