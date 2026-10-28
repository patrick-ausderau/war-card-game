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

package org.ausderau.view;

import java.util.ResourceBundle;

public class Menu {

    /**
     * Returns a string with stars characters '*' with the text in the middle.
     *
     * @param text text to add in the middle of the stars
     * @return a string with text in middle of stars if text's length is shorter than 50
     */
    private static String printStar(String text) {
        int n = 50 - text.length() / 2;
        return "*".repeat(n) + text + "*".repeat(n - (text.length() % 2 == 0 ? 0 : 1));
    }

    /**
     * Returns a string of 50 stars '*' characters
     *
     * @return a string with 50 stars
     */
    private static String printStar() {
        return printStar("");
    }

    /**
     * Returns a string with item text for the main menu
     *
     * @param bundle for internationalization
     * @return a string of text representing the main menu
     */
    public static String printMainMenu(ResourceBundle bundle) {
        return String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n", printStar(bundle.getString("menu.main.title")),
                             bundle.getString("menu.main.show"), bundle.getString("menu.main.shuffle"),
                             bundle.getString("menu.main.start"), bundle.getString("menu.main.startSame"),
                             bundle.getString("menu.main.quit"), printStar());
    }

    /**
     * Returns a string with item text for the game menu
     *
     * @param bundle for internationalization
     * @return a string of text representing the game menu
     */
    public static String printGameMenu(ResourceBundle bundle) {
        return String.format("%s\n%s\n%s\n%s\n%s\n", printStar(bundle.getString("menu.game.title")),
                             bundle.getString("menu.game.playNext"), bundle.getString("menu.game.show"),
                             bundle.getString("menu.game.automate"), printStar());
    }
}
