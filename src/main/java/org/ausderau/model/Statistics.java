/*
 * War card game (also known as Battle).
 *
 * Copyright © 2026 Patrick Ausderau
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.ausderau.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents statistics about a game
 */
@Entity
public class Statistics {
    /**
     * Unique identifier for DB
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    /**
     * When the game took place
     */
    private LocalDateTime date;
    /**
     * Winner of the game, if any
     */
    private String winner;
    /**
     * How many tricks were played during that game
     */
    private long numberOfTricks;
    /**
     * How many wars were played during that game
     */
    private long numberOfWars;
    /**
     * list of players of that game for the amount and their names
     */
    @OneToMany(cascade = CascadeType.ALL)
    @OrderColumn
    private List<Player> players;
    /**
     * Shuffled deck that was used for that game. By forcing the order of both players and deck, the initial hand of
     * players can be restored.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @OrderColumn
    private List<Card> deck;

    /**
     * Constructs an empty statistics object.
     */
    public Statistics() {
    }

    /**
     * Constructs a statistics object with players and the shuffled deck. Initialize also the date to current time
     * and sets number of tricks and wars to zero.
     *
     * @param players list of players who participated in that game
     */
    public Statistics(List<Player> players, List<Card> deck) {
        this.players = players;
        this.deck = deck;
        date = LocalDateTime.now();
        numberOfTricks = 0;
        numberOfWars = 0;
    }

    /**
     * Returns the database identifier corresponding to this object.
     *
     * @return the database identifier
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the date when the game was played.
     *
     * @return the date when the game was played
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Replaces the date when the game was played.
     *
     * @param date the date when the game was played to be set
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Returns the winner of the game if any.
     *
     * @return the player's name who win the game or an empty string if the game ended in a draw
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Replaces the name of the winner of the game.
     *
     * @param winner the name of the winner to be set
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    /**
     * Returns the number of tricks played during that game.
     *
     * @return the number of tricks that game lasted
     */
    public long getNumberOfTricks() {
        return numberOfTricks;
    }

    /**
     * Replaces the number of tricks played during this game.
     *
     * @param numberOfTricks the number of tricks played to be set
     */
    public void setNumberOfTricks(long numberOfTricks) {
        this.numberOfTricks = numberOfTricks;
    }

    /**
     * Returns the number of wars played during a game
     *
     * @return the amount of wars played
     */
    public long getNumberOfWars() {
        return numberOfWars;
    }

    /**
     * Replaces the number of wars played during a game
     *
     * @param numberOfWars the number of war to be set
     */
    public void setNumberOfWars(long numberOfWars) {
        this.numberOfWars = numberOfWars;
    }

    /**
     * Returns the list of players who played that game.
     *
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Replaces the list of players who played that game.
     *
     * @param players the list of player to be set
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Returns the shuffled deck used during that game.
     *
     * @return the ordered list of cards
     */
    public List<Card> getDeck() {
        return deck;
    }

    /**
     * Replace the shuffled deck used during that game.
     *
     * @param deck the list of cards to be set
     */
    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    /**
     * count the number of tricks played during a game
     */
    public void incrementNumberOfTricks() {
        numberOfTricks++;
    }

    /**
     * count the number of wars played during a game
     */
    public void incrementNumberOfWars () {
        numberOfWars++;
    }
}
