package com.javarush.task.task36.task3608_MVC;

import com.javarush.task.task36.task3608_MVC.controller.Controller;
import com.javarush.task.task36.task3608_MVC.model.MainModel;
import com.javarush.task.task36.task3608_MVC.model.Model;
import com.javarush.task.task36.task3608_MVC.view.EditUserView;
import com.javarush.task.task36.task3608_MVC.view.UsersView;

public class Solution {
    public static void main(String[] args) {
        Model model = new MainModel();
        UsersView usersView = new UsersView();
        EditUserView editUserView =new EditUserView();
        Controller controller = new Controller();

        usersView.setController(controller);
        controller.setModel(model);
        controller.setUsersView(usersView);

        usersView.fireEventShowAllUsers();
        controller.setEditUserView(editUserView);
        usersView.fireEventOpenUserEditForm(126L);
        editUserView.setController(controller);
        editUserView.fireEventUserChanged("Sidorovich",126L,3);
        //controller.setUsersView(usersView);

        editUserView.fireEventUserDeleted(124L);
        //model.getModelData().setDisplayDeletedUserList(true);

        usersView.fireEventShowDeletedUsers();

        //controller.setUsersView(usersView);
        //usersView.fireEventShowAllUsers();

    }
}