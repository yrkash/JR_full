package com.javarush.task.task36.task3608_MVC.view;

import com.javarush.task.task36.task3608_MVC.bean.User;
import com.javarush.task.task36.task3608_MVC.controller.Controller;
import com.javarush.task.task36.task3608_MVC.model.ModelData;

import java.util.List;

public class UsersView implements View {
    private Controller controller;

    @Override
    public void refresh(ModelData modelData) {

        List<User> userList = modelData.getUsers();
        boolean isDisplayDeletedUserList = modelData.isDisplayDeletedUserList();
        String message = (isDisplayDeletedUserList) ? "All deleted users:" :  "All users:";
        System.out.println(message);
        for (User user: userList) {
            System.out.println("\t"+user);
        }
        System.out.println("===================================================");
    }
    public void fireEventOpenUserEditForm(long id) { controller.onOpenUserEditForm(id);}

    public void fireEventShowAllUsers() { controller.onShowAllUsers();}

    public void fireEventShowDeletedUsers() {
        controller.onShowAllDeletedUsers();
    }



    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
