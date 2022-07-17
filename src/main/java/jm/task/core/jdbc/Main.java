package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        final byte age = 10;
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Vasya", "Get", age);
        userService.saveUser("Vanya", "Kat", (byte) 52);
        userService.saveUser("Lora", "Get", (byte) 48);
        userService.saveUser("Olya", "Kat", (byte) 47);

        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
