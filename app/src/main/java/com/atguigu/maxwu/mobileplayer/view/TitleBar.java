package com.atguigu.maxwu.mobileplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.maxwu.mobileplayer.R;

/**
 * 作者: WuKai
 * 时间: 2017/5/28
 * 邮箱: 648838173@qq.com
 * 作用:
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private TextView tv_sousuo;
    private RelativeLayout rl_game;
    private ImageView recorde;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_sousuo = (TextView) getChildAt(1);
        rl_game = (RelativeLayout) getChildAt(2);
        recorde = (ImageView) getChildAt(3);
        tv_sousuo.setOnClickListener(this);
        rl_game.setOnClickListener(this);
        recorde.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sousuokuang:
                Toast.makeText(mContext, "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_game:
                Toast.makeText(mContext, "Game", Toast.LENGTH_SHORT).show();
                break;
            case R.id.recorde:
                Toast.makeText(mContext, "Recorde", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
