# Pay My Buddy

An Application Programing Interface, designed for rescue services.
The application return datas in Json from URLs
This app uses Java to run and stores the data in H2 database.

This application is designed to help people to transfer money to their friends, and to their bank accounts

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 3.6.2
- Mysql 8.0.17

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

2.Install MySQL community:

https://dev.mysql.com/downloads/mysql/

After downloading the mysql 8 installer and installing it, you will be asked to configure the password 
for the default root account. This code uses the default root account to connect and the password can 
be set as rootroot. If you add another user/credentials make sure to change the same in the code base.

### Create database

You will have to set up the tables and data in the data base. For this, please run the sql commands present in the paymybuddy.sql file.

### Running App

Post installation of Java,  Maven and MySQL, and after creating the database, you will be ready to lauch the application, for this open your cmd.exe, go to the payapp-0.0.1-SNAPSHOT.jar (In the target folder of the application)
In the terminal, use the command "cd", type the filepath to the the alert-0.0.1-SNAPSHOT.jar. And use the commande "java -jar alert-0.0.1-SNAPSHOT.jar". Pay my buddy will be stated on port 9001

### Use Pay my buddy

As we said, the application started on port 9001, so open your favorite web browser, and type "http://localhost:9001/" in the address bar. The anthentification page should show up.
To connect to the application, you have to enter an email address, and a password. 

By default, all the user password are encoded in the database, here are all the accounts you can use with their password not encrypted : 

- Email: pauline.germain@gmail.com - Password: root1
- Email: pierre.pillard@gmail.com - Password: root2
- Email: anthony.diurne@gmail.com - Password: root3


Once connected, you have access to all the fonctionnality of the application, you can:
- Add new friends
- Transfer money to your friends
- Transfer money to your bank accounts
- Add new bank accounts (if they are not already join to a user)
