package com.mouldandroid.activity.contentList;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.ConversionUtil;
import com.mouldandroid.utils.LogUtils;
import com.mouldandroid.utils.ToastUtil;
import com.mouldandroid.utils.myretrofitutils.StringConverterFactory;
import com.mouldandroid.viewHolder.FinanciaListViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/28.
 *
 * 金融列表
 */

public class FinancialListActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnItemClickListener{

    private Context context;

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.recyclerView)
    public EasyRecyclerView recyclerView;

    private RecyclerArrayAdapter adapter;
    private Handler handler_thread = new Handler();
    private final int SUCCESS = 1;
    private ArrayList<String> arrayList;

    @Override
    protected int setView() {
        return R.layout.financial_list_layout;
    }

    @Override
    protected void initView() {
        context = FinancialListActivity.this;

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setRefreshingColorResources(R.color.color_54a2f8);  //设置SwipeRefreshLayout的进度条颜色
        recyclerView.setProgressView(R.layout.view_progress);
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, ConversionUtil.dip2px(context,0.5f),ConversionUtil.dip2px(context,72),0);
        itemDecoration.setDrawLastItem(false);
        //设置项分割线
//        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter(context) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new FinanciaListViewHolder(parent);
            }
        });
        //设置正在加载更多数据
        adapter.setMore(R.layout.view_more,this);
        //设置没有更多数据显示和回调
        adapter.setNoMore(R.layout.view_nomore,onNoMoreListener);
        //设置网络错误layout和回调
        adapter.setError(R.layout.view_error,errorListener);
        //设置项点击事件
        adapter.setOnItemClickListener(this);
        //设置下拉刷新
        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    @Override
    protected void initData() {

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        handler_thread.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                postData();
            }
        },2000);
    }

    /**
     * 加载方法
     */
    @Override
    public void onLoadMore() {

    }

    /**
     * 项点击方法
     * @param position
     */
    @Override
    public void onItemClick(int position) {

    }

    /**
     * 请求数据
     */
    private void postData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.financial("1")
                .subscribeOn(Schedulers.newThread())           //请求在新的线程执行
                .observeOn(Schedulers.io())                    //请求完成后在IO线程中执行
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {   //添加数据
                        arrayList = new ArrayList<>();
                        //处理数据

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())   //最后在主线程执行
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {  //请求失败
                        LogUtils.e(e.getMessage());
                        ToastUtil.showToast(context,"请求失败");
                    }
                    @Override
                    public void onNext(String s) {
                        handler.sendEmptyMessage(SUCCESS);
                    }
                });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    adapter.addAll(arrayList);
                    break;
            }
        }
    };

    //网络错误回调
    private RecyclerArrayAdapter.OnErrorListener errorListener = new RecyclerArrayAdapter.OnErrorListener(){
        @Override
        public void onErrorShow() {
            adapter.resumeMore();
        }
        @Override
        public void onErrorClick() {
            adapter.resumeMore();
        }
    };

    //没有更多回调
    private RecyclerArrayAdapter.OnNoMoreListener onNoMoreListener = new RecyclerArrayAdapter.OnNoMoreListener(){
        @Override
        public void onNoMoreShow() {
            adapter.resumeMore();
        }
        @Override
        public void onNoMoreClick() {
            adapter.resumeMore();
        }
    };
}
