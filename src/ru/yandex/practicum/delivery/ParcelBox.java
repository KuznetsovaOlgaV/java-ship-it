package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private final List<T> parcels = new ArrayList<>();
    private final String destinationCity;
    private final int maxWeight;
    private int currentWeight;

    public ParcelBox(String destinationCity, int maxWeight) {
        this.destinationCity = destinationCity;
        this.maxWeight = maxWeight;
        this.currentWeight = 0;
    }

    public boolean addParcel(T parcel) {
        if (currentWeight + parcel.weight > maxWeight) {
            System.out.println("Предупреждение: не удалось добавить посылку " + parcel.description
                    + " — превышен максимальный вес коробки.");
            return false;
        }
        parcels.add(parcel);
        currentWeight += parcel.weight;
        return true;
    }

    public List<T> getAllParcels() {
        return Collections.unmodifiableList(parcels);
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

}