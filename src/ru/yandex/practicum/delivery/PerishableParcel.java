package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel {
    private static final int BASE_COST = 3;
    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress,
                            int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    @Override
    public void packageItem() {
        super.packageItem();
    }

    @Override
    public void deliver() {
        System.out.println("Посылка " + description + " доставлена по адресу " + deliveryAddress);
    }

    @Override
    protected int getBaseCostPerKg() {
        return BASE_COST;
    }

    public boolean isExpired(int currentDay) {
        return (currentDay - sendDay) > timeToLive;
    }
}