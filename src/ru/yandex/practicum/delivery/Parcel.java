package ru.yandex.practicum.delivery;

public abstract class Parcel {
    protected String description;
    protected int weight;
    protected String deliveryAddress;
    protected int sendDay;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    protected void printPackaged() {
        System.out.println("Посылка " + description + " упакована");
    }

    protected abstract int getBaseCostPerKg();

    public double calculateDeliveryCost() {
        return weight * getBaseCostPerKg();
    }

    public abstract void packageItem();
    public abstract void deliver();
}