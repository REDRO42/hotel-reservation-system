package com.otelrezervasyon.model;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RezervasyonTest {
    private Rezervasyon rezervasyon;
    private Date girisTarihi;
    private Date cikisTarihi;

    @BeforeEach
    void setUp() {
        rezervasyon = new Rezervasyon();
        girisTarihi = new Date();
        cikisTarihi = new Date(System.currentTimeMillis() + 86400000); // 1 gün sonrası
    }

    @Test
    void testRezervasyonOlusturma() {
        rezervasyon.setMusteriAdi("Ahmet Yılmaz");
        rezervasyon.setMusteriEmail("ahmet@example.com");
        rezervasyon.setMusteriTelefon("5551234567");
        rezervasyon.setGirisTarihi(girisTarihi);
        rezervasyon.setCikisTarihi(cikisTarihi);
        rezervasyon.setOdaNumarasi(101);
        rezervasyon.setOdaTipi("Standart");
        rezervasyon.setToplamFiyat(1000.0);
        rezervasyon.setDurum("Onaylandı");

        assertEquals("Ahmet Yılmaz", rezervasyon.getMusteriAdi());
        assertEquals("ahmet@example.com", rezervasyon.getMusteriEmail());
        assertEquals("5551234567", rezervasyon.getMusteriTelefon());
        assertEquals(girisTarihi, rezervasyon.getGirisTarihi());
        assertEquals(cikisTarihi, rezervasyon.getCikisTarihi());
        assertEquals(101, rezervasyon.getOdaNumarasi());
        assertEquals("Standart", rezervasyon.getOdaTipi());
        assertEquals(1000.0, rezervasyon.getToplamFiyat());
        assertEquals("Onaylandı", rezervasyon.getDurum());
    }

    @Test
    void testRezervasyonDurumuDegistirme() {
        rezervasyon.setDurum("İptal Edildi");
        assertEquals("İptal Edildi", rezervasyon.getDurum());
    }

    @Test
    void testRezervasyonFiyatDegistirme() {
        rezervasyon.setToplamFiyat(1500.0);
        assertEquals(1500.0, rezervasyon.getToplamFiyat());
    }

    @Test
    void testToString() {
        rezervasyon.setId(1);
        rezervasyon.setMusteriAdi("Ahmet Yılmaz");
        rezervasyon.setMusteriEmail("ahmet@example.com");
        rezervasyon.setMusteriTelefon("5551234567");
        rezervasyon.setGirisTarihi(girisTarihi);
        rezervasyon.setCikisTarihi(cikisTarihi);
        rezervasyon.setOdaNumarasi(101);
        rezervasyon.setOdaTipi("Standart");
        rezervasyon.setToplamFiyat(1000.0);
        rezervasyon.setDurum("Onaylandı");

        String beklenenString = String.format(
            "Rezervasyon{id=1, musteriAdi='Ahmet Yılmaz', musteriEmail='ahmet@example.com', " +
            "musteriTelefon='5551234567', girisTarihi=%s, cikisTarihi=%s, odaNumarasi=101, " +
            "odaTipi='Standart', toplamFiyat=1000.0, durum='Onaylandı'}", 
            girisTarihi, cikisTarihi
        );
        
        assertEquals(beklenenString, rezervasyon.toString());
    }
} 