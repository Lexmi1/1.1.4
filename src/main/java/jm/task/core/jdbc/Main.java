package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Sasha", "Strukov", (byte) 21);
        userService.saveUser("Kris", "Rogozina", (byte) 21);
        userService.saveUser("Tanya", "Tselueva", (byte) 45);
        //получить после добавления сразу как Алишев говорил

        userService.getAllUsers().stream().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}