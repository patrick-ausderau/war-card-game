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

import java.util.ArrayList;
import java.util.List;

public class Deck {
    //52 card deck with value, ace -> 13, king -> 12,…
    private static final List<Card> cards = new ArrayList<>();

    static {
        int i = 0;
        for (int codePoint = 0x1F0A1; codePoint <= 0x1F0DE; codePoint++) {
            // jump over gaps between colors, jokers and knights
            if (codePoint == 0x1F0AF || codePoint == 0x1F0B0
                    || codePoint == 0x1F0C0 || codePoint == 0x1F0D0
                    || codePoint == 0x1F0AC || codePoint == 0x1F0BC
                    || codePoint == 0x1F0CC || codePoint == 0x1F0DC
                    || codePoint == 0x1F0BF || codePoint == 0x1F0CF) continue;

            cards.add(new Card(i == 0 ? 13 : i, codePoint <= 0x1F0AE ? "spade" : codePoint <= 0x1F0BE ? "heart" : codePoint <= 0x1F0CE ? "diamond" : "club", new String(Character.toChars(codePoint))));
            if (++i % 13 == 0) i = 0;
        }
    }

    public static List<Card> getCards() {
        return List.copyOf(cards);
    }
}
