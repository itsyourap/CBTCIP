CipherByte Technologies Internship Tasks
========================================

Table of contents
=================

<!--ts-->

* [Task 1: Guess The Number Game](#task-1-guess-the-number-game)
    * [Description](#description)
    * [Rules](#rules)
    * [How to run](#how-to-run)


* [Task 2: ATM Application](#task-2--atm-application)
    * [Description](#description-1)
    * [How to run](#how-to-run-1)

<!--te-->

[Task 1: Guess The Number Game](https://github.com/itsyourap/CBTCIP/tree/main/Task1_GuessTheNumber)
================================

Description
-----------

This is a simple game where the user has to guess a number between 1 and 100. The user has 10 chances to guess the
number. If the user guesses the number correctly, he/she wins the game. If the user is unable to guess the number in 10
chances, he/she loses the game.

Rules
-----

* This program generates a random number between 1 and 100 and asks the user to guess the number.
* The user has 10 guesses to guess the number correctly.
* If the user guesses the number correctly, the program prints "You guessed the number!" and exits.
* If the user does not guess the number correctly, the program prints "Your guess is too high!" or "Your guess is too
  low!" depending on whether the guess is higher or lower than the random number.
* If the user does not guess the number correctly in 10 guesses, the program prints "You ran out of guesses!" and exits.
* The program also prints the random number after the user has run out of guesses.
* The program then asks the user if they want to play again. If the user enters "yes", the program generates a new
  random number and asks the user to guess the number again.
* If the user enters "no", the program exits.
* This program also has rounds feature
* The program also displays the points scored in total throughout the rounds

How to run
----------

* Clone the repository

```shell
git clone https://github.com/itsyourap/CBTCIP/
```

* Navigate to the Task1_GuessTheNumber directory

```shell
cd CBTCIP/Task1_GuessTheNumber
```

* Compile the program

```shell
javac -classpath "src/;" src/GuessTheNumber.java
```

* Run the program

```shell
java -cp "src/" GuessTheNumber
```

[Task 2: ATM Application](https://github.com/itsyourap/CBTCIP/tree/main/Task2_ATM)
=======================

Description
-----------

This is a simple ATM application that allows the user to perform the following operations:

* Getting user input for User ID and PIN
* Validating the User ID and PIN
* Displaying the main menu
* Getting user input for the main menu
* Performing the selected action
* Displaying the result of the action
* Returning to the main menu
* Exiting the application

The main menu has the following options:

* Withdraw
* Deposit
* Transfer
* Show Transaction History
* Exit

How to run
----------

* Clone the repository

```shell
git clone https://github.com/itsyourap/CBTCIP/
```

* Navigate to the Task2_ATM directory

```shell
cd CBTCIP/Task2_ATM/
```

* Compile the program

```shell
javac -classpath "src/;../lib/sqlite-jdbc-3.44.0.0.jar;../lib/slf4j-api-1.7.36.jar" src/ATM.java
```

* Run the program

```shell
java -cp "src/;../lib/sqlite-jdbc-3.44.0.0.jar;../lib/slf4j-api-1.7.36.jar" ATM
```