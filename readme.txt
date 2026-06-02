Run micro services
mvn -f score/pom.xml spring-boot:run
mvn -f health/pom.xml spring-boot:run


mvn clean install
mvn exec:exec