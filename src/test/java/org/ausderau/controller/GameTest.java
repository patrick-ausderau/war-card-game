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

import org.ausderau.utils.BadNumberOfPlayerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.ausderau.TestConst.winnerDeck;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    // restore normal input after test
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @AfterEach
    void retoreNormalSystem() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    void initNumberOfPlayers() {
        ResourceBundle bundle = ResourceBundle.getBundle("Message", Locale.getDefault());
        //one string, and two values out of range, all should throw exception
        System.setIn(new ByteArrayInputStream("foo\n1\n42\n".getBytes()));
        Scanner scanner = new Scanner(System.in);
        try {
            assertThrows(BadNumberOfPlayerException.class, () -> Game.initNumberOfPlayers(bundle, scanner));
            assertThrows(BadNumberOfPlayerException.class, () -> Game.initNumberOfPlayers(bundle, scanner));
            assertThrows(BadNumberOfPlayerException.class, () -> Game.initNumberOfPlayers(bundle, scanner));
        } catch (Exception _) {
            // Pokemon, catch em all
        }
    }

    @Test
    void playGame() {
        ResourceBundle bundle = ResourceBundle.getBundle("Message", Locale.US);
        //one string, and two values out of range, all should throw exception
        System.setIn(new ByteArrayInputStream("2\n3\n".getBytes()));
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(System.in);
        try {
            Game.initNumberOfPlayers(bundle, scanner);
            Game.initGameDeal(winnerDeck);
            Game.playGame(bundle, scanner);
            //TODO: clean DB after test
            //Database.deleteStatistics(id);
            String out = outContent.toString();
            assertEquals("🏆 The winner is player2\n", out.substring(out.length() - 25));
        } catch (BadNumberOfPlayerException e) {
            throw new RuntimeException(e);
        }
    }
}