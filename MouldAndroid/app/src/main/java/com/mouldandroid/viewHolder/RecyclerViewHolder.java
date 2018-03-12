package com.mouldandroid.viewHolder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mouldandroid.R;
import com.mouldandroid.entity.TheArticle;

/**
 * Created by Administrator on 2018/1/31.
 */

public class RecyclerViewHolder extends BaseViewHolder<TheArticle.TheArticleData> {

    private ImageView iv_content_icon;
    private TextView tv_content_title,tv_content_author,tv_content_comments,tv_content_reading;

    public RecyclerViewHolder(ViewGroup parent) {
        super(parent, R.layout.content_item);
        iv_content_icon = $(R.id.iv_content_icon);
        tv_content_title = $(R.id.tv_content_title);
        tv_content_author = $(R.id.tv_content_author);
        tv_content_comments = $(R.id.tv_content_comments);
        tv_content_reading = $(R.id.tv_content_reading);
    }

    @Override
    public void setData(TheArticle.TheArticleData data) {
        super.setData(data);
//        Glide.with(getContext())
        tv_content_title.setText(data.getTitle());
        tv_content_author.setText(data.getAuthor());
        tv_content_comments.setText(data.getComments());
        tv_content_reading.setText(data.getFavorites());
    }
}