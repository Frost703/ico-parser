This program parses all information about icos from icotracker and icorating.
It uses postgres started on localhost and stores all information with hibernate.

Required dependencies: hibernate, postgres driver, jsoup.
include this into your pom.xml

        <!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
        <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.8.3</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/postgresql/postgresql -->
        <dependency>
            <groupId>postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>9.1-901-1.jdbc4</version>
        </dependency>

        <!-- hibernate -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>3.6.3.Final</version>
        </dependency>


If you continue contributing to this project, make sure you refactor all 'void' methods and add unit tests to correspond to the best coding practices.