package algonquin.cst2335.kuma0181;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageView imgview;
        Switch sw;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgview = findViewById(R.id.flagview);
        sw = findViewById(R.id.switch1);

        sw.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked) {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(5000);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());

                imgview.startAnimation(rotate);
            } else {
                imgview.clearAnimation();
            }
        });
    }
}