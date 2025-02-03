package com.example.demo.console;

import com.example.demo.domain.Ingredient;
import com.example.demo.domain.Recipe;
import com.example.demo.service.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Пример консольного взаимодействия,
 * запускается автоматически при старте Spring Boot (если зарегистрирован как bean).
 */
@Component
public class ConsoleRunner implements CommandLineRunner {

    private final RecipeService recipeService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleRunner(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addRecipeFlow();
                    break;
                case "2":
                    searchRecipesFlow();
                    break;
                case "3":
                    removeRecipeFlow();
                    break;
                case "0":
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неизвестный выбор!");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Меню ===");
        System.out.println("1 - Добавить рецепт");
        System.out.println("2 - Найти рецепт (по названию)");
        System.out.println("3 - Удалить рецепт");
        System.out.println("0 - Выход");
        System.out.print("Выберите команду: ");
    }

    private void addRecipeFlow() {
        System.out.print("Введите название блюда: ");
        String dish = scanner.nextLine();
        List<Ingredient> ingList = new ArrayList<>();

        while (true) {
            System.out.println("Добавить ингредиент? (y/n)");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (!answer.equals("y")) {
                break;
            }
            System.out.print("Название ингредиента: ");
            String ingName = scanner.nextLine();
            System.out.print("Количество (например, 200 г): ");
            String amount = scanner.nextLine();
            // Пока id=null, recipeId=null
            ingList.add(new Ingredient(null, null, ingName, amount));
        }

        long newId = recipeService.addRecipe(dish, ingList);
        System.out.println("Рецепт добавлен! ID=" + newId);
    }

    private void searchRecipesFlow() {
        System.out.print("Введите поисковый запрос (часть названия): ");
        String query = scanner.nextLine();
        List<Recipe> found = recipeService.searchRecipes(query);
        if (found.isEmpty()) {
            System.out.println("Ничего не найдено.");
        } else {
            System.out.println("Найдено рецептов: " + found.size());
            for (Recipe r : found) {
                System.out.println(r);
            }
        }
    }

    private void removeRecipeFlow() {
        System.out.print("Введите ID рецепта для удаления: ");
        long rid = Long.parseLong(scanner.nextLine());
        boolean removed = recipeService.removeRecipe(rid);
        if (removed) {
            System.out.println("Рецепт удалён.");
        } else {
            System.out.println("Рецепт не найден.");
        }
    }
}