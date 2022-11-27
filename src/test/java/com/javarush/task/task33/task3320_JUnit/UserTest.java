package com.javarush.task.task33.task3320_JUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {
    private User user;
    private User user1;
    private User user2;
    private User userNotAdd;
    private User userNotAdd1;

    @Before
    public void setUp() throws Exception {
        user = new User("Евгений", 35, Sex.MALE);
        user1 = new User("Марина", 34, Sex.FEMALE);
        user2 = new User("Алина", 7, Sex.FEMALE);

        userNotAdd = new User("", 0, null);
        userNotAdd1 = new User(null, 0, null);
    }

    @Test
    public void newUser_EMPTY_NAME() {
        for (User user : User.getAllUsers()){
            if (user.getName() != null && user.getName().isEmpty()) {
                Assert.fail("Попытка создания пользователя с пустым именем");
            }
        }
    }

    @Test
    public void newUser_AGE_ZERO() {
        for (User user : User.getAllUsers()) {
            if (user.getAge() <= 0) {
                Assert.fail("Попытка создания пользователя c не допустимым возрастом");
            }
        }
    }

    @Test
    public void newUser_SEX_NO_NULL() {
        for (User user : User.getAllUsers()) {
            if (user.getSex() == null) {
                Assert.fail("Попытка создания пользователя с указанием пола = null");
            }
        }
    }

    @Test
    public void getAllUsers() {
        //создаем список actual и заполняем его данными нашего метода
        List<User> actual = User.getAllUsers();

        //создаем список expected в него помещаем данные для сравнения
        //то что мы предпологиаем метод должен вернуть
        List<User> expected = new ArrayList<>();
        expected.add(user);
        expected.add(user1);
        expected.add(user2);

        //запускаем тест, в случае если список expected и actual не будут равны
        //тест будет провален, о результатах теста читаем в консоли
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllUsers_NO_NULL() {
        //добавим проверку на null
        List<User> actual = User.getAllUsers();
        Assert.assertNotNull(actual);
    }

    @Test
    public void getAllUsers_MALE() {
        List<User> actual = User.getAllUsers(Sex.MALE);
        List<User> expected = new ArrayList<>();
        expected.add(user);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllUsers_MALE_NO_NULL() {
        //добавим проверку на null
        List<User> actual = User.getAllUsers(Sex.MALE);
        Assert.assertNotNull(actual);
    }

    @Test
    public void getAllUsers_FEMALE() {
        List<User> actual = User.getAllUsers(Sex.FEMALE);
        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllUsers_FEMALE_NO_NULL() {
        //добавим проверку на null
        List<User> actual = User.getAllUsers(Sex.FEMALE);
        Assert.assertNotNull(actual);
    }

    @Test
    public void getHowManyUsers() {
        int actual = User.getHowManyUsers();
        int expected = 3;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHowManyUsers_MALE() {
        int actual = User.getHowManyUsers(Sex.MALE);
        int expected = 1;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHowManyUsers_FEMALE() {
        int actual = User.getHowManyUsers(Sex.FEMALE);
        int expected = 2;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllAgeUsers() {
        int actual = User.getAllAgeUsers();
        int expected = 35 + 34 + 7;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllAgeUsers_MALE() {
        int actual = User.getAllAgeUsers(Sex.MALE);
        int expected = 35;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllAgeUsers_FEMALE() {
        int actual = User.getAllAgeUsers(Sex.FEMALE);
        int expected = 34 + 7;
        Assert.assertEquals(expected, actual);
    }
}
