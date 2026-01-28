FROM eclipse-temurin:17-jdk-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado pelo job anterior
COPY app/*.jar app.jar

EXPOSE 8082

# Comando de inicialização
ENTRYPOINT ["java","-jar","app.jar"]

