FROM hseeberger/scala-sbt:11.0.12_1.5.5_2.13.8
WORKDIR /app
COPY . /app
RUN sbt assembly
EXPOSE 9091
CMD ["java", "-jar", "target/scala-2.13/FinancialTransactionsService-assembly-0.1.0-SNAPSHOT.jar"]
