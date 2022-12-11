package com.javarush.task.task36.task3608_MVC.model;

import com.javarush.task.task36.task3608_MVC.bean.User;
import com.javarush.task.task36.task3608_MVC.model.service.UserService;
import com.javarush.task.task36.task3608_MVC.model.service.UserServiceImpl;

import java.util.List;

public class MainModel implements Model {

    private ModelData modelData = new ModelData();
    private UserService userService = new UserServiceImpl();


    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadUsers() {
        modelData.setDisplayDeletedUserList(false);
        //List<User> loadList = new ArrayList<>();
        //loadList = userService.getUsersBetweenLevels(1,100);
        modelData.setUsers(getAllUsers());
    }

    private List<User> getAllUsers() {
        List<User> allUsers = userService.getUsersBetweenLevels(1,100);
        return userService.filterOnlyActiveUsers(allUsers);
    }
    @Override
    public void loadDeletedUsers() {
        modelData.setDisplayDeletedUserList(true);
        List<User> users = userService.getAllDeletedUsers();
        modelData.setUsers(users);
    }

    public void loadUserById(long userId) {
        User user = userService.getUsersById(userId);
        modelData.setActiveUser(user);
    }

    @Override
    public void deleteUserById(long id) {
        userService.deleteUser(id);
        modelData.setUsers(getAllUsers());
    }

    public void changeUserData(String name, long id, int level) {
        userService.createOrUpdateUser(name, id, level);
        modelData.setUsers(getAllUsers());

    }
}
