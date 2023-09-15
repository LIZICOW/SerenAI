package com.example.serenai;

import static java.sql.DriverManager.println;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Design_Left extends Fragment {
    private Button btn_test = null;

    SeekBar seekBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.design_left, container, false);
        LinearLayout designRight = view.findViewById(R.id.skbr);

        seekBar = view.findViewById(R.id.aseek);
        seekBar.setMax(100);
        seekBar.setProgress(50);

        // 添加布局完成监听器
        ViewTreeObserver viewTreeObserver = seekBar.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 获取SeekBar的宽度
                int seekBarWidth = seekBar.getWidth();

                // 移除监听器，避免重复调用
                seekBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // 创建线性渐变
                LinearGradient test = new LinearGradient(
                        0.f, 0.f,seekBarWidth, 0.0f,
                        new int[] { 0xFFFF0000, 0xFFFFFF00, 0xFF00BD0D, 0xFF00C9BF,
                                0xFF0000B5, 0xFFC91CC1, 0xFFFF0000 },
                        null, Shader.TileMode.CLAMP);
                // 创建ShapeDrawable并设置渐变颜色
                ShapeDrawable shape = new ShapeDrawable(new RectShape());
                shape.getPaint().setShader(test);

                // 设置渐变色的ShapeDrawable为SeekBar的进度背景
                seekBar.setProgressDrawable(shape);
            }
        });

        // 添加seekbar点击监视器
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 当开始拖动SeekBar时触发
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 当停止拖动SeekBar时触发
            }
        });

        return  view;
    }


}