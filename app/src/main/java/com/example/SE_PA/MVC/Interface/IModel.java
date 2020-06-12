package com.example.SE_PA.MVC.Interface;

import java.util.Map;

    public interface IModel {
        void setView(IView view);
        void handleData(Map<String, String> data);
        void clearData();
    }
