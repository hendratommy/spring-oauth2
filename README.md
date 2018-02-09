# spring-oauth2-jwt
An example of oauth2 using spring-security and jwt token using xml configuration instead spring-boot

## Getting Started
1. Download or clone this repository
2. Import the project using eclipse/intellij as Maven Project
3. Edit the datasource details in /META-INF/spring/application-context.xml
4. Change the hibernate.hbm2ddl.auto to "create" in /META-INF/persistence.xml
5. Run the project to initialize the tables structure
6. Execute oauth.sql in sql folder to initialize data

### Additional Information
- Default client detail  
client_id: oauth2jwt  
client_secret: aece87dd8c142135d1060cb30861204d

- User detail  
username: admin  
password: admin

- Token endpoint:  
http://localhost:8080/oauth/token
- Access token endpoint:  
http://localhost:8080/oauth/authorize
- You can try to access the user api at http://localhost:8080/oauth/api/users to see if it works, see the controller for more details

If you using postman, you can import my test collection  
https://www.getpostman.com/collections/5545e6942e0857359e88

Cheers!
