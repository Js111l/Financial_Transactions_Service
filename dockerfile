FROM hseeberger/scala-sbt:11.0.5_1.3.6_2.13.1
WORKDIR /app
COPY . /app
RUN sbt assembly
EXPOSE 9091
CMD ["java", "-jar", "target/scala-2.13/FinancialTransactionsService-assembly-0.1.0-SNAPSHOT.jar"]
