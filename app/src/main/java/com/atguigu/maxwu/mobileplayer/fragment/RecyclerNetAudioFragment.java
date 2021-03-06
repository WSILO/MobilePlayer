package com.atguigu.maxwu.mobileplayer.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.maxwu.mobileplayer.R;
import com.atguigu.maxwu.mobileplayer.activity.ShowImageAndGifActivity;
import com.atguigu.maxwu.mobileplayer.adapter.MultipleAdapter;
import com.atguigu.maxwu.mobileplayer.domain.NetAudioBean;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 作者: WuKai
 * 时间: 2017/5/28
 * 邮箱: 648838173@qq.com
 * 作用:
 */

public class RecyclerNetAudioFragment extends BaseFragment {

    private final static String NET_AUDIO_URL = "http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.2.8/0-20.json?market=baidu&udid=863425026599592&appname=baisibudejie&os=4.2.2&client=android&visiting=&mac=98%3A6c%3Af5%3A4b%3A72%3A6d&ver=6.2.8";
    private final static String LAST_URL = "http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.2.8/0-";
    private final static String NEXT_URL = ".json?market=baidu&udid=863425026599592&appname=baisibudejie&os=4.2.2&client=android&visiting=&mac=98%3A6c%3Af5%3A4b%3A72%3A6d&ver=6.2.8\\";
    private int count = 20;
    private boolean isRefresh = true;
    private ArrayList<NetAudioBean.ListBean> datas = new ArrayList<>();

    private MultipleAdapter adapter;
    private TextView textView;
    private RecyclerView recyclerView;
    private MaterialRefreshLayout refresh;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.recycler_view, null);
        refresh = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        textView = (TextView) view.findViewById(R.id.tv_nodata);
        recyclerView = (RecyclerView) view.findViewById(R.id.net_recycler_audio);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                isRefresh = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDataFromNet();
                    }
                }, 2000);

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                isRefresh = false;
                getMoreData();
            }
        });
        adapter = new MultipleAdapter(mContext, datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultipleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NetAudioBean.ListBean listEntity = adapter.getItem(position);
                if (listEntity != null) {
                    Intent intent = new Intent(mContext, ShowImageAndGifActivity.class);
                    if (listEntity.getType().equals("gif")) {
                        String url = listEntity.getGif().getImages().get(0);
                        intent.putExtra("url", url);
                        mContext.startActivity(intent);
                    } else if (listEntity.getType().equals("image")) {
                        String url = listEntity.getImage().getBig().get(0);
                        intent.putExtra("url", url);
                        mContext.startActivity(intent);
                    }

                }
            }
        });
        return view;
    }

    private void getMoreData() {
        String newUrl = LAST_URL + count + NEXT_URL;
        RequestParams request = new RequestParams(newUrl);
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "onSuccess--result---" + result);
                parseJson(result);
                refresh.finishRefreshLoadMore();
                count += 20;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mContext, "onerror-----" + ex.getStackTrace(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams request = new RequestParams(NET_AUDIO_URL);
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "onSuccess--result---" + result);
                parseJson(result);
                refresh.finishRefresh();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mContext, "onerror-----" + ex.getStackTrace(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void parseJson(String result) {
        NetAudioBean netAudioBean = (new Gson()).fromJson(result, NetAudioBean.class);
        if(isRefresh) {
            ArrayList<NetAudioBean.ListBean> list = (ArrayList<NetAudioBean.ListBean>) netAudioBean.getList();
            if (list != null && list.size() > 0) {
                adapter.setDatas(list);
                textView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.VISIBLE);
            }
        }else {
            ArrayList<NetAudioBean.ListBean> list = (ArrayList<NetAudioBean.ListBean>) netAudioBean.getList();
            if (list != null && list.size() > 0) {
                adapter.setDatas(list);
                textView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.VISIBLE);
            }
        }

    }


}
