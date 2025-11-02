# Word Game App

- **Frontend:** React, Redux Toolkit, TypeScript, Tailwind CSS  
- **Backend:** Spring Boot, Java  
- **Data:** In-memory list of words  

## Setup & Running
To run backend:
mvn spring-boot:run

To run the functional tests, execute:
mvn test -Dtest=WordgameFunctionalTest

To run unit tests:
mvn test -Dtest=WordgameUnitTest

To run console app:
mvn compile exec:java

To run frontend:
npm install 
npm run dev