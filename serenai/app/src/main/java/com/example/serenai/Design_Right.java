package com.example.serenai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Design_Right extends Fragment {
    private Button btn_test = null;
    private int[] mImgIds; // 存储图片资源的数组
    private LinearLayout designRight; // 布局容器，用于显示图片

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.design_right, container, false);

        // 查找布局容器
        LinearLayout designRight = view.findViewById(R.id.horizontalScrollViewItemContainer);

        // 存储图片资源的数组
        int[] mImgIds = { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
                R.drawable.e, R.drawable.e};

        // 遍历图片资源数组，创建ImageView并添加到布局容器
        for (int mImgId : mImgIds) {
            ImageView newImg = new ImageView(getContext());
            newImg.setImageResource(mImgId);

            // 设置ImageView的布局参数，包括大小和边距
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(-12, 20, -12, 20);
            newImg.setLayoutParams(params);

            // 将ImageView添加到布局容器
            designRight.addView(newImg);
        }
        return  view;
    }
}
