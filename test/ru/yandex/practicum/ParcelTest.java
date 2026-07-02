package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.*;
import java.util.List;

class ParcelBoxTest {

    @Test
    void addParcelFitsWithinMaxWeight() {
        ParcelBox<StandardParcel> box = new ParcelBox<>("Город", 10);
        StandardParcel p = new StandardParcel("Коробка", 5, "ул. Ленина, 8", 3);
        Assertions.assertTrue(box.addParcel(p));
        List<StandardParcel> contents = box.getAllParcels();
        Assertions.assertEquals(1, contents.size());
        Assertions.assertSame(p, contents.get(0));
    }

    @Test
    void addParcelExceedsMaxWeight() {
        ParcelBox<StandardParcel> box = new ParcelBox<>("Город", 5);
        StandardParcel p1 = new StandardParcel("Лёгкая", 3, "ул. Мира, 5", 8);
        StandardParcel p2 = new StandardParcel("Тяжёлая", 3, "ул. Яровая, 8", 7);

        Assertions.assertTrue(box.addParcel(p1));
        Assertions.assertFalse(box.addParcel(p2));

        List<StandardParcel> contents = box.getAllParcels();
        Assertions.assertEquals(1, contents.size());
        Assertions.assertSame(p1, contents.get(0));
    }

    @Test
    void addParcelBoundaryWeight() {
        ParcelBox<StandardParcel> box = new ParcelBox<>("Город", 5);
        StandardParcel p = new StandardParcel("Ровно", 5, "ул. Равная, 1", 1);

        Assertions.assertTrue(box.addParcel(p));
        Assertions.assertEquals(5, box.getCurrentWeight());
    }

}