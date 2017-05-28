package com.atguigu.maxwu.mobileplayer;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.atguigu.maxwu.mobileplayer.fragment.BaseFragment;
import com.atguigu.maxwu.mobileplayer.fragment.LocalAudioFragment;
import com.atguigu.maxwu.mobileplayer.fragment.LocalVideoFragment;
import com.atguigu.maxwu.mobileplayer.fragment.NetAudioFragment;
import com.atguigu.maxwu.mobileplayer.fragment.NetVideoFragment;
import com.atguigu.maxwu.mobileplayer.fragment.RecyclerNetAudioFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radio;
    private int position;
    private BaseFragment tempFragment;
    private ArrayList<BaseFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        radio.setOnCheckedChangeListener(this);
        radio.check(R.id.local_video);
    }

    private void initData() {
        setContentView(R.layout.activity_main);
        radio = (RadioGroup)findViewById(R.id.radio);
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
        if(tempFragment != currentFragment) {
            if(!currentFragment.isAdded()) {
                if(tempFragment != null) {
                    ft.hide(tempFragment);
                }
                ft.add(R.id.fl_content, currentFragment);
            }else {
                if(tempFragment != null) {
                    ft.hide(tempFragment);
                }
                ft.show(currentFragment);
            }
            tempFragment = currentFragment;
        }
        ft.commit();
    }
}
