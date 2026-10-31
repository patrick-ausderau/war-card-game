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

import java.util.List;

/**
 * Constant value for tests
 */
public class TestConst {

    public static final Card aceOfSpade = new Card(13, "spade", "🂡");
    public static final Card kingOfClub = new Card(12, "club", "🃞");
    public static final Card kingOfSpade = new Card(12, "spade", "🂮");
    public static final Card kingOfHeart = new Card(12, "heart", "🂾");
    public static final Card queenOfHeart = new Card(11, "heart", "🂽");
    public static final Card sevenOfDiamond = new Card(6, "diamond", "🃇");
    public static final Card sevenOfSpade = new Card(6, "spade", "🂧");
    public static final Card sevenOfClub = new Card(6, "club", "🃗");
    public static final Card tenOfClub = new Card(9, "club", "🃚");
    public static final Card tenOfHeart = new Card(9, "heart", "🂺");
    // test for a 2 player game with one war and player2 win
    public static final List<Card> winnerDeck = List.of(kingOfHeart, aceOfSpade, sevenOfSpade, sevenOfClub, tenOfClub,
                                                        queenOfHeart, sevenOfDiamond, kingOfSpade);
    // to test the print output
    public static final List<Card> smallDeck = List.of(kingOfSpade, tenOfHeart, queenOfHeart, sevenOfDiamond, kingOfHeart,
                                                       tenOfClub, aceOfSpade, sevenOfClub, sevenOfSpade, kingOfClub);

    public static final String printSmallDeck = "🂮🂺🂽🃇🂾🃚🂡🃗🂧🃞";
}
