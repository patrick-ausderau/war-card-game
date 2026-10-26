
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

import org.ausderau.model.Card;
import org.ausderau.model.Deck;
import org.ausderau.view.Menu;

import java.util.*;

public class Main {
    static void main() {
        int menuItem;
        List<Card> deck = new ArrayList<>(Deck.getCards());
        ResourceBundle bundle = ResourceBundle.getBundle("Message", Locale.getDefault());
        Scanner scanner = new Scanner(System.in);
        do {
            IO.println(Menu.printMainMenu(bundle));
            try {
                menuItem = scanner.nextInt();
                switch (menuItem) {
                    case 1:
                        deck.forEach(card -> IO.print(card.getDisplay()));
                        IO.println();
                        break;
                    case 2:
                        Collections.shuffle(deck);
                        break;
                    default: return;
                }
            } catch (NoSuchElementException | IllegalStateException exception) {
                return;
            }
        } while (true);
    }
}
