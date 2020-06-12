package com.example.SE_PA.MVC;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.SE_PA.MVC.Interface.IController;
import com.example.SE_PA.MVC.Interface.IModel;
import com.example.SE_PA.MVC.Interface.IView;
import com.example.SE_PA.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {

    private TextView textView;
    private EditText editText;
    private TextView tip;
    private Button btn;

    private IModel model = new Model();
    private IController controller = new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);
        // 设置可滚动
        textView.setMovementMethod(new ScrollingMovementMethod());
        editText = findViewById(R.id.edit);
        tip = findViewById(R.id.tip);
        btn = findViewById(R.id.btn);

        controller.setModel(model);
        model.setView(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.clearData();
                Map<String, String> map = new HashMap(){{
                    put("input", editText.getText().toString());
                }};
                controller.onDataChange(map);
            }
        });
    }

    @Override
    public void setController(IController controller) {
        this.controller = controller;
    }

    @Override
    public void dataHanding() {
        tip.setText("waiting request......");
    }

    @Override
    public void onDataHandled(Map<String, String> data) {
        if (TextUtils.isEmpty(data.get("result"))) {
            tip.setText("request error");
        } else {
            textView.setText(data.get("result"));
            tip.setText("request successful");
        }


    }
}