package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.Parcel;
import ru.yandex.practicum.delivery.PerishableParcel;
import ru.yandex.practicum.delivery.StandardParcel;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryCostTest {

    @Test
    void standardParcelCost() {
        Parcel p = new StandardParcel("Книга", 5, "ул. Ленина", 1);
        assertEquals(10.0, p.calculateDeliveryCost(), 0.001);
    }

    @Test
    void fragileParcelCost() {
        Parcel p = new FragileParcel("Сервис", 3, "ул. Мира", 2);
        assertEquals(12.0, p.calculateDeliveryCost(), 0.001);
    }

    @Test
    void perishableParcelCost() {
        Parcel p = new PerishableParcel("Тумба", 2, "ул. Деревянная", 3, 2);
        assertEquals(6.0, p.calculateDeliveryCost(), 0.001);
    }

    @Test
    void zeroWeightCost() {
        Parcel p = new StandardParcel("Письмо", 0, "ул. Невесомая", 1);
        assertEquals(0.0, p.calculateDeliveryCost(), 0.001);
    }

    @Test
    void isExpiredNotExpired() {
        PerishableParcel p = new PerishableParcel("Фрукты", 1, "ул. Фруктовая", 10, 5);
        assertFalse(p.isExpired(14)); // срок не вышел
    }

    @Test
    void isExpiredExpired() {
        PerishableParcel p = new PerishableParcel("Фрукты", 1, "ул. Фруктовая", 10, 5);
        assertTrue(p.isExpired(16)); // срок вышел
    }
}