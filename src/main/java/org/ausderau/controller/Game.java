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

package org.ausderau.controller;

import org.ausderau.model.Card;
import org.ausderau.model.Deck;
import org.ausderau.model.Player;
import org.ausderau.utlis.BadNumberOfPlayerException;
import org.ausderau.utlis.CardUtlis;
import org.ausderau.view.Menu;

import java.util.*;

public class Game {

    /**
     * List of players for the game
     */
    private static List<Player> players;
    /**
     * List of winner of the game.
     * If size equals one, there is a winner. If size is bigger the game ends in draw (no winner).
     */
    private static List<Player> winner;

    // store the players who play the biggest card, if only one, s/he is the winner, multiple means war
    private static List<Player> trickWinnerOrWar = new ArrayList<>();
    // last played card during a trick or biggest card of a war
    private static Card card, biggest;

    /**
     * Initialize the number of players for a new game.
     *
     * @param bundle for internationalization
     * @param scanner input reader
     * @throws BadNumberOfPlayerException if user input something else than a number or if the amount of players in
     * out of [2..10] range
     */
    public static void initNumberOfPlayers(ResourceBundle bundle, Scanner scanner) throws BadNumberOfPlayerException {
        IO.println(bundle.getString("game.player.numbers"));
        int numOfPlayer;
        try {
            numOfPlayer = scanner.nextInt();
            scanner.nextLine(); //flush new-line (e.g. if user input text after number)
        } catch (NoSuchElementException | IllegalStateException exception) {
            scanner.nextLine(); //flush new-line (e.g. if user input text)
            throw new BadNumberOfPlayerException();
        }
        if(numOfPlayer < 2 || numOfPlayer > 10) throw new BadNumberOfPlayerException();
        initPlayersName(numOfPlayer);
    }

    /**
     * Initialize a list of players with their name set incrementally (e.g. '1', '2',…).
     *
     * @param numberOfPlayer amount of players to add to the list
     */
    public static void initPlayersName(int numberOfPlayer) {
        players = new ArrayList<>();
        for(int i = 0; i < numberOfPlayer; i++) {
            players.add(new Player("" + (i + 1)));
        }
    }

    /**
     * Prepares the game by dealing the cards one by one to every player
     *
     * @param deck list of cards to deal
     * @throws BadNumberOfPlayerException if list of user is null or if the amount of players in out of [2..10] range
     */
    public static void initGameDeal(List<Card> deck) throws BadNumberOfPlayerException {
        if(null == players || players.size() < 2 || players.size() > 10) throw new BadNumberOfPlayerException();
        for(int i = 0; i < players.size(); i++) {
            players.get(i).setHand(CardUtlis.dealCards(deck, i, players.size()));
        }
    }

    public static void playGame(ResourceBundle bundle, Scanner scanner) {
        // read user menu choice input
        int menuItem;
        winner = new ArrayList<>();
        do {
            IO.println(Menu.printGameMenu(bundle));
            try {
                menuItem = scanner.nextInt();
            } catch (NoSuchElementException | IllegalStateException e) {
                menuItem = -1;
            }
            scanner.nextLine(); // flush new-line
            switch (menuItem) {
                case 1:
                    playTrick(bundle);
                    if(isGameEnding(bundle)) return;
                    break;
                case 2:
                    printPlayersWithHand(bundle);
                    break;
                case 3:
                    while (!isGameEnding(bundle)) {
                        playTrick(bundle);
                    }
                    return;
                default:
                    break;
            }
        } while (true);
    }

    /**
     * Play a trick with possible war and draw situation
     *
     * @param bundle for internationalization
     */
    //TODO: optimize
    private static void playTrick(ResourceBundle bundle) {
        trickWinnerOrWar.clear();
        // if the player still has cards, play the first card from its hand
        for(Player player : players) {
            if(!player.getHand().isEmpty()) {
                card = player.playCard();
                if(trickWinnerOrWar.isEmpty() || trickWinnerOrWar.getFirst().getTrickCards().getLast().getValue() <= card.getValue()) {
                    if(!trickWinnerOrWar.isEmpty() && trickWinnerOrWar.getFirst().getTrickCards().getLast().getValue() < card.getValue()) {
                        trickWinnerOrWar.clear();
                    }
                    trickWinnerOrWar.add(player);
                }
            }
        }
        // until there is more than one player with the biggest card, there is war
        while (trickWinnerOrWar.size() > 1) {
            biggest = new Card();
            // check if the players still have cards to participate in war, otherwise, the game ends with no winner
            for (Player player : trickWinnerOrWar) {
                if(player.getHand().isEmpty()) {
                    winner = trickWinnerOrWar;
                    return;
                }
                card = player.playCard();
                if(card.getValue() > biggest.getValue()) biggest = card;
            }
            Card finalBiggest = biggest;
            trickWinnerOrWar.removeIf(player -> player.getTrickCards().getLast().getValue() < finalBiggest.getValue());
        }
        printTrick(bundle);
        // give all trick cards to winner
        for (Player player : players) {
            if(!player.getTrickCards().isEmpty()) {
                trickWinnerOrWar.getFirst().addCardsToHand(player.resetTrickCards());
            }
        }
        IO.println(bundle.getString("game.trick.winner") + trickWinnerOrWar.getFirst().getName());
        // check if the player is the winner of the game
        if(trickWinnerOrWar.getFirst().getHand().size() == Deck.getCards().size()) {
            winner.add(trickWinnerOrWar.getFirst());
        }
    }

    /**
     * Check if the game has ended. Either one player collected all the cards or a war ended in a draw.
     * @param bundle for internationalization
     * @return true if the game is over
     */
    private static boolean isGameEnding(ResourceBundle bundle) {
        if(winner.size() > 1) {
            IO.println(bundle.getString("game.draw"));
            return true;
        } else if (winner.size() == 1) {
            IO.println(bundle.getString("game.winner") + winner.getFirst().getName());
            return true;
        }
        return false;
    }

    /**
     * Prints to console the list of players with their cards at hand.
     *
     * @param bundle for internationalization
     */
    public static void printPlayersWithHand(ResourceBundle bundle) {
        players.forEach(player -> IO.println(CardUtlis.printPlayer(player.getName(), player.getHand(), bundle)));
    }

    /**
     * Prints to console the list of players with their played cards.
     *
     * @param bundle for internationalization
     */
    public static void printTrick(ResourceBundle bundle) {
        players.forEach(player -> IO.println(CardUtlis.printPlayer(player.getName(), player.getTrickCards(), bundle)));
    }
}
