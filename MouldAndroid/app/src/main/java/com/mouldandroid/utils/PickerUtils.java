package com.mouldandroid.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.mouldandroid.R;
import com.mouldandroid.entity.JsonBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/26.
 *
 *底部选择器工具类
 */

public class PickerUtils {

    public static OptionsPickerView pvOptions;
    public static TimePickerView pvTime;
    private final static int MSG_LOAD_SUCCESS = 1;
    private static ArrayList<JsonBean> options1Items = new ArrayList<>();
    private static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private static Thread thread;
    private static Context contexts;
    private static TextView textViews;

    /**
     * 性别选择器
     * @param context
     * @param options1Items
     * @param textView
     * @return
     */
    public static OptionsPickerView initOptionPicker(Context context, final ArrayList<String> options1Items,final TextView textView) {//条件选择器初始化
        pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str = options1Items.get(options1).toString();
                textView.setText(str);
            }
        })
                .setTitleText("性别")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项  .setBgColor(Color.BLACK)
                .setBgColor(Color.WHITE)
                .setTitleBgColor(context.getResources().getColor(R.color.color_eeeeee))
                .setTitleColor(Color.BLACK)
                .setCancelColor(context.getResources().getColor(R.color.color_279AD5))
                .setSubmitColor(context.getResources().getColor(R.color.color_279AD5))
                .setTextColorCenter(Color.BLACK)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。 .setLabels("省", "市", "区")
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                .build();
        pvOptions.setPicker(options1Items);
        return pvOptions;
    }

    /**
     * 时间选择器
     * @param context
     * @return
     */
    public static TimePickerView initTimePicker(Context context,final TextView textView) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
//                TextView textView = (TextView) v;
                textView.setText(getTime(date));
            }
        })
                .setTitleText("日期")
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
        return pvTime;
    }

    private static boolean is = false;
    public static void newThread(final Context context, final TextView textView){
        if (!is){
            if (thread == null){
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        is = initJsonData(context,textView);
                    }
                });
                thread.start();
            }
        }else {
            ShowPickerView(context,textView);
        }
    }

    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_LOAD_SUCCESS:
                    ShowPickerView(contexts,textViews);
                    break;
            }
        }
    };

    /**
     * 解析省份，并弹出选择器
     * @param context
     * @param textView
     */
    public static boolean initJsonData(Context context,TextView textView) {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        contexts = context;
        textViews = textView;
        String JsonData = new GetJsonDataUtil().getJson(context,"province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = GetJsonDataUtil.parseData(JsonData);//用Gson 转成实体
         //添加省份数据
         //注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         //PickerView会通过getPickerViewText方法获取字符串显示出来。
        options1Items = jsonBean;
        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {
                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            //添加城市数据
            options2Items.add(CityList);
            //添加地区数据
            //options3Items.add(Province_AreaList);
        }
        //解析完成
        //弹出选择器
        handler.sendEmptyMessage(MSG_LOAD_SUCCESS);
        return true;
    }

    /**
     * 弹出城市选择器
     * @param context
     * @param textView
     */
    public static void ShowPickerView(Context context, final TextView textView) {// 弹出选择器
        OptionsPickerView  pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+","+
                        options2Items.get(options1).get(options2);
//                        +options3Items.get(options1).get(options2).get(options3);
//                Toast.makeText(JsonDataActivity.this,tx,Toast.LENGTH_SHORT).show();
                textView.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        //pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        //pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }

    private static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}
