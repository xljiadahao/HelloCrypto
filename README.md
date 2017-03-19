# HelloCrypto
Let's start to explore Cryptography. It's a very good project for the coders who would like to start to bring security concept with cryptography in their projects.

It can be used by any organisation for the lucky draw of the events with practice of the java keytool usage. Tech Stack: Java, Spring, Struts2, Hibernate (and JPA), MySQL, HTML, jQuery, Ajax, Crypto. Web part uses Struts2 as the Controller to handle the requests, and Rest part uses Spring RestController to handle the requests. Target to use the encryption and decryption (here is asymmetric encryption such as RSA) for the lucky draw. End users publish their public key, and the admin uses some of the public keys to encrypt the content, then publishes the encrypted message. As the end users have the  key pair, private key and public key, so the end users who can use their private key to decrypt the message and get the correct content win the lucky draw.

## Steps to launch the project
1. Run DB script dbscript/db_script.sql in the MySQL database;
2. Start Application Server, create and configure data source to connect MySQL DB hellocrypto schma which is done by step 1; in this project, we use JBoss Application Service, and the data source name is java:jboss/datasources/hellocryptoDS; if not use JBoss server or not use current data source name, we can create the data source to connect the MySQL DB hellocrypto schma, and configure the data source name in src/main/resources/META-INF/persistence.xml accordingly;
3. Build HelloCrypto project and export hellocrypto.war file under the target folder in the workspace of the project; then deploy hellocrypto.war to the Application Server (JBoss App Server) and launch the hellocrypto application;
4. (how to use the lucky draw system) For end user who take part in the lucky draw activity, hit the URL by GET call http://localhost:8080/hellocrypto/rsa/certificate.action to access Upload Certificate page;
5. (how to use the lucky draw system) For end user who take part in the lucky draw activity, do the following steps to generate RSA key pair within keystore file and export the certificate; a. find out JAVA_HOME path, ehco $JAVA_HOME, if you cannot find your JDK path, b. use Java keytool to generate the RSA key pair, $JAVA_HOME/bin/keytool -genkey -alias (your own alias) -keyalg RSA -keysize 1024 -keystore (your keystore file name).keystore -storepass (your owm keystore file password) -keypass (your own key entry password); c. export the certificate, $JAVA_HOME/bin/keytool -export -alias (your own alias) -keystore (your keystore file name).keystore -file (your certificate file name).crt -storepass (your owm keystore file password); let the user remenber their alias, storepass, keypass, and keep the generated keystore file and certificate file;
6. (how to use the lucky draw system) For end user who take part in the lucky draw activity, fill with the Full Name of the user, and attach the certificate file which is generated in step 5;
7. (how to use the lucky draw system) For lucky draw system Admin, use Postman (a Google Chrome App for Restful Service) to trigger the lucky draw with hitting the URL http://localhost:8080/hellocrypto/securedraw.rest by PUT call, with the request body {"luckDrawNum":"2","luckDrawText":["lucky draw encrypted msg 1","lucky draw encrypted msg 2"],"auth":"xulei"}; luckDrawNum is the number of the lucky draw winners, luckDrawText is the lucky draw encrypted message which will be shown afterwards and the number of the messages should be the same as the number of the lucky draw winners, auth is hardcoded in the code Constant.java, you can change it if you want before you build the project; NOTE: for the corresponding number of the lucky draw winners, the lucky draw system will encrypt the clear luckDrawText set by the admin with the randomly selected public key which is in the uploaded certificate by the end user respectively; then the Admin should get the response with isSuccess: true and rest of the luck draw winners' information;
8. (how to use the lucky draw system) For end user who take part in the lucky draw activity, click the Decryption link which is enabled after refresh the page which is mentioned in step 4, the user will see the encrypted messages; then the user can copy and paste one of the encrypted message, upload the keystore file with the keystore and key entry credential to try to decrypt the message shown in your page; the ones who can decrypt the message and get the human readable content are the lucky draw winners.
