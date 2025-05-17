# Otel Rezervasyon Sistemi

Bu proje, Java ile geliştirilmiş bir otel rezervasyon yönetim sistemidir. Swing arayüzü kullanılarak masaüstü uygulaması olarak tasarlanmıştır.

## 🚀 Özellikler

- Oda yönetimi (ekleme, düzenleme, silme)
- Rezervasyon işlemleri
- Fiyatlandırma yönetimi
- Müşteri bilgileri takibi
- Tarih bazlı rezervasyon kontrolü
- MySQL veritabanı entegrasyonu

## 🛠️ Teknolojiler

- Java 11
- Swing (GUI)
- MySQL 8.0
- Maven
- JUnit 5 (Unit Testing)
- Mockito (Test Mocking)
- Jackson (JSON işlemleri)

## 📋 Gereksinimler

- JDK 11 veya üzeri
- MySQL 8.0
- Maven 3.6 veya üzeri

## 🔧 Kurulum

1. Projeyi klonlayın:
```bash
git clone https://github.com/https://github.com/redro42/otel-rezervasyon-sistemi.git
```

2. Proje dizinine gidin:
```bash
cd otel-rezervasyon-sistemi
```

3. Maven bağımlılıklarını yükleyin:
```bash
mvn clean install
```

4. MySQL veritabanını oluşturun ve yapılandırın:
   - MySQL sunucunuzu başlatın
   - Veritabanı şemasını oluşturun
   - Bağlantı ayarlarını yapılandırın

5. Uygulamayı çalıştırın:
```bash
mvn exec:java
```

## 🧪 Testler

Projede kapsamlı unit testler bulunmaktadır. Testleri çalıştırmak için:

```bash
mvn test
```

Test kapsamı:
- Model sınıfları (Oda, Rezervasyon, FiyatAyar)
- Veritabanı işlemleri
- İş mantığı kontrolleri

## 📁 Proje Yapısı

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── otelrezervasyon/
│   │           ├── dao/         # Veritabanı erişim katmanı
│   │           ├── model/       # Veri modelleri
│   │           ├── ui/          # Kullanıcı arayüzü
│   │           └── util/        # Yardımcı sınıflar
│   └── resources/              # Kaynak dosyaları
└── test/
    └── java/
        └── com/
            └── otelrezervasyon/
                └── model/       # Unit testler
```

## 🤝 Katkıda Bulunma

1. Bu depoyu fork edin
2. Yeni bir branch oluşturun (`git checkout -b feature/yeniOzellik`)
3. Değişikliklerinizi commit edin (`git commit -am 'Yeni özellik: Açıklama'`)
4. Branch'inizi push edin (`git push origin feature/yeniOzellik`)
5. Pull Request oluşturun

## 📝 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakın.

## 👥 İletişim

Proje Sahibi - [@github_username](https://github.com/github_username)

Proje Linki: [https://github.com/github_username/otel-rezervasyon-sistemi](https://github.com/github_username/otel-rezervasyon-sistemi) 