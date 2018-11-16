package com.feng.layoututil.util;

import android.support.v4.app.Fragment;

/**
 * @author Feng Zhaohao
 * Created on 2018/11/16
 */
public class TabLayoutConfig {
    private int mTabNum = 0;            //标签数
    private int[] mBeforePressedIcons;  //未点击时的图标
    private int[] mPressedIcons;        //点击后的图标
    private String[] mTabContents;      //标签文字
    private int mColorBeforePressed;    //未点击时的文字颜色
    private int mColorPressed;          //点击后的文字颜色
    private int mContainer;             //Fragment的容器
    private Fragment[] mFragments;      //Fragment集合

    public int getTabNum() {
        return mTabNum;
    }

    public int[] getBeforePressedIcons() {
        return mBeforePressedIcons;
    }

    public int[] getPressedIcons() {
        return mPressedIcons;
    }

    public String[] getTabContents() {
        return mTabContents;
    }

    public int getColorBeforePressed() {
        return mColorBeforePressed;
    }

    public int getColorPressed() {
        return mColorPressed;
    }

    public int getContainer() {
        return mContainer;
    }

    public Fragment[] getFragments() {
        return mFragments;
    }

    public static class Builder {
        int tabNum = 0;            //标签数
        int[] beforePressedIcons;  //未点击时的图标
        int[] pressedIcons;        //点击后的图标
        String[] tabContents;      //标签文字
        int colorBeforePressed;    //未点击时的文字颜色
        int colorPressed;          //点击后的文字颜色
        int container;             //Fragment的容器
        Fragment[] fragments;      //Fragment集合

        public Builder setTabNum(int num) {
            tabNum = num;
            return this;
        }

        public Builder setIcons(int [] beforePressedIcons, int [] pressedIcons) throws Exception {
            if (beforePressedIcons.length != tabNum ||
                    pressedIcons.length != tabNum) {
                throw new Exception("数组个数和标签数不匹配");
//                throw new Error("数组个数和标签数不匹配");
            }
            this.beforePressedIcons = beforePressedIcons;
            this.pressedIcons = pressedIcons;

            return this;
        }

        public Builder setTabContents(String[] tabContents) throws Exception {
            if (tabContents.length != tabNum) {
                throw new Exception("数组个数和标签数不匹配");
            }
            this.tabContents = tabContents;

            return this;
        }

        public Builder setTextColor(int colorBeforePressed, int colorPressed) {
            this.colorBeforePressed = colorBeforePressed;
            this.colorPressed = colorPressed;

            return this;
        }

        public Builder setFragmentContainer(int container) {
            this.container = container;

            return this;
        }

        public Builder setFragments(Fragment[] fragments) throws Exception {
            if (fragments.length != tabNum) {
                throw new Exception("数组个数和标签数不匹配");
            }

            this.fragments = fragments;

            return this;
        }

        /**
         * 根据已经设置好的属性创建配置对象
         *
         * @return
         */
        public TabLayoutConfig create() {
            TabLayoutConfig config = new TabLayoutConfig();
            config.mTabNum = tabNum;
            config.mBeforePressedIcons = beforePressedIcons;
            config.mPressedIcons = pressedIcons;
            config.mTabContents = tabContents;
            config.mColorBeforePressed = colorBeforePressed;
            config.mColorPressed = colorPressed;
            config.mContainer = container;
            config.mFragments = fragments;

            return config;
        }

    }
}
