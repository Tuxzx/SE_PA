package com.example.SE_PA.MVC;

import com.example.SE_PA.MVC.Interface.IController;
import com.example.SE_PA.MVC.Interface.IModel;

import java.util.Map;

public class Controller implements IController {

    private IModel model = null;

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void onDataChange(Map<String, String> data) {
        model.handleData(data);
    }

    @Override
    public void clearData() {
        model.clearData();
    }
}
