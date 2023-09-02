package com.example.serenai;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * 用来管理添加的标签页类
 */

public class BaseFragemetAdapter extends FragmentStatePagerAdapter {

    //这里用来添加泛型的标签数组， 子layout(标签页)
    ArrayList<Fragment> fgs;

    //数组记录标签页名字
    private String[] mTitles;



    //子标签页和标签页名关联到一起
    public void setFgs(ArrayList<Fragment> fgs, String[] mTitles){
        this.fgs=fgs;
        this.mTitles=mTitles;
    }

    //调用父类的构造函数
    public BaseFragemetAdapter(FragmentManager fm) {
        super(fm); }


    //对父类方法的重写，监测当前选中的标签页ID
    @Override
    public Fragment getItem(int position) {
        return fgs.get(position); }


    //总共有多少个标签页
    @Override public int getCount() {
        return fgs.size(); }


    //获取选中的标签页名
    @Override public CharSequence getPageTitle(int position) {
        return mTitles[position];//很重要，起到与tableLayout关联的作用， 否则会报错
    }
}