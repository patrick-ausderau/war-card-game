
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

package org.ausderau;

import org.ausderau.controller.Game;
import org.ausderau.model.Card;
import org.ausderau.utils.BadNumberOfPlayerException;
import org.ausderau.utils.CardUtils;
import org.ausderau.view.Menu;

import java.util.*;

public class War {

    static void main() {
        // Read user menu choice input
        int menuItem;
        // Deck of cards to play with
        List<Card> deck = new ArrayList<>(CardUtils.getDeck());
        // Internationalization, currently support French/English
        ResourceBundle bundle = ResourceBundle.getBundle("Message", Locale.getDefault());
        Scanner scanner = new Scanner(System.in);
        do {
            IO.println(Menu.printMainMenu(bundle));
            try {
                menuItem = scanner.nextInt();
                scanner.nextLine(); //flush new-line (e.g. if user input text after number)
                switch (menuItem) {
                    case 1:
                        IO.println(CardUtils.printCardsArray(deck));
                        break;
                    case 2:
                        Collections.shuffle(deck);
                        break;
                    case 3:
                    case 4:
                        if(menuItem == 3) {
                            try {
                                Game.initNumberOfPlayers(bundle, scanner);
                            } catch (BadNumberOfPlayerException e) {
                                System.err.println(bundle.getString("game.error.number"));
                                break;
                            }
                        }
                        //TODO: case 5, Game init from DB
                        try {
                            Game.initGameDeal(deck);
                            //TODO: save to DB
                        } catch (BadNumberOfPlayerException e) {
                            System.err.println(bundle.getString("game.error.number"));
                            break;
                        }
                        Game.playGame(bundle, scanner);
                        break;

                    default:
                        IO.println(bundle.getString("game.bye"));
                        return;
                }
            } catch (NoSuchElementException | IllegalStateException exception) {
                // if not an int, end the game too
                IO.println(bundle.getString("game.bye"));
                return;
            }
        } while (true);
    }
}
