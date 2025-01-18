package main;

import main.models.User;
import main.models.Users;
import main.utils.UsersManager;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        User user = null;
        Users users = UsersManager.loadUsers();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Для начала работы с сервисом нужно войти или зарегистрироваться");
            System.out.println("1 - Вход");
            System.out.println("2 - Регистрация");
            System.out.println("3 - Завершение работы");

            String next = scanner.nextLine();
            switch (next) {
                case "1":
                    System.out.print("Введите userId: ");
                    String enteredUserId = scanner.nextLine();
                    for (User localUser : users.getUsers()) {
                        if (enteredUserId.equals(localUser.getUserId())) {
                            user = localUser;
                            break;
                        }
                    }
                    break;
                case "2":
                    String userId = UUID.randomUUID().toString();
                    user = new User(userId);
                    UsersManager.saveUser(user, users);
                    System.out.println("Ваш userId: " + userId + "\nСохраните его для входа!");
                    break;

                case "3":
                    return;

                default:
                    System.out.println("Введите корректную команду");
                    continue;
            }

            if (user == null) {
                System.out.println("Не удалось войти, повторите попытку");
            } else {
                break;
            }
        }

        System.out.println("Успешный вход!");
    }
}