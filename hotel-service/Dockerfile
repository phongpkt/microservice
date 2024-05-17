# Sử dụng image base phù hợp
FROM openjdk:17-jdk

# Đặt thư mục làm việc
WORKDIR /app

# Copy file .jar vào container
COPY target/*.jar app.jar

# Khai báo cổng mạng
EXPOSE 8084

# Lệnh chạy ứng dụng khi container được khởi chạy
CMD ["java", "-jar", "app.jar"]
