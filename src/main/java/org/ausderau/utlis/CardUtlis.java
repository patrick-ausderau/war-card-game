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

package org.ausderau.utlis;

import org.ausderau.model.Card;
import org.ausderau.model.Player;

import java.util.*;

public class CardUtlis {

    public static Queue<Card> dealCards(List<Card> cards, int start, int step) {
        Queue<Card> result = new LinkedList<>();
        for (int i = start; i < cards.size(); i += step) {
            result.add(cards.get(i));
        }
        return result;
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
}
