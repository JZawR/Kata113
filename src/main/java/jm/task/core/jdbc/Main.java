package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Vasya", "Get", (byte) 50);
        userService.saveUser("Vanya", "Kat", (byte) 52);
        userService.saveUser("Lora", "Get", (byte) 48);
        userService.saveUser("Olya", "Kat", (byte) 47);
        userService.removeUserById(1);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
