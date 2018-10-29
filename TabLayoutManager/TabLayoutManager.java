package com.feng.layoututil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

/**
 * @author Feng Zhaohao
 * Created on 2018/10/28
 */
public class TabLayoutManager {

    @SuppressLint("StaticFieldLeak")
    private static TabLayoutManager mTabLayoutManager;
    private TabLayout mTabLayout;
    private AppCompatActivity mAppCompatActivity;

    private int mTabNum = 0;            //标签数
    private int[] mBeforePressedIcons;  //未点击时的图标
    private int[] mPressedIcons;        //点击后的图标
    private String[] mTabContents;      //标签文字
    private int mColorBeforePressed;    //未点击时的文字颜色
    private int mColorPressed;          //点击后的文字颜色

    private static int FLAG_FIRST_CLICK = 1;    //1表示第一次点击，0表示不是第一次点击

    private static final String TAG = "fzh";

    private FragmentTransaction mFragmentTransaction;

    private OnTabSelectedListener mOnTabSelectedListener;
    //该接口用于点击tab后回调
    public interface OnTabSelectedListener{
        void tabSelected(int position);
    }
    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mOnTabSelectedListener = onTabSelectedListener;
    }

    public static TabLayoutManager newInstance(TabLayout mTabLayout, AppCompatActivity mAppCompatActivity) {
        mTabLayoutManager = new TabLayoutManager(mTabLayout, mAppCompatActivity);
        return mTabLayoutManager;
    }

    TabLayoutManager(TabLayout mTabLayout, AppCompatActivity mAppCompatActivity) {
        this.mTabLayout = mTabLayout;
        this.mAppCompatActivity = mAppCompatActivity;
    }

    public TabLayoutManager setTabNum(int num) {
        mTabNum = num;
        return mTabLayoutManager;
    }

    public TabLayoutManager setIcons(int [] beforePressedIcons, int [] pressedIcons) {
        if (beforePressedIcons.length != mTabNum ||
                pressedIcons.length != mTabNum) {
            return mTabLayoutManager;
        }
        mBeforePressedIcons = beforePressedIcons;
        mPressedIcons = pressedIcons;

        return mTabLayoutManager;
    }

    public TabLayoutManager setTabContents(String[] tabContents) {
        if (tabContents.length != mTabNum) {
            return mTabLayoutManager;
        }
        mTabContents = tabContents;

        return mTabLayoutManager;
    }

    public TabLayoutManager setTextColor(int colorBeforePressed, int colorPressed) {
        mColorBeforePressed = colorBeforePressed;
        mColorPressed = colorPressed;

        return mTabLayoutManager;
    }

    public void build(int container, Fragment[] fragments) {
        addTabLayoutListener(container, fragments);
        addTabLayout();
    }

    public void recycle() {
        mTabLayout.removeAllTabs();     //移除所有tab
        mTabLayout.clearOnTabSelectedListeners();   //移除所有tab监听
        FLAG_FIRST_CLICK = 1;           //重置标记位
    }

    /**
     * 添加TabLayout选择监听
     *
     * @param container
     * @param fragments
     */
    private void addTabLayoutListener(final int container, final Fragment[] fragments) {
        //对TabLayout添加监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("CommitTransaction")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mOnTabSelectedListener.tabSelected(mTabLayout.getSelectedTabPosition());

                mFragmentTransaction = mAppCompatActivity.getSupportFragmentManager()
                        .beginTransaction();        //开启事务

                addFragmentToContainer(container, fragments);
                setTabState();
                showFragment(mTabLayout.getSelectedTabPosition(), fragments);

                mFragmentTransaction.commit();      //提交事务
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 添加tab到TabLayout中
     */
    private void addTabLayout() {
        for (int i = 0; i < mTabNum; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(mAppCompatActivity, i)));
        }
    }

    /**
     * 第一次点击时添加Fragment到容器中
     *
     * @param container
     * @param fragments
     */
    private void addFragmentToContainer(int container, Fragment[] fragments) {
        if (fragments.length != mTabNum) {
            return;
        }
        if (FLAG_FIRST_CLICK == 1) {
            for (Fragment fragment : fragments) {
                mFragmentTransaction.add(container, fragment);
            }
            FLAG_FIRST_CLICK = 0;
        }
    }

    /**
     * 点击tab时改变其图标和文字状态
     */
    private void setTabState() {
        for (int i = 0; i < mTabNum; i++) {
            try {
                View view = Objects.requireNonNull(mTabLayout.getTabAt(i)).getCustomView();
                ImageView tabIconImageView = Objects.requireNonNull(view).findViewById(R.id.iv_main_tab_icon);
                TextView tabTitleTextView = view.findViewById(R.id.tv_main_tab_title);
                //选中的tab
                if (i == mTabLayout.getSelectedTabPosition()) {
                    tabIconImageView.setImageResource(mPressedIcons[i]);
                    //设置颜色时不能直接tabTitleTextView.setTextColor(mColorPressed);
                    tabTitleTextView.setTextColor(mAppCompatActivity.getResources().getColor(mColorPressed));
                } else {    //未选中的tab
                    tabIconImageView.setImageResource(mBeforePressedIcons[i]);
                    tabTitleTextView.setTextColor(mAppCompatActivity.getResources().getColor(mColorBeforePressed));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示对应的碎片
     *
     * @param position
     * @param fragments
     */
    private void showFragment(int position, Fragment [] fragments) {
        for (int i = 0; i < mTabNum; i++) {
            //显示被点击的碎片，其他的碎片隐藏
            if (position == i) {
                if (fragments[i].isHidden()) {
                    mFragmentTransaction.show(fragments[i]);
                }
            } else {
                if (!fragments[i].isHidden()) {
                    mFragmentTransaction.hide(fragments[i]);
                }
            }

        }
    }

    /**
     * 获取Tab显示的内容
     *
     * @param context  上下文
     * @param position Tab的索引
     * @return 自定义的TabView
     */
    private View getTabView(Context context, int position) {

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.tab_view, null);
        //设置图标
        ImageView tabIconImageView = view.findViewById(R.id.iv_main_tab_icon);
        tabIconImageView.setImageResource(mBeforePressedIcons[position]);
        //设置文字
        TextView tabTitleTextView = view.findViewById(R.id.tv_main_tab_title);
        tabTitleTextView.setText(mTabContents[position]);
        return view;
    }
}
