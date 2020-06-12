package com.example.SE_PA;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpSingleton {

    private static OkHttpSingleton instance;
    private static OkHttpClient client = new OkHttpClient();

    private OkHttpSingleton(){}

    public static synchronized OkHttpSingleton getInstance() {
        if (instance == null) {
            instance = new OkHttpSingleton();
        }
        return instance;
    }

    public String syncGet(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void asyncGet(String url, final CCallback callback){
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    callback.todo(responseBody.string());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
