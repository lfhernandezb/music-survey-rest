Construcción de los artefactos

Generar librería

cd library
mvn clean
mvn package
mvn install

Generar discovery server (eureka)

cd discovery
mvn clean
mvn package

Generar microservicio "musicstyle-service"

cd music-style
mvn clean
mvn package

Generar microservicio "survey-service"

cd survey
mvn clean
mvn package

Generar microservicio "score-service"

cd score
mvn clean
mvn package

Despliegue

Por simplicidad se generan jar's

java -jar discovery/target/discovery-0.0.1-SNAPSHOT.jar -b:0.0.0.0

java -jar music-style/target/music-style-0.0.1-SNAPSHOT.jar -b:0.0.0.0

java -jar survey/target/survey-0.0.1-SNAPSHOT.jar -b:0.0.0.0

java -jar score/target/score-0.0.1-SNAPSHOT.jar -b:0.0.0.0






