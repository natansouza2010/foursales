# Usando a imagem oficial do OpenJDK 17
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR gerado pelo Spring Boot para dentro do container
COPY build/libs/auth-service-foursales-system-0.0.1-SNAPSHOT.jar auth-service.jar

# Expõe a porta do serviço
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
