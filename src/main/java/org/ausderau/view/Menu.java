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

    private static String printStar(String text) {
        int n = 50 - text.length() / 2;
        return "*".repeat(n) + text + "*".repeat(n - (text.length() % 2 == 0 ? 0 : 1));
    }
    private static String printStar() {
        return printStar("");
    }

    public static String printMainMenu(ResourceBundle bundle) {
        return String.format("%s\n%s\n%s\n%s\n%s\n", printStar(bundle.getString("menu.main.title")),
                             bundle.getString("menu.main.show"), bundle.getString("menu.main.shuffle"),
                             bundle.getString("menu.main.quit"), printStar());
    }
}
