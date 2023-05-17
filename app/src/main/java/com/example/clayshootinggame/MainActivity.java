package com.example.clayshootinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
        iv_bullet.setVisibility(View.VISIBLE);

        ObjectAnimator bullet_SDX = ObjectAnimator.ofFloat(iv_bullet,"scaleX",1f,0f);
        ObjectAnimator bullet_SDY = ObjectAnimator.ofFloat(iv_bullet,"scaleY",1f,0f);
        ObjectAnimator bullet_Y = ObjectAnimator.ofFloat(iv_bullet,"translationY",(float)gun_y ,0f);

        double bullet_x = gun_center_x - 0.5 * bullet_width;
        iv_bullet.setX((float)bullet_x);

        bullet_Y.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                bullet_center_x = iv_bullet.getX() + 0.5f * bullet_width;
                bullet_center_y = iv_bullet.getY() + 0.5f * bullet_height;

                clay_center_x = iv_clay.getX() + 0.5f * clay_width;
                clay_center_y = iv_clay.getY() + 0.5f * clay_height;

                double dist = Math.sqrt(Math.pow(bullet_center_x - clay_center_x, 2)+Math.pow(bullet_center_y - clay_center_y, 2));
                if(dist <= 100)
                    iv_clay.setVisibility(View.INVISIBLE);
            }
        });

        bullet_SDX.start();
        bullet_SDY.start();
        bullet_Y.start();
    }

    private void gameStop() {
        finish();
    }

    private void gameStart() {
        ObjectAnimator clay_translateX = ObjectAnimator.ofFloat(iv_clay, "translationX", -100f, (float)screen_width);
        ObjectAnimator clay_translateY = ObjectAnimator.ofFloat(iv_clay, "translationY", 0f, 0f );
        ObjectAnimator clay_rotation = ObjectAnimator.ofFloat(iv_clay, "rotation", 0f, 360f*5f);
        clay_translateX.setRepeatCount(NO_OF_CLAYS-1);
        clay_translateY.setRepeatCount(NO_OF_CLAYS-1);
        clay_rotation.setRepeatCount(NO_OF_CLAYS-1);
        clay_translateX.setDuration(3000);
        clay_translateY.setDuration(3000);
        clay_rotation.setDuration(3000);

        clay_translateX.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animator) {
                iv_clay.setVisibility(View.VISIBLE);
            }
            public void onAnimationEnd(Animator animator) {
                Toast.makeText(getApplicationContext(), "게임 종료", Toast.LENGTH_SHORT).show();
            }
            public void onAnimationCancel(Animator animator) {
            }
            public void onAnimationRepeat(Animator animator) {
                iv_clay.setVisibility(View.VISIBLE);
            }
        });
        clay_translateX.start();
        clay_translateY.start();
        clay_rotation.start();

    }
}