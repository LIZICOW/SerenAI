package com.example.serenai;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

//        seekBar = new SeekBar(getContext());
        seekBar = view.findViewById(R.id.aseek);
        seekBar.setMax(100);
        seekBar.setProgress(50);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 使用渐变色来设置SeekBar的颜色
                LinearGradient test = new LinearGradient(0.f, 0.f, seekBar.getWidth(), 0.0f,
                        new int[] { 0xFFFF0000, 0xFFFFFF00, 0xFF00BD0D, 0xFF00C9BF,
                                0xFF0000B5, 0xFFC91CC1, 0xFFFF0000},
                        null, Shader.TileMode.CLAMP);
                ShapeDrawable shape = new ShapeDrawable(new RectShape());
                shape.getPaint().setShader(test);
                seekBar.setProgressDrawable(shape);

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