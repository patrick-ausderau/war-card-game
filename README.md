# War card game

Play war card game (also known as battle) with 2 or more players.

## Install and run

### Dependencies

<!-- TODO: add link and version -->
To build and run the application, the following dependencies are required:

* Java JDK 27
* Maven 4
* MySQL server

### Database configuration

1. connect to your database server, e.g. from terminal:
   ```console
   $ mysql -u root -p
   ```
2. create the war database and a user with privileges on it, e.g.:
    ```sql
    > CREATE DATABASE war;
    > CREATE USER 'dbuser' IDENTIFIED BY 'test123';
    > GRANT USAGE ON *.* TO 'dbuser'@localhost IDENTIFIED BY 'test123';
    > GRANT ALL ON war.* TO 'dbuser'@'localhost';
    > FLUSH PRIVILEGES;
    > exit
    ```

### Install

1. clone this repo
2. in a terminal go to the cloned repo `cd ~/<download folder>/war`
3. rename the `hibernate.propreties.sample` file to `hibernate.propreties`
   ```console
   mv src/main/resources/hibernate.properties.sample src/main/resources/hibernate.properties
   ```
   and modify the database username and password
4. compile with maven `mvn clean package`

### How to play

1. run with maven `mvn exec:java -Dexec.mainClass="org.ausderau.War"`

## Application

### Game winning condition

Normally the winner is the player who collects all the cards. However, if there is a war and a player has no more
cards in its hand, he can't participate in it. In that case, it was decided to end the game with a draw, i.e. no
winner.

Other options would have been to allow the player with no cards in hand to use the card at war as her/his second
card. Or, allow him to randomly pick one card from his opponent.

If the player with no cards in hand would be declared as a looser, then we could give her/his cards to the winner of
the war; but we can face the risk of a multiple war where e.g. in a 5 players game having player1, player3 and player4
with only one king card in hand and players 2 and 5 playing a smaller card in this trick…

From the implementation point of view, the hand is stored as a `Queue` for FIFO operations and there is a double
safety. First, when playing the top card, it uses the `poll()` that returns `null` instead of `remove()` to avoid
throwing an exception. And before playing it there is a second check if the queue `!isEmpty()`.

### Contribute

This project was made as a java + maven + hibernate demo. It might not be developed further. If you want to modify
it, feel free to fork it and do whatever you like as long as you follow the GPL license.
