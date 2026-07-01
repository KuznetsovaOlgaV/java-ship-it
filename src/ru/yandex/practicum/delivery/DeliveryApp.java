package ru.yandex.practicum.delivery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);

    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>("Москва", 50);
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>("Санкт-Петербург", 30);
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>("Казань", 40);

    private static final List<Trackable> trackableItems = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    handleTracking();
                    break;
                case 5:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Обновить статус трекинга");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.print("Тип посылки (standard/fragile/perishable): ");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.print("Описание: ");
        String description = scanner.nextLine();

        System.out.print("Вес (кг): ");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.print("Адрес доставки: ");
        String address = scanner.nextLine();

        System.out.print("День отправки: ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case "standard": {
                StandardParcel p = new StandardParcel(description, weight, address, sendDay);
                standardBox.addParcel(p);
                break;
            }
            case "fragile": {
                FragileParcel p = new FragileParcel(description, weight, address, sendDay);
                fragileBox.addParcel(p);
                trackableItems.add(p);
                break;
            }
            case "perishable": {
                System.out.print("Срок годности (дней): ");
                int timeToLive = Integer.parseInt(scanner.nextLine());
                PerishableParcel p = new PerishableParcel(description, weight, address, sendDay, timeToLive);
                perishableBox.addParcel(p);
                break;
            }
            default:
                System.out.println("Неизвестный тип посылки.");
        }
    }

    private static void sendParcels() {
        sendBox(standardBox);
        sendBox(fragileBox);
        sendBox(perishableBox);
    }

    private static <T extends Parcel> void sendBox(ParcelBox<T> box) {
        for (T p : box.getAllParcels()) {
            p.packageItem();
            p.deliver();
        }
    }

    private static void calculateCosts() {
        double total = 0.0;
        total += sumCosts(standardBox.getAllParcels());
        total += sumCosts(fragileBox.getAllParcels());
        total += sumCosts(perishableBox.getAllParcels());
        System.out.println("Общая стоимость доставки: " + total);
    }

    private static double sumCosts(List<? extends Parcel> parcels) {
        double sum = 0.0;
        for (Parcel p : parcels) {
            sum += p.calculateDeliveryCost();
        }
        return sum;
    }

    private static void handleTracking() {
        System.out.print("Новое местоположение: ");
        String location = scanner.nextLine();
        for (Trackable item : trackableItems) {
            item.reportStatus(location);
        }
    }

    private static void showBoxContents() {
        System.out.print("Какую коробку показать (standard/fragile/perishable): ");
        String type = scanner.nextLine().trim().toLowerCase();

        switch (type) {
            case "standard":
                printBoxContents(
                        standardBox.getAllParcels(),
                        "Стандартная коробка",
                        standardBox.getMaxWeight(),
                        standardBox.getDestinationCity()
                );
                break;
            case "fragile":
                printBoxContents(
                        fragileBox.getAllParcels(),
                        "Хрупкая коробка",
                        fragileBox.getMaxWeight(),
                        fragileBox.getDestinationCity()
                );
                break;
            case "perishable":
                printBoxContents(
                        perishableBox.getAllParcels(),
                        "Скоропортящаяся коробка",
                        perishableBox.getMaxWeight(),
                        perishableBox.getDestinationCity()
                );
                break;
            default:
                System.out.println("Неизвестная коробка.");
        }
    }

    private static <T extends Parcel> void printBoxContents(
            List<T> parcels,
            String boxName,
            int maxWeight,
            String destinationCity
    ) {
        System.out.println(boxName + ":");
        System.out.println("  Город назначения: " + destinationCity);
        System.out.println("  Максимальный вес: " + maxWeight + " кг");

        if (parcels.isEmpty()) {
            System.out.println("  (пусто)");
            return;
        }

        for (T p : parcels) {
            String status = "";
            if (p instanceof PerishableParcel perishable) {
                int currentDay = LocalDate.now().getDayOfMonth();
                status = perishable.isExpired(currentDay) ? " [Срок вышел]" : " [Срок не вышел]";
            }
            System.out.println("  - " + p.description + " (" + p.weight + " кг)" + status);
        }
    }


}