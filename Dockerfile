# Temel alınacak resim belirtilir, burada Java 17 sürümünü kullanıyorum
FROM openjdk:17-jdk


  # Uygulama jar dosyasını container içine kopyala
COPY build/libs/projectt2-0.0.1-SNAPSHOT.jar app.jar

  # Uygulamayı çalıştır
CMD ["java", "-jar", "app.jar"]
