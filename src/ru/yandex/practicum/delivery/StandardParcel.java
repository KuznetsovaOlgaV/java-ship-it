package ru.yandex.practicum.delivery;

public class StandardParcel extends Parcel {
    private static final int BASE_COST = 2;

    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        printPackaged();
    }

    @Override
    public void deliver() {
        System.out.println("Посылка " + description + " доставлена по адресу " + deliveryAddress);
    }

    @Override
    protected int getBaseCostPerKg() {
        return BASE_COST;
    }

}