package com.javarush.task.task36.task3608_MVC.view;

import com.javarush.task.task36.task3608_MVC.controller.Controller;
import com.javarush.task.task36.task3608_MVC.model.ModelData;

public interface View {
    public void refresh(ModelData modelData);
    public void setController(Controller controller);
}
