package com.atguigu.maxwu.mobileplayer;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.atguigu.maxwu.mobileplayer.fragment.BaseFragment;
import com.atguigu.maxwu.mobileplayer.fragment.LocalAudioFragment;
import com.atguigu.maxwu.mobileplayer.fragment.LocalVideoFragment;
import com.atguigu.maxwu.mobileplayer.fragment.NetAudioFragment;
import com.atguigu.maxwu.mobileplayer.fragment.NetVideoFragment;
import com.atguigu.maxwu.mobileplayer.fragment.RecyclerNetAudioFragment;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radio;
    private int position;
    private BaseFragment tempFragment;
    private ArrayList<BaseFragment> fragments;
    private SensorManager sensorManager;
    private JCVideoPlayer.JCAutoFullscreenListener sensorEventListener;
    private boolean isExit = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
        radio.setOnCheckedChangeListener(this);
        radio.check(R.id.local_video);
    }

    private void initData() {
        setContentView(R.layout.activity_main);
        radio = (RadioGroup) findViewById(R.id.radio);
        fragments = new ArrayList<>();
        addDataToList();
    }

    private void addDataToList() {
        fragments.add(new LocalVideoFragment());
        fragments.add(new LocalAudioFragment());
        fragments.add(new NetVideoFragment());
        fragments.add(new NetAudioFragment());
        fragments.add(new RecyclerNetAudioFragment());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.local_video:
                position = 0;
                break;
            case R.id.local_audio:
                position = 1;
                break;
            case R.id.net_video:
                position = 2;
                break;
            case R.id.net_listview_audio:
                position = 3;
                break;
            case R.id.net_recycler_audio:
                position = 4;
                break;
        }
        BaseFragment currentFragment = fragments.get(position);
        addFragment(currentFragment);
    }

    private void addFragment(BaseFragment currentFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (tempFragment != currentFragment) {
            if (!currentFragment.isAdded()) {
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                ft.add(R.id.fl_content, currentFragment);
            } else {
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                ft.show(currentFragment);
            }
            tempFragment = currentFragment;
        }
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (position != 0) {
                position = 0;
                radio.check(R.id.local_video);
                return true;
            } else if (!isExit) {
                isExit = true;
                Toast.makeText(MainActivity.this, "再按一次退出软件", Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDestroy();

    }
}
