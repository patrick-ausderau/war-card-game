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

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

/**
 * Represents a playing cards
 */
@Entity
public class Card {
    /**
     * card's identifier for database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    /**
     * ace -> 13, king -> 12,…
     */
    private int value;
    /**
     * club, diamond, heart or spade
     */
    private String color;
    /**
     * card character like 🂡, 🂽, 🃇, 🃚,…
     */
    private String display;

    /**
     *  Construct an empty card
     */
    public Card() {
        this.value = -1;
    }

    /**
     * Construct a card with initial values.
     *
     * @param value integer value: ace -> 13, king -> 12,…
     * @param color one of 'club', 'diamond', 'heart' or 'spade'
     * @param display character representation like '🂡', '🂽', '🃇', '🃚',…
     */
    public Card(int value, String color, String display) {
        this.value = value;
        this.color = color;
        this.display = display;
    }

    /**
     * Returns the value of the card.
     *
     * @return the integer card's value: ace -> 13, king -> 12, queen -> 11,…
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the display's character of the card.
     *
     * @return the display's character of the card
     */
    public String getDisplay() {
        return display;
    }

    /**
     * Returns a string representation of the card. Text with the class name, value, color and display.
     *
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return String.format("%s{value: %2d, color: '%s', display: '%s'}", getClass().getSimpleName(), value, color, display);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && Objects.equals(color, card.color) &&
                Objects.equals(display, card.display);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, color, display);
    }
}
