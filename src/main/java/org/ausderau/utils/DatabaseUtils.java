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

package org.ausderau.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Provides utils methods to connect to the database and start/close session
 */
public class DatabaseUtils implements AutoCloseable {
    private final StandardServiceRegistry registry;
    private final SessionFactory sessionFactory;

    /**
     * Constructs and initialize the database connection.
     * @param entityClasses the list of entity classes that can be stored to database
     */
    public DatabaseUtils(Class<?>... entityClasses) {
        registry = new StandardServiceRegistryBuilder().build();
        try {
            MetadataSources sources = new MetadataSources(registry);
            for(Class<?> entityClass : entityClasses) {
                sources.addAnnotatedClass(entityClass);
            }
            sessionFactory = sources.buildMetadata().buildSessionFactory();
        } catch (RuntimeException runtimeException) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw runtimeException;
        }
    }

    /** Opens a new session. The caller must close it. */
    public Session openSession() {
        return sessionFactory.openSession();
    }

    /**
     * Closes the database connection.
     */
    @Override
    public void close() {
        try {
            sessionFactory.close();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
