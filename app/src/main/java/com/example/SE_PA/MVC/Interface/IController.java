package com.example.SE_PA.MVC.Interface;

import java.util.Map;

public interface IController {
    void setModel(IModel model);
    void onDataChange(Map<String, String> data);
    void clearData();
}
