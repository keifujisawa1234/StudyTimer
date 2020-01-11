package net.ebisobacode.studytimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTimerText;
    ImageView mStartButton;
    ImageView mStopButton;
    SoundPool mSoundPool;
    int mSoundResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerText = (TextView) findViewById(R.id.textViewTimer);
        mStartButton = (ImageView) findViewById(R.id.imageViewTimerStart);
        mStopButton = (ImageView) findViewById(R.id.imageViewTimerStop);

//        final CountDownTimer timer = new CountDownTimer(3*60*1000, 100) {
        final CountDownTimer timer = new CountDownTimer(5000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                long hour = millisUntilFinished/1000/60/60;
                long minute = millisUntilFinished/1000/60;
                long second = millisUntilFinished/1000%60;

                mTimerText.setText(String.format("%1$d:%2$02d:%3$02d", hour, minute, second));
            }

            @Override
            public void onFinish() {
                mTimerText.setText("お疲れ様でした！");
                mSoundPool.play(mSoundResId, 1.0f, 1.0f, 0,0, 1.0f);
            }
        };

        mStartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timer.start();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timer.cancel();
                mSoundPool.stop(mSoundResId);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build())
                .build();

        mSoundResId = mSoundPool.load(this, R.raw.line_girl1_otsukaresamadeshita1,1);
    }

    @Override
    protected void onPause() {

        super.onPause();
        mSoundPool.release();
    }


}
