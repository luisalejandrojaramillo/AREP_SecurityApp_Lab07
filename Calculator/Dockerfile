FROM openjdk:8


WORKDIR /usrapp/bin


ENV PORT 6000


COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency

COPY /keystores /usrapp/bin/keystores

CMD ["java","-cp","./classes:./dependency/*","co.edu.eci.calculator.SecureApp"]