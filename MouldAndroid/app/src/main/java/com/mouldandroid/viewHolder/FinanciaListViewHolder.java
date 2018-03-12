package com.mouldandroid.viewHolder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mouldandroid.R;

/**
 * Created by Administrator on 2018/2/28.
 */

public class FinanciaListViewHolder extends BaseViewHolder<String>{

    private TextView tv_financial_title;
    private TextView tv_financial_rate;
    private TextView tv_financial_date;

    public FinanciaListViewHolder(ViewGroup parent) {
        super(parent, R.layout.financial_list_item);
        tv_financial_title = $(R.id.tv_financial_title);
        tv_financial_rate = $(R.id.tv_financial_rate);
        tv_financial_date = $(R.id.tv_financial_date);
    }

    @Override
    public void setData(String data) {
        super.setData(data);
    }
}
