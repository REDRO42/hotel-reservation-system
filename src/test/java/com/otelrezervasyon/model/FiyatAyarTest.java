package com.otelrezervasyon.model;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FiyatAyarTest {
    private FiyatAyar fiyatAyar;

    @BeforeEach
    void setUp() {
        fiyatAyar = new FiyatAyar("Standart", new BigDecimal("500.00"));
    }

    @Test
    void testFiyatAyarOlusturma() {
        assertNotNull(fiyatAyar);
        assertEquals("Standart", fiyatAyar.getOdaTipi());
        assertEquals(new BigDecimal("500.00"), fiyatAyar.getGunlukFiyat());
    }

    @Test
    void testOdaTipiDegistirme() {
        fiyatAyar.setOdaTipi("Deluxe");
        assertEquals("Deluxe", fiyatAyar.getOdaTipi());
    }

    @Test
    void testGunlukFiyatDegistirme() {
        fiyatAyar.setGunlukFiyat(new BigDecimal("750.00"));
        assertEquals(new BigDecimal("750.00"), fiyatAyar.getGunlukFiyat());
    }

    @Test
    void testFiyatAyarIdDegistirme() {
        fiyatAyar.setFiyatAyarId(1);
        assertEquals(1, fiyatAyar.getFiyatAyarId());
    }

    @Test
    void testToString() {
        fiyatAyar.setFiyatAyarId(1);
        String beklenenString = "FiyatAyar{fiyatAyarId=1, odaTipi='Standart', gunlukFiyat=500.00}";
        assertEquals(beklenenString, fiyatAyar.toString());
    }
} 