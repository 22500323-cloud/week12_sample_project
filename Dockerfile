FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

CMD ["sh", "-c", "java -jar $(find build/libs -maxdepth 1 -name '*.jar' ! -name '*-plain.jar' | head -n 1)"]
