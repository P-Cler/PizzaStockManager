# ---- Estágio de Build ----
# Usamos uma imagem oficial do Maven com o Java 17 para compilar o projeto
FROM maven:3.9.6-eclipse-temurin-17-focal AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia primeiro o pom.xml para aproveitar o cache de dependências do Docker
COPY pom.xml .

# Copia o resto do código do seu projeto
COPY . .

# Executa o build do Maven para gerar o arquivo .jar
# O resultado estará em /app/target/
RUN mvn clean package -DskipTests

# ---- Estágio de Execução ----
# Usamos uma imagem leve do Java 17 para rodar a aplicação, economizando espaço
FROM eclipse-temurin:17-jdk-focal

# Define o diretório de trabalho
WORKDIR /app

# Copia APENAS o arquivo .jar gerado no estágio de build para o nosso contêiner final
# IMPORTANTE: Substitua 'pizza-0.0.1-SNAPSHOT.jar' pelo nome exato do seu JAR!
COPY --from=build /app/target/pizza-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que a aplicação vai usar. O Render usa a porta 10000.
EXPOSE 10000

# Comando para iniciar a aplicação quando o contêiner rodar
ENTRYPOINT ["java", "-jar", "app.jar"]