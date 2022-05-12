# SDET-test

### Project Notes
Time spent: 4-5 hours


The following test case is failing:

```
Validate that the amounts of pictures that each "Curiosity" camera took on 1000 Mars sol is not greater than 10 times 
the amount taken by other cameras on the same date.
```

Does not meet the conditions mentioned and therefore fails.

Curiosity total photos: 856

Spirit and Opportunity photos: 130


This information can also be seen in the report

### Project execution

- Command line: 
  - Open a terminal 
  - Go to the project path
  - Execute the following command: `mvn clean test`
- Factory File: `src\test\java\suite\NasaFactory.java`
- Test File: `src\test\java\test\CuriosityTest.java`
- 
### Tools Used

- Java - Programming language used for the base of the project.
- Rest Assured - Used to test RESTful Web Services.
- Jackson - Used for Serialization/Deserialization.
- TestNG - Used as framework
- Lombok - To auto generate getters and setters
- Log4j2 - For logging