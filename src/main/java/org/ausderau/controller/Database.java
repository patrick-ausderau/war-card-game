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
import org.ausderau.model.Player;
import org.ausderau.model.Statistics;
import org.ausderau.utils.CardUtils;
import org.ausderau.utils.DatabaseUtils;
import org.ausderau.view.Menu;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Page;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Controls the database operations. Connection is done in the main() method.
 */
public class Database {

    /**
     * Creates statistics form a game to be stored in database.
     *
     * @param statistics data to be stored in database
     */
    public static void creatStatistics(Statistics statistics) {
        try (DatabaseUtils db = new DatabaseUtils(Statistics.class, Player.class, Card.class)) {
            try (Session session = db.openSession()) {
                Transaction tx = session.beginTransaction();
                session.persist(statistics);
                tx.commit();
            }
        }
    }

    /**
     * Deletes stored game's statistics form database.
     *
     * @param id identifier of the statistics to delete
     */
    public static void deleteStatistics(long id) {
        try (DatabaseUtils db = new DatabaseUtils(Statistics.class, Player.class, Card.class)) {
            try (Session session = db.openSession()) {
                Statistics statistics = session.find(Statistics.class, id);
                Transaction tx = session.beginTransaction();
                session.remove(statistics);
                tx.commit();
            }
        }
    }

    /**
     * Select and show data based on user choice.
     *
     * @param bundle for internationalization
     * @param scanner read user input
     */
    public static void showStatistics(ResourceBundle bundle, Scanner scanner) {
        int menuItem;
        try (DatabaseUtils db = new DatabaseUtils(Statistics.class, Player.class, Card.class)) {
            while(true) {
                IO.println(Menu.printDatabaseMenu(bundle));
                try {
                    menuItem = scanner.nextInt();
                } catch (NoSuchElementException | IllegalStateException e) {
                    menuItem = -1;
                }
                scanner.nextLine(); // flush new-line
                switch (menuItem) {
                    case 1:
                        paginatedStatistics(bundle, scanner, db);
                        break;
                    case 2:
                        printDetailStatistics(bundle, scanner, db);
                        break;
                    default:
                        return;
                }
            }
        }
    }

    /**
     * Select and print statistics stored in database.
     *
     * @param bundle for internationalization
     * @param scanner to read user input
     * @param db database connection
     */
    private static void paginatedStatistics(ResourceBundle bundle, Scanner scanner, DatabaseUtils db) {
        Page statisticsPage = Page.first(10);
        List<Statistics> statisticsList;
        IO.println(bundle.getString("database.stat.columns"));
        while (true) {
            try (Session session = db.openSession()) {
                statisticsList = session.createSelectionQuery("from Statistics s order by s.id", Statistics.class)
                                        .setPage(statisticsPage).getResultList();
                if(statisticsList.isEmpty()) {
                    IO.println(bundle.getString("database.end.stat"));
                }
                statisticsList.forEach(statistics -> IO.println(printStatistics(statistics, bundle)));
                if(statisticsList.size() < statisticsPage.getSize()) {
                    IO.println(bundle.getString("database.end.records"));
                    break;
                }
                IO.println(bundle.getString("database.next"));
                if (!scanner.nextLine().trim().isEmpty()) {
                    break;
                }

                statisticsPage = statisticsPage.next();
            }
        }
    }

    private static void printDetailStatistics(ResourceBundle bundle, Scanner scanner, DatabaseUtils db) {
        IO.println(bundle.getString("database.id"));
        long id;
        try {
             id = scanner.nextLong();
             try (Session session = db.openSession()) {
                 Statistics statistics = session.find(Statistics.class, id);
                 if(null != statistics) {
                     IO.println(bundle.getString("database.stat.columns"));
                     IO.println(printStatistics(statistics, bundle));
                     IO.println(bundle.getString("card.deck") + " " + CardUtils.printCardsArray(statistics.getDeck()));
                     List<Player> players = statistics.getPlayers();
                     CardUtils.dealCards(players, statistics.getDeck());
                     IO.println(bundle.getString("database.player.hand"));
                     CardUtils.printPlayersWithHand(statistics.getPlayers(), bundle);

                 } else {
                     IO.println(bundle.getString("database.notFound"));
                 }
             }
        } catch (NoSuchElementException | IllegalStateException _) {
        } finally {
            scanner.nextLine();//flush new-line
        }
    }

    private static String printStatistics(Statistics statistics, ResourceBundle bundle) {
       return String.format("%5d | %s | %s | %d | %d", statistics.getId(), statistics.getDate(),
                            statistics.getWinner() != null ? bundle.getString("player") + statistics.getWinner() : bundle.getString("game.draw"), statistics.getNumberOfTricks(), statistics.getNumberOfWars());
    }
}
