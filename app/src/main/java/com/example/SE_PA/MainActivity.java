package com.example.SE_PA;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button btn;
    private TextView tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定TextView
        textView = findViewById(R.id.tv);
        // 设置可滚动
        textView.setMovementMethod(new ScrollingMovementMethod());
        editText = findViewById(R.id.edit);
        btn = findViewById(R.id.btn);
        tip = findViewById(R.id.tip);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip.setText("waiting request......");
                requestAndSetData(editText.getText().toString());
            }
        });
    }

    private void requestAndSetData(String locId) {
        String url ="https://www.metaweather.com/api/location/"+locId;
        OkHttpSingleton.getInstance().asyncGet(url, new CCallback() {
            @Override
            public void todo(final String str) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            tip.setText("request error");
                        } else {
                            textView.setText(json2string(str));
                            tip.setText("request successful");
                        }
                    }
                });
            }
        });

    }

    private String json2string(String jsonstr) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            JSONArray weather = jsonObject.getJSONArray("consolidated_weather");
            for (int i=0;i<weather.length();i++){
                JSONObject obj = weather.getJSONObject(i);
                String date = obj.getString("applicable_date");
                String weather_state_name = obj.getString("weather_state_name");
                String max_temp = obj.getString("max_temp");
                String the_temp = obj.getString("the_temp");
                String min_temp = obj.getString("min_temp");
                String wind_direction_compass = obj.getString("wind_direction_compass");
                String wind_speed = obj.getString("wind_speed");
                String air_pressure = obj.getString("air_pressure");
                String humidity = obj.getString("humidity");
                String visibility = obj.getString("visibility");
                String predictability = obj.getString("predictability");

                stringBuilder.append(date+"\n");
                stringBuilder.append(weather_state_name+"\n");
                stringBuilder.append("当前温度："+the_temp+"\n");
                stringBuilder.append("最高温："+max_temp+"\n");
                stringBuilder.append("最低温："+min_temp+"\n");
                stringBuilder.append("风向："+wind_direction_compass+"\n");
                stringBuilder.append("风速："+wind_speed+"\n");
                stringBuilder.append("气压："+air_pressure+"\n");
                stringBuilder.append("湿度："+humidity+"\n");
                stringBuilder.append("可见度："+visibility+"\n");
                stringBuilder.append("预测准确率："+predictability+"\n");
                stringBuilder.append("\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}