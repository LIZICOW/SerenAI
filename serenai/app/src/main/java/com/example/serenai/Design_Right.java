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
    private int[] mImgIds;
    private LinearLayout designRight;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.design_right, container, false);

        LinearLayout designRight = view.findViewById(R.id.horizontalScrollViewItemContainer);

        int[] mImgIds = { R.drawable.a, R.drawable.b, R.drawable.c,R.drawable.d,
                R.drawable.e};

        for (int mImgId : mImgIds) {
            ImageView newImg = new ImageView(getContext());
            newImg.setImageResource(mImgId);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(12, 0, 12, 0);
            newImg.setLayoutParams(params);
            designRight.addView(newImg);

        }
        return  view;
    }
}