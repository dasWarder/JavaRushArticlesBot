FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=test.javaRushArticlesBot
ENV BOT_TOKEN=1699786878:AAH36i07nPAhV7ke41a2aBadflxu3Nhv7TI
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dbot.name=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar","/app.jar"]