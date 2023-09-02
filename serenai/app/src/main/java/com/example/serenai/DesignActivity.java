package com.example.serenai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class DesignActivity extends AppCompatActivity {

    //放标签页名的容器
    private TabLayout mTableLayout;
    //标签页名字叫什么
    private String[] mTitles={"主题","Seren"};
    //新建标签页数组(泛型)
    private ArrayList<Fragment> fgs=new ArrayList<Fragment>();
    //标签页具体的显示内容
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        initViews();
        intiDatas();
        initEvents();

    }

    //监听我们选中的标签页是哪个
    private void initEvents() {
        mTableLayout.addOnTabSelectedListener(new TabLayout. OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(DesignActivity.this, tab.getText()+"", Toast.LENGTH_SHORT).show(); }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            } }); }

    //添加标签页到主窗口
    private void initMenus(){
        fgs.clear();

        //添加左边标签页
        fgs.add(new Design_Left());
        //添加右边标签页
        fgs.add(new Design_Right());


    }

    //用BaseFragemetAdapter来管理当前的子标签页
    private void intiDatas()
    {
        initMenus();
        BaseFragemetAdapter adapter=new BaseFragemetAdapter( getSupportFragmentManager());

        adapter.setFgs(fgs,mTitles);
        vp.setAdapter(adapter);
        mTableLayout.setupWithViewPager(vp);
    }

    //找个存放标签页title 控件 的句柄
    private void initViews()
    {
        mTableLayout= (TabLayout) getViewById(R.id.tb);
        vp= (ViewPager) getViewById(R.id.vp);
    }


    private View getViewById(int id)
    {
        return this.findViewById(id);
    }
}