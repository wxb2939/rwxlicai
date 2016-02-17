package com.rwxlicai.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rwxlicai.R;
import com.rwxlicai.activity.A0_LoginActivity;
import com.rwxlicai.activity.Borrow_GetBorrowInfo_Activity;
import com.rwxlicai.activity.Crash_regionActivity;
import com.rwxlicai.activity.Invest_regionActivity;
import com.rwxlicai.entity.IndexResult;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.utils.Config;
import com.rwxlicai.utils.PrepareDate;
import com.rwxlicai.view.RoundProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xuebing on 15/12/28.
 */
public class ContentFragment extends Fragment {

    @InjectView(R.id.list)
    PullToRefreshListView listView;


    private JSONArray jsonArray;
    private View headview, invest, footView;
    private MyAdapter adapter;
    private List<IndexResult> data;
    protected ImageLoader imageLoader;
    private ViewPager viewPager;
    private int currentItem = 0; // 当前图片的索引号
    private int[] imageResId; // 图片ID
    private List<View> dots; // 图片标题正文的那些点
    private List<ImageView> imageViews;
    private ScheduledExecutorService scheduledExecutorService;

    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        ButterKnife.inject(this, view);
        data = new ArrayList<>();
        index();
        imageLoader = ImageLoader.getInstance();
        headview = LayoutInflater.from(getActivity()).inflate(R.layout.home_headview, null);
        footView = LayoutInflater.from(getActivity()).inflate(R.layout.footview, null);
        ListView list_view = listView.getRefreshableView();
        list_view.addHeaderView(headview);
        list_view.addFooterView(footView);
        listView.setClickable(false);
        listView.setEnabled(false);

        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);
        initHeadView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Config.getLogin(getActivity())) {

                    Float money = Float.parseFloat(Config.getAvailableMoney(getActivity()));
                    if (money == 0.0){
                        startActivity(new Intent(getActivity(), Crash_regionActivity.class));
                    }else {
                        Intent mIntent = new Intent(getActivity(), Borrow_GetBorrowInfo_Activity.class);
                        mIntent.putExtra("data", data.get(position - 2).getBid());
                        startActivity(mIntent);
                    }

                } else {
                    startActivity(new Intent(getActivity(), A0_LoginActivity.class));
                }
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("仁我行理财");//改变标题
        setHasOptionsMenu(true);//保证能调用onCreateOptionsMenu
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();//不清空就会变成添加进来而不是替换
        inflater.inflate(R.menu.main, menu);
    }


    public void index() {

        RwxHttpClient.ClientTokenPost(getActivity(), RwxUrlFactory.BASE_URL + RwxUrlFactory.index, null, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
//                        Toast.makeText(getActivity(), obj.getString("Message"), Toast.LENGTH_LONG).show();
                        jsonArray = new JSONArray(obj.getString("result"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            IndexResult indexResult = gson.fromJson(object.toString(), IndexResult.class);
                            data.add(indexResult);
                        }
                    } else {
                        Toast.makeText(getActivity(), obj.getString("Message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(getActivity(), "请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initHeadView() {
        imageResId = PrepareDate.getImg();
        imageViews = new ArrayList<ImageView>();
        // 初始化图片资源
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.content_fragment_cell, null);
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
        viewPager = (ViewPager) headview.findViewById(R.id.vp);
        invest = headview.findViewById(R.id.invest);
        invest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Invest_regionActivity.class));
            }
        });

        dots = new ArrayList<View>();
        dots.add(headview.findViewById(R.id.v_dot0));
        dots.add(headview.findViewById(R.id.v_dot1));
        dots.add(headview.findViewById(R.id.v_dot2));
        // 设置填充ViewPager页面的适配器
        viewPager.setAdapter(new MyAdapter1());
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }


    @Override
    public void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4,
                TimeUnit.SECONDS);
        super.onStart();
    }


    private class MyAdapter1 extends PagerAdapter {

        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imageViews.get(arg1));
            return imageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
            // tv_title.setText(titles[position]);
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    @Override
    public void onStop() {
        scheduledExecutorService.shutdown();
        super.onStop();
    }

    /**
     * 换行切换任务
     *
     * @author Administrator
     */
    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

    // ViewHolder静态类
    static class ViewHolder {
        public RoundProgressBar mRoundProgressBar;
        public TextView title;
        public TextView interestRate;
        public TextView type;
        public TextView borrowSum;
        public TextView tenderSum;
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public IndexResult getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.employ_myd_item, null);
                holder.mRoundProgressBar = (RoundProgressBar) convertView.findViewById(R.id.roundProgressBar);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.interestRate = (TextView) convertView.findViewById(R.id.interestRate);
                holder.type = (TextView) convertView.findViewById(R.id.type);
                holder.borrowSum = (TextView) convertView.findViewById(R.id.borrowSum);
                holder.tenderSum = (TextView) convertView.findViewById(R.id.tenderSum);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            convertView.setEnabled(false);
            convertView.setClickable(false);
            double mfloat = Float.parseFloat(data.get(position).getProgressRate());
            holder.mRoundProgressBar.setProgress((int) mfloat);
//            holder.mRoundProgressBar.setProgress(50);
            String flag = data.get(position).getType();
            String dFlag = data.get(position).getIsDay();
            String nFlag = data.get(position).getBorrowTimeLimit();
            if (dFlag.equals("1")) {
                holder.type.setText(nFlag + "天");
            } else {
                holder.type.setText(nFlag + "月");
            }
            if (flag == null) {
                holder.title.setText("月标" + "-" + data.get(position).getBorrowTitle());
            } else {
                if (flag.equals("year")) {
                    holder.title.setText(data.get(position).getTitle() + "-" + data.get(position).getBorrowTitle());
                } else if (flag.equals("quarter")) {
                    holder.title.setText(data.get(position).getTitle() + "-" + data.get(position).getBorrowTitle());
                } else if (flag.equals("day")) {
                    holder.title.setText(data.get(position).getTitle() + "-" + data.get(position).getBorrowTitle());
                }
            }
            holder.interestRate.setText(data.get(position).getInterestRate() + "％");
            holder.borrowSum.setText(data.get(position).getBorrowSum());
            holder.tenderSum.setText(data.get(position).getTenderSum());
            return convertView;
        }
    }
}
