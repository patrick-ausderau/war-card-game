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

import static org.ausderau.TestConst.*;
import static org.junit.jupiter.api.Assertions.*;

class CardUtilsTest {


    @org.junit.jupiter.api.Test
    void getDeck() {
        // 52 cards deck
        assertEquals(52, CardUtils.getDeck().size());
        // from the order of the Unicode characters, first is 1F0A1: ace of spade, last is 1F0DE: king of club
        assertEquals(aceOfSpade, CardUtils.getDeck().getFirst());
        assertEquals(kingOfClub, CardUtils.getDeck().getLast());
    }

    @org.junit.jupiter.api.Test
    void dealCards() {
       // 3 players
        List<Player> players = List.of(new Player("foo"), new Player("bar"), new Player("baz"));
        CardUtils.dealCards(players, smallDeck);
        assertIterableEquals(List.of(kingOfSpade, sevenOfDiamond, aceOfSpade, kingOfClub), players.getFirst().getHand());
        assertIterableEquals(List.of(tenOfHeart, kingOfHeart, sevenOfClub), players.get(1).getHand());
        assertIterableEquals(List.of(queenOfHeart, tenOfClub, sevenOfSpade), players.getLast().getHand());
        // more players than cards?
        List<Player> playersBig = new ArrayList<>();
        for(int i = 0; i < 13; i++) {
            playersBig.add(new Player("foo" + (i + 1)));
        }
        CardUtils.dealCards(playersBig, smallDeck);
        assertIterableEquals(List.of(kingOfSpade), playersBig.getFirst().getHand());
        assertFalse(playersBig.get(9).getHand().isEmpty());
        assertTrue(playersBig.get(11).getHand().isEmpty());
    }

    @org.junit.jupiter.api.Test
    void printCardsArray() {
        assertEquals("🂡🂢🂣🂤🂥🂦🂧🂨🂩🂪🂫🂭🂮🂱🂲🂳🂴🂵🂶🂷🂸🂹🂺🂻🂽🂾🃁🃂🃃🃄🃅🃆🃇🃈🃉🃊🃋🃍🃎🃑🃒🃓🃔🃕🃖🃗🃘🃙🃚🃛🃝🃞",
                     CardUtils.printCardsArray(CardUtils.getDeck()));
        assertEquals(printSmallDeck, CardUtils.printCardsArray(smallDeck));
    }

    @org.junit.jupiter.api.Test
    void printPlayer() {
        Player player = new Player("1", new LinkedList<>( smallDeck));
        ResourceBundle bundle = ResourceBundle.getBundle("Message", Locale.US);
        String playerEn = "player1: ";
        String playerFr = "joueur1: ";
        assertEquals(playerEn + printSmallDeck, CardUtils.printPlayer(player.getName(), player.getHand(), bundle));
        int size = player.getHand().size();
        for(int i = 0; i < size; i++) {
            player.playCard();
        }
        assertEquals(playerEn + printSmallDeck, CardUtils.printPlayer("1", player.getTrickCards(), bundle));
        List<Card> cards = player.resetTrickCards();
        player.addCardsToHand(cards);
        bundle = ResourceBundle.getBundle("Message", Locale.FRENCH);
        assertEquals(playerFr + printSmallDeck, CardUtils.printPlayer(player.getName(), player.getHand(), bundle));
        assertEquals(playerFr, CardUtils.printPlayer(player.getName(), player.resetTrickCards(), bundle));

    }
}