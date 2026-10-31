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

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides text to be print to show menu to end user with internationalization
 */
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

    private static String printMenu(String title, String... menuItems) {
        return Stream.concat(Stream.concat(Stream.of(printStar(title)), Arrays.stream(menuItems)),
                                 Stream.of(printStar())).collect(Collectors.joining( "\n"));
    }

    /**
     * Returns a string with item text for the main menu
     *
     * @param bundle for internationalization
     * @return a string of text representing the main menu
     */
    public static String printMainMenu(ResourceBundle bundle) {
        return printMenu(bundle.getString("menu.main.title"),  bundle.getString("menu.main.show"),
                         bundle.getString("menu.main.shuffle"), bundle.getString("menu.main.start"),
                         bundle.getString("menu.main.startSame"), bundle.getString("menu.main.saved"),
                         bundle.getString("menu.main.quit"));
    }

    /**
     * Returns a string with item text for the game menu
     *
     * @param bundle for internationalization
     * @return a string of text representing the game menu
     */
    public static String printGameMenu(ResourceBundle bundle) {
        return printMenu(bundle.getString("menu.game.title"), bundle.getString("menu.game.playNext"),
                         bundle.getString("menu.game.show"), bundle.getString("menu.game.automate"));
    }

    public static String printDatabaseMenu(ResourceBundle bundle) {
        return printMenu(bundle.getString("menu.db.title"), bundle.getString("menu.db.show10"),
                         bundle.getString("menu.db.showId"), bundle.getString("menu.db.back"));
    }
}
