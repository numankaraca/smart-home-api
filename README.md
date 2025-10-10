# Akıllı Ev/Yurt Odası Yönetim Sistemi API

Bu proje, bir akıllı ev veya yurt odasındaki cihazları (lambalar, prizler, sensörler vb.) yönetmek için geliştirilmiş bir REST API sunucusudur. Proje, odalar ve cihazlar arasında ilişkisel bir yapı kurarak, cihazların odalara göre yönetilmesine olanak tanır.

Bu proje, 4. sınıf bir yazılım mühendisliği öğrencisi tarafından, Gemini'nin teknik mentorluğunda, modern backend geliştirme pratikleri kullanılarak adım adım geliştirilmiştir.

---

## 🚀 Özellikler (Features)

- **Oda Yönetimi:** Oda ekleme, silme, güncelleme ve listeleme (Tam CRUD).
- **Cihaz Yönetimi:** Cihaz ekleme, silme, güncelleme ve listeleme (Tam CRUD).
- **İlişkisel Yapı:** Cihazların belirli odalara atanması ve bir odaya ait cihazların listelenmesi.
- **Akıllı Arama:** Cihazları durumlarına (`status`) veya isme göre (`name`, büyük/küçük harf duyarsız) arama.
- **Veri Doğrulama (Validation):** API'ye gelen verilerin (örn: boş isim) geçersiz olmasını engelleme.
- **Profesyonel Hata Yönetimi:** Kaynak bulunamadığında (`404`) veya geçersiz veri gönderildiğinde (`400`) standart ve anlaşılır JSON hata mesajları döndürme.
- **API Dokümantasyonu:** Swagger (OpenAPI) ile otomatik olarak oluşturulan interaktif API dokümantasyonu.
- **Birim Testleri (Unit Tests):** Servis katmanının temel iş mantığının JUnit 5 ve Mockito ile test edilmesi.

---

## 🛠️ Kullanılan Teknolojiler

- **Dil:** Java 17+
- **Çatı (Framework):** Spring Boot 3.x
- **Veri Erişimi:** Spring Data JPA (Hibernate)
- **Veritabanı (Geliştirme):** H2 In-Memory Database
- **API Dokümantasyonu:** springdoc-openapi (Swagger 3)
- **Test:** JUnit 5, Mockito, AssertJ
- **Bağımlılık Yönetimi:** Maven

---

## 🏁 Projeyi Yerel Olarak Çalıştırma

Projeyi kendi bilgisayarınızda çalıştırmak için aşağıdaki adımları izleyebilirsiniz:

1.  **Projeyi Klonlayın:**
    ```bash
    git clone [https://github.com/KULLANICI_ADIN/smart-home-api.git](https://github.com/KULLANICI_ADIN/smart-home-api.git)
    ```
    *(Not: `KULLANICI_ADIN` kısmını kendi GitHub kullanıcı adınızla değiştirmeyi unutmayın.)*

2.  **Projeyi IntelliJ IDEA'da Açın:**
    IntelliJ IDEA'yı açın ve klonladığınız `smart-home-api` klasörünü proje olarak açın. Maven bağımlılıklarının otomatik olarak indirilmesini bekleyin.

3.  **Uygulamayı Çalıştırın:**
    `src/main/java/com/example/smarthomeapi/SmartHomeApiApplication.java` sınıfını bulun ve `main` metodunu çalıştırın. Uygulama `8080` portunda başlayacaktır.

---

## 📖 API Dokümantasyonu ve Kullanımı

Uygulama çalışmaya başladıktan sonra, tüm API endpoint'lerini görmek ve interaktif olarak test etmek için aşağıdaki adresi tarayıcınızda açabilirsiniz:

[**Swagger UI: http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)