# AREP_SecurityApp_Lab07

El objetivo de este laboratorio es crear dos aplicaciones web seguras, para lo que vamos a crear
pares de llaves, as´ı estableceremos una conexi´on http segura (https), para ello debemos modificar la
aplicaci´on para que use los certificados creados. Despu´es de resolver ese cero, procedemos a crear
las im´agenes de Docker de nuestras dos aplicaciones web, en la imagen incluiremos los certificados.
Por ´ultimo subiremos la aplicaci´on a AWS

## Enlace Youtube 

[![Video](https://img.youtube.com/vi/Uhmdxozzd44/0.jpg)](https://www.youtube.com/watch?v=Uhmdxozzd44)

## Requisitos
* Git
* Java 8
* Maven
* Docker

## Instalación
1. Abrimos una terminal
2. Clonamos el repositorio
```
git clone https://github.com/luisalejandrojaramillo/AREP_SecurityApp_Lab07
```
3. Entramos al directorio 2
```
cd AREP_SecurityApp_Lab07/MainPage
```
4. Empaquetamos
```
mvn package
```
5. Ejecutamos
```
java -cp ./classes:./dependency/* co.edu.escuelaing.securityApp.SecureAppServiceApp
```
3. Entramos al directorio 2, desde otra terminal 
```
cd ../Calculator
```
4. Empaquetamos
```
mvn package
```
5. Ejecutamos
```
java -cp ./classes:./dependency/* co.edu.escuelaing.calculator.SecureAppServiceApp
```

## Arquitectura

Mediante una conexi´on https vamos a acceder por nuestro browser a nuestra primera aplicaci´on web,
la cual contar´a con pantalla de login, la cual nos dirigir´a si nos autenticamos correctamente a una
pantalla donde debemos ingresar unos n´umeros, a los cuales se les va a realizar un ordenamiento.
Cuando ingresemos los n´umeros, nuestro servicio 1 se va a conectar a nuestro servicio 2 mediante
https, en el servicio dos se va a hacer el ordenamiento va a retornarnos una pagina a la cual va a
acceder el servicio 1.
Lo primero que se hizo fue crear las im´agenes en Docker, donde se copiaron los certificados de
los dos servicios, posteriormente se desplegaron las im´agenes desde una maquina EC2 en AWS en
dos computadores distintos (PC1 y PC2).

![Arquitectuda](/img/dpro.png)

## Prueba

Como podemos ver, esto es lo que nos arroja el pc2 cuando hacemos la petici´on con los datos,
mediante el http reader se va a retornar un string con lo que manda el Pc2, eso es lo que estamos
imprimiendo y es lo que se va a mostrar en pantalla.

![1](/img/1.PNG)

Dentro del Pc2 hay unos prints donde se ve que es lo que se solicita, as´ı verificamos que efectivamente est´a haciendo la petici´on de Pc1 a Pc2.

![2](/img/2.PNG)

Creacion de certificado.

![cert](/img/Captura.PNG)

Certificado en navegador.

![certweb](/img/3.PNG)

## Paso a paso
* Pantalla de login
![p1](/img/a1.PNG)
* Pantalla home donde ingresaremos los datos
![p2](/img/a2.PNG)
*  Luego de la peticion al otro servicios tenemos esta pantalla con los resultados
![p2](/img/a3.PNG)


## Informe

[Informe Latex](lab07AREP.pdf)

## License
[MIT License ](/LICENSE)

## Autor
Luis Alejandro Jaramillo Rincon
