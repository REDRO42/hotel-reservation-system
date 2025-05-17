CREATE DATABASE IF NOT EXISTS otel_rezervasyon;
USE otel_rezervasyon;

CREATE TABLE IF NOT EXISTS odalar (
    oda_id INT PRIMARY KEY AUTO_INCREMENT,
    oda_numarasi INT NOT NULL UNIQUE,
    oda_tipi VARCHAR(50) NOT NULL,
    kapasite INT NOT NULL,
    eklenme_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS rezervasyonlar (
    id INT PRIMARY KEY AUTO_INCREMENT,
    musteri_adi VARCHAR(100) NOT NULL,
    musteri_email VARCHAR(100) NOT NULL,
    musteri_telefon VARCHAR(20) NOT NULL,
    giris_tarihi DATE NOT NULL,
    cikis_tarihi DATE NOT NULL,
    oda_numarasi INT NOT NULL,
    oda_tipi VARCHAR(50) NOT NULL,
    toplam_fiyat DECIMAL(10,2) NOT NULL,
    durum VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS fiyat_ayarlari (
    fiyat_ayar_id INT PRIMARY KEY AUTO_INCREMENT,
    oda_tipi VARCHAR(50) NOT NULL UNIQUE, -- Standart, Deluxe, Suite, Aile (Benzersiz olmalı)
    gunluk_fiyat DECIMAL(10,2) NOT NULL,
    guncellenme_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Örnek Fiyat Ayarları (Admin panelinden yönetilecek)
-- INSERT INTO fiyat_ayarlari (oda_tipi, gunluk_fiyat) VALUES ('Standart', 500.00);
-- INSERT INTO fiyat_ayarlari (oda_tipi, gunluk_fiyat) VALUES ('Deluxe', 750.00);
-- INSERT INTO fiyat_ayarlari (oda_tipi, gunluk_fiyat) VALUES ('Suite', 1000.00);
-- INSERT INTO fiyat_ayarlari (oda_tipi, gunluk_fiyat) VALUES ('Aile', 1200.00); 