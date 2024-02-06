# AKKA HTTP with PostgresSQL     

**Clone the repository and follow below steps**     

1. **Open a terminal** and **connect to postgres DB** by the below commands.     
     ```sh 
   sudo -i -u postgres;       
   ```   
     ![Screenshot from 2024-02-05 12-58-42.png](..%2F..%2F..%2FPictures%2FScreenshot%20from%202024-02-05%2012-58-42.png)
 

2. **Create a database Postgres(already created then don't create).**        
    ```sh 
   CREATE DATABASE postgres;       
   ```      


3. **Connect to postgres** and **Create a table UserInfo.**
    ```sh 
   \c postgres 
   ```
    ```sh 
    CREATE TABLE UserInfo (
      userId INT PRIMARY KEY,
      firstName VARCHAR(20),
      lastName VARCHAR(20),
      email VARCHAR(50) NOT NULL,
      phoneNumber BIGINT,
      address VARCHAR(60)
    );
    ```         
  

4. In **resources/postgres.conf** file **_change the username and password_** (Enter your username and password)
   ```sh
       postgres {
       url = "jdbc:postgresql://localhost:5432/postgres"
       host = "localhost"
       port = 5432
       name = "postgres"
       username = "postgres"  
       password = "manish12345"
    }
   ```
   

5. **Now Run** the **UserAPI scala class** it will generate the userAPI to create, read, update, delete the users.    
   Below are the **list of user API.**
    - **create user:** http://localhost:8081/api/create-user
    - **get all users:** http://localhost:8081/api/get-all-users
    - **get user by id:** http://localhost:8081/api/get-user-by-id/101 **_(Replace 101 by the userID)_**
    - **delete user:** http://localhost:8081/api/delete-user-by-id/101 **_(Replace 101 by the userID)_**
    - **update user firstname:** http://localhost:8081/api/update-user-name/101/Manish **_(Replace 101 by the userID & Manish with FirstName)_**


6. **Open Postman** and select your **choice(post, get, delete, patch)** and **enter the above url** as per your choice and **hit send button.**  
    **Sample Data to Create User:**
    ```sh
   { 
      "userId":101,
      "firstName": "Manish",
      "lastName": "Mishra",
      "email": "manish.mishra1@nashtechglobal.com",
      "phoneNumber": 9898787876,
      "address": "Near Ram-eesh International, Greater Noida",
   }
   ```
   **Example:**      
   ![Screenshot from 2024-02-05 21-02-21.png](..%2F..%2F..%2FPictures%2FScreenshot%20from%202024-02-05%2021-02-21.png)