Trial Application
=================

Set up Gide
-----------

* Maven 3, Java 8, Mysql 5.7

* Change data source properties in application-dev.properties and application-test.properties.

* Import db.sql into mysql database.

* Import testdb.sql in test/resources into mysql database.

* run : mvn clean install

* run : java -jar target/trial-0.0.1-SNAPSHOT.jar

* Access Url : http://localhost:8080/

* You need to enter valid email type when register and login.

* Admin credentials
    
    email : admin@gammaairline.com
    password : admin
    
* PS : mvn clean install will reset the state of applicant. 
    If you want to keep state, then you need to run :  mvn clean install -DskipTests



