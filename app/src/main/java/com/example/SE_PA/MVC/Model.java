package com.example.SE_PA.MVC;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.SE_PA.CCallback;
import com.example.SE_PA.MVC.Interface.IModel;
import com.example.SE_PA.MVC.Interface.IView;
import com.example.SE_PA.OkHttpSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Model implements IModel {
    private IView view = null;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void handleData(Map<String, String> data) {
        if (TextUtils.isEmpty(data.get("input"))) {
            return;
        }
        view.dataHanding();
        handler.removeCallbacksAndMessages(null);

        String url ="https://www.metaweather.com/api/location/"+data.get("input");
        OkHttpSingleton.getInstance().asyncGet(url, new CCallback() {
            @Override
            public void todo(final String str) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap(){{
                            put("result", json2string(str));
                        }};
                        view.onDataHandled(map);
                    }
                }, 0);
            }
        });
    }

    @Override
    public void clearData() {
        handler.removeCallbacksAndMessages(null);
        view.onDataHandled(new HashMap<String, String>(){{put("clear","");}});
    }

    // 解析json
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
