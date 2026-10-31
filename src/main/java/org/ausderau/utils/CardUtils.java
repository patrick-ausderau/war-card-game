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

package org.ausderau.utils;

import org.ausderau.model.Card;
import org.ausderau.model.Player;

import java.util.*;

/**
 * Provides methods to manipulate cards and deck
 */
public class CardUtils {

    /**
     * deck of cards with value, ace -> 13, king -> 12,…
     */
    private static final List<Card> deck = new ArrayList<>();

    /*
     * Initialize a 52 cards deck with the four French card suits (diamond, heart, club, spade) from ace, 2, 3,…, king
     */
    static {
        int i = 0;
        for (int codePoint = 0x1F0A1; codePoint <= 0x1F0DE; codePoint++) {
            // jump over gaps between colors, jokers and knights
            if (codePoint == 0x1F0AF || codePoint == 0x1F0B0 || codePoint == 0x1F0C0 || codePoint == 0x1F0D0 ||
                    codePoint == 0x1F0AC || codePoint == 0x1F0BC || codePoint == 0x1F0CC || codePoint == 0x1F0DC ||
                    codePoint == 0x1F0BF || codePoint == 0x1F0CF) continue;

            deck.add(new Card(i == 0 ? 13 : i, codePoint <= 0x1F0AE ? "spade" :
                    codePoint <= 0x1F0BE ? "heart" : codePoint <= 0x1F0CE ? "diamond" : "club",
                              new String(Character.toChars(codePoint))));
            if (++i % 13 == 0) i = 0;
        }
    }

    /**
     * Get the list of the playable cards from the deck
     *
     * @return a list of cards
     * @see Card
     */
    public static List<Card> getDeck() {
        return List.copyOf(deck);
    }

    /**
     * Returns a hand of cards for a player. Cards are dealt one by one to each player. Some players might get one more
     * card than other players if the size of the deck is not a multiple of the number of players.
     *
     * @param deck the deck of cards to deal to the players
     * @param start the position of the player, player 1 at position 0
     * @param numberOfPlayer the number of player as a step to deal all the n<sup>th</sup> card to the current player
     * @return an ordered list of cards based on the order of the deck
     */
    private static Queue<Card> dealCards(List<Card> deck, int start, int numberOfPlayer) {
        Queue<Card> hand = new LinkedList<>();
        for (int i = start; i < deck.size(); i += numberOfPlayer) {
            hand.add(deck.get(i));
        }
        return hand;
    }

    public static void dealCards(List<Player> players, List<Card> deck) {
        for(int i = 0; i < players.size(); i++) {
            players.get(i).setHand(dealCards(deck, i, players.size()));
        }
    }

    /**
     * Return a string for a print friendly representation of a collection of cards.
     *
     * @param cards the collection to display
     * @return a string with the cards display, something like '🂡🂽🃇🃚'
     */
    public static String printCardsArray(Collection<Card> cards) {
        return cards.stream().map(Card::getDisplay).reduce("", (a, b) -> a+b);
    }

    /**
     * Returns a string for a print friendly representation of the player with its name and its hand or played cards.
     *
     * @param name name of the player to print
     * @param cards collection of cards to print
     * @param bundle for translation
     * @return a string with player's name and display hand
     */
    public static String printPlayer(String name, Collection<Card> cards, ResourceBundle bundle) {
        return String.format("%s%s: %s", bundle.getString("player"), name, printCardsArray(cards));
    }

    /**
     * Prints to console the list of players with their cards at hand.
     *
     * @param bundle for internationalization
     */
    public static void printPlayersWithHand(Collection<Player> players, ResourceBundle bundle) {
        players.forEach(player -> IO.println(printPlayer(player.getName(), player.getHand(), bundle)));
    }

    /**
     * Prints to console the list of players with their played cards.
     *
     * @param bundle for internationalization
     */
    public static void printTrick(Collection<Player> players, ResourceBundle bundle) {
        players.forEach(player -> IO.println(printPlayer(player.getName(), player.getTrickCards(), bundle)));
    }
}
