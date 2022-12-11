package com.javarush.task.task20.task2002;


import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
Читаем и пишем в файл: JavaRush
*/

public class Solution {
    public static void main(String[] args) {
        //you can find your_file_name.tmp in your TMP directory or adjust outputStream/inputStream according to your file's actual location
        //вы можете найти your_file_name.tmp в папке TMP или исправьте outputStream/inputStream в соответствии с путем к вашему реальному файлу
        try {
            File yourFile = File.createTempFile("your_file_name", null);
            OutputStream outputStream = new FileOutputStream(yourFile);
            InputStream inputStream = new FileInputStream(yourFile);

            JavaRush javaRush = new JavaRush();
            User user1 = new User();
            user1.setFirstName("Yuriy");
            user1.setLastName("Mitryasov");
            user1.setBirthDate(new Date(30,10,1988));
            user1.setCountry(User.Country.RUSSIA);
            user1.setMale(true);
            User user2 = new User();
            user2.setFirstName("Mikola");
            user2.setLastName("Lashuk");
            user2.setBirthDate(new Date(31,10,1988));
            user2.setCountry(User.Country.UKRAINE);
            user2.setMale(true);
            javaRush.users.add(user1);
            javaRush.users.add(user2);

            //initialize users field for the javaRush object here - инициализируйте поле users для объекта javaRush тут
            javaRush.save(outputStream);
            outputStream.flush();

            JavaRush loadedObject = new JavaRush();
            loadedObject.load(inputStream);
            //here check that the javaRush object is equal to the loadedObject object - проверьте тут, что javaRush и loadedObject равны
            if (javaRush.equals(loadedObject)) System.out.println("Equals");
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Oops, something is wrong with my file");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Oops, something is wrong with the save/load method");
        }
    }

    public static class JavaRush {
        public List<User> users = new ArrayList<>();

        public void save(OutputStream outputStream) throws Exception {
            //implement this method - реализуйте этот метод
            PrintWriter printWriter = new PrintWriter(outputStream);
            if (!users.isEmpty()) {
                for (int i = 0; i < users.size(); i++) {
                    User buffUser = users.get(i);
                    printWriter.println(buffUser.getFirstName());
                    printWriter.println(buffUser.getLastName());
                    printWriter.println(buffUser.getBirthDate().getTime());
                    printWriter.println(buffUser.getCountry());
                    printWriter.println(buffUser.isMale());
                }
            }
            printWriter.flush();
            outputStream.flush();
        }

        public void load(InputStream inputStream) throws Exception {
            //implement this method - реализуйте этот метод
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String buffFirstName = null;
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.ENGLISH);

            while ((buffFirstName = bufferedReader.readLine()) != null) {
                User buffUser = new User();
                buffUser.setFirstName(buffFirstName);
                buffUser.setLastName(bufferedReader.readLine());
                buffUser.setBirthDate(new Date(Long.parseLong(bufferedReader.readLine())));
                buffUser.setCountry(User.Country.valueOf(bufferedReader.readLine()));
                buffUser.setMale(bufferedReader.readLine().equals("true") ? true : false);
                this.users.add(buffUser);
            }
            bufferedReader.close();
            inputStream.close();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            JavaRush javaRush = (JavaRush) o;

            return users != null ? users.equals(javaRush.users) : javaRush.users == null;

        }

        @Override
        public int hashCode() {
            return users != null ? users.hashCode() : 0;
        }
    }
}
