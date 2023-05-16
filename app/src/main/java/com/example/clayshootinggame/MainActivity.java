package com.example.clayshootinggame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView iv_gun;
    ImageView iv_bullet;
    ImageView iv_clay;

    double screen_width, screen_height;

    float gun_width, gun_height;
    float bullet_width, bullet_height;
    float clay_width, clay_height;

    float bullet_center_x, bullet_center_y;
    float clay_center_x, clay_center_y;
    double gun_center_x;

    double gun_x, gun_y;

    final int NO_OF_CLAYS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button btnStart = findViewById(R.id.btnStart);
        Button btnStop = findViewById(R.id.btnStop);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        ConstraintLayout layout = findViewById(R.id.layout);
        screen_width = Resources.getSystem().getDisplayMetrics().widthPixels;
        screen_height = Resources.getSystem().getDisplayMetrics().heightPixels;

        iv_gun = new ImageView(this);
        iv_bullet = new ImageView(this);
        iv_clay = new ImageView(this);

        iv_gun.setImageResource(R.drawable.gun);
        iv_gun.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        gun_width = iv_gun.getMeasuredWidth();
        gun_height = iv_gun.getMeasuredHeight();
        layout.addView(iv_gun);

        iv_bullet.setImageResource(R.drawable.bullet);
        iv_bullet.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        bullet_width = iv_bullet.getMeasuredWidth();
        bullet_height = iv_bullet.getMeasuredHeight();
        iv_bullet.setVisibility(View.INVISIBLE); // 총알이 안보이는 상태 설정 Visibility =
        layout.addView(iv_bullet);

        iv_clay.setImageResource(R.drawable.clay);
        iv_clay.setScaleX(0.8f);
        iv_clay.setScaleY(0.8f);
        iv_clay.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        clay_width = iv_clay.getMeasuredWidth();
        clay_height = iv_clay.getMeasuredHeight();
        layout.addView(iv_clay);

        gun_center_x = screen_width * 0.7;
        gun_x = gun_center_x - 0.5 * gun_width;
        gun_y = screen_height - gun_width;

        iv_gun.setX((float)gun_x);
        iv_gun.setY((float)gun_y);

        iv_gun.setClickable(true);
        iv_gun.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnStart)
            gameStart();
        else if(view.getId() == R.id.btnStop)
            gameStop();
        else if(view == iv_gun)
            shootingStart();
    }

    private void shootingStart() {
    }

    private void gameStop() {
        finish();
    }

    private void gameStart() {
        
    }
}