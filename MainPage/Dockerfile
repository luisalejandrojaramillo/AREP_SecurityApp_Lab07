FROM openjdk:8


WORKDIR /usrapp/bin


ENV PORT 6000


COPY MainPage/target/classes /usrapp/bin/classes
COPY MainPage/target/dependency /usrapp/bin/dependency

COPY MainPage/keystores /usrapp/bin/MainPage/keystores
COPY Calculator/keystores /usrapp/bin/Calculator/keystores

CMD ["java","-cp","./classes:./dependency/*","co.edu.eci.securityApp.SecureAppServiceApp"]