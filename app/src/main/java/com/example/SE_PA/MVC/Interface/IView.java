package com.example.SE_PA.MVC.Interface;

        import java.util.Map;

public interface IView {
    void setController(IController controller);
    void dataHanding();
    void onDataHandled(Map<String, String> data);
}
