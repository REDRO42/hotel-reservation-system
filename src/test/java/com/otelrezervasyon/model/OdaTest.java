package com.otelrezervasyon.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OdaTest {
    private Oda oda;

    @BeforeEach
    void setUp() {
        oda = new Oda(101, "Standart", 2);
    }

    @Test
    void testOdaOlusturma() {
        assertNotNull(oda);
        assertEquals(101, oda.getOdaNumarasi());
        assertEquals("Standart", oda.getOdaTipi());
        assertEquals(2, oda.getKapasite());
    }

    @Test
    void testOdaNumarasiDegistirme() {
        oda.setOdaNumarasi(102);
        assertEquals(102, oda.getOdaNumarasi());
    }

    @Test
    void testOdaTipiDegistirme() {
        oda.setOdaTipi("Deluxe");
        assertEquals("Deluxe", oda.getOdaTipi());
    }

    @Test
    void testKapasiteDegistirme() {
        oda.setKapasite(3);
        assertEquals(3, oda.getKapasite());
    }

    @Test
    void testToString() {
        String beklenenString = "Oda{odaId=0, odaNumarasi=101, odaTipi='Standart', kapasite=2}";
        assertEquals(beklenenString, oda.toString());
    }
} 