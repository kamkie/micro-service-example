micro-service-example
=====================

Dependencies
---------------------
* java 8
* maven 3.1+
* nodejs for webui project
* MySQL 5+

Steps for running
---------------------
### from command line using maven
1. Do `mvn install` to build project
2. Start **service-config**  
3. Start all other services
```bash
cd service-config  
mvn spring-boot:run
```

### from command line
1. Do `mvn install` to build project
2. Start **service-config**  
3. Start all other services
```bash
cd service-config/src/main/resources  
java -jar ../../../target/service-config-{version}.jar
```

### from IDE
1. Import maven project to preferred IDE (IntelliJ IDEA)
2. Start `BootConfig` class from **service-config**
3. Start all other services (Boot*.java files)

### Remarks
* Database will be created in boot process of **service-config**  
* **service-auth** use this same MySql database named `test`  
* all services need `logback.xml` and `bootstrap.yml` files from resources directory
**service-config** require also `application.yml`
these file are also part of jar file
