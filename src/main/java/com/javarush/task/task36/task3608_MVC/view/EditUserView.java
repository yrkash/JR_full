package com.javarush.task.task36.task3608_MVC.view;

import com.javarush.task.task36.task3608_MVC.bean.User;
import com.javarush.task.task36.task3608_MVC.controller.Controller;
import com.javarush.task.task36.task3608_MVC.model.ModelData;

public class EditUserView implements View {
    private Controller controller;
    @Override
    public void refresh(ModelData modelData) {
        System.out.println("User to be edited:");
        User activeUser = modelData.getActiveUser();
        System.out.println("\t"+activeUser);
        System.out.println("===================================================");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;

    }
    public void fireEventUserDeleted(long id) {
        controller.onUserDelete(id);
    }

    public void fireEventUserChanged(String name, long id, int level) { controller.onUserChange(name,id,level);}

}
