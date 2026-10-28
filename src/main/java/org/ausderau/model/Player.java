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

import java.util.*;

public class Player {
    /**
     * player's unique name
     */
    private String name;
    /**
     * player's cards
     */
    private Queue<Card> hand = new LinkedList<>();
    private List<Card> trickCards = new LinkedList<>();

    /**
     * Constructs an empty player.
     */
    public Player() {
        this.name = "";
    }

    public Player(String name) {
        super();
        this.name = name;
    }

    /**
     * Constructs a player with default values.
     *
     * @param name player's name
     * @param hand player's cards
     */
    public Player(String name, Queue<Card> hand) {
        super();
        this.name = name;
        this.hand = hand;
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Replace the player's name.
     *
     * @param name the name of the player to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the player's cards.
     *
     * @return the ordered list of cards of the player
     */
    public Queue<Card> getHand() {
        return hand;
    }

    /**
     * Replace player's cards. Should only be used at beginning of the game.
     * During the game, add or remove cards from appropriate methods.
     *
     * @param hand ordered list of cards to set
     */
    public void setHand(Queue<Card> hand) {
        this.hand = hand;
    }

    /**
     * Returns the cards the player has played for a trick.
     *
     * @return the cards for a trick by this player
     */
    public List<Card> getTrickCards() {
        return trickCards;
    }

    /**
     * Replace the cards played for a trick by the player.
     *
     * @param trickCards the list of cards play during a trick
     */
    public void setTrickCards(List<Card> trickCards) {
        this.trickCards = trickCards;
    }

    /**
     * Add a card at the end of the player's hand.
     *
     * @param card the card to add to player's hand
     */
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    /** Add many cards at the end of the player's hand.
     *
     * @param cards queue of cards to add to player's hand
     */
    public void addCardsToHand(Collection<Card> cards) {
        hand.addAll(cards);
    }

    /**
     * Returns the first card of the player's hand, or null if the player has no more cards.
     *
     * @return the first card of the player's hand, or null if hand is empty
     */
    public Card playCard() {
        Card card = hand.poll();
        if(null != card) {
            trickCards.add(card);
        }
        return card;
    }

    public List<Card> resetTrickCards() {
        List<Card> cards = new LinkedList<>(trickCards);
        trickCards.clear();
        return cards;
    }

    /**
     * Returns a string representation of the player. Text with the class name, name and hand.
     *
     * @return a string representation of the player
     */
    @Override
    public String toString() {
        return "Player{" +
                "name:'" + name + '\'' +
                ", hand:" + hand + '\'' +
                ", trickCards:" + trickCards +
                '}';
    }
}
