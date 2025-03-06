package edu.skidmore.cs276.lab07.persistence;

import java.util.Enumeration;
import java.util.Properties;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Manages database connections.
 * 
 * Not suitable for production applications.
 *
 * Used as part of a secure coding training application Copyright 2014 by David
 * S. Read
 *
 * @author David Read
 * @version 01.00.00
 */
public class DatabaseConnection {
	/**
	 * The property name containing the DB driver value.
	 */
	public static final String PROP_DRIVER = "driver";

	/**
	 * The property name containing the DB URL.
	 */
	public static final String PROP_URL = "url";

	/**
	 * The property name containing the DB user id.
	 */
	public static final String PROP_UID = "userId";

	/**
	 * The property name containing the DB password.
	 */
	public static final String PROP_PASSWORD = "password";

	/**
	 * Logger Instance
	 */
	private static Logger logger = Logger.getLogger(DatabaseConnection.class);

	/**
	 * The DB connection properties
	 */
	private Properties dbConnectionProperties;

	/**
	 * Instance of class - implemented as singleton
	 */
	private static DatabaseConnection db;

	/**
	 * The JDBC connection to the database.
	 */
	private Connection conn;

	/**
	 * Creates class instance - private since this is a singleton.
	 */
	private DatabaseConnection() {
	}

	/**
	 * Get the Singleton instance
	 * 
	 * @return The instance
	 */
	public static synchronized DatabaseConnection instance() {
		if (db == null) {
			logger.info("Create new DatabaseConnection instance");
			db = new DatabaseConnection();
		}

		return db;
	}

	/**
	 * Configure the database connection.
	 * 
	 * @param driver   The JDBC driver
	 * @param dbUrl    The connection URL for the database
	 * @param userId   The user id for the connection
	 * @param password The password for the connection
	 */
	public void setDatabaseProperties(String driver, String dbUrl, String userId, String password) {
		dbConnectionProperties = new Properties();
		dbConnectionProperties.setProperty(PROP_DRIVER, driver);
		dbConnectionProperties.setProperty(PROP_URL, dbUrl);
		dbConnectionProperties.setProperty(PROP_UID, userId);
		dbConnectionProperties.setProperty(PROP_PASSWORD, password);

		try {
			Class.forName(dbConnectionProperties.getProperty(PROP_DRIVER));
		} catch (ClassNotFoundException cnf) {
			logger.error("Could not find DB driver class to register ["
					+ dbConnectionProperties.getProperty(PROP_DRIVER) + "]", cnf);
		}

		try {
			setupConnection();
		} catch (SQLException sqle) {
			logger.error("Unable to connect to the database", sqle);
			throw new IllegalStateException("Unable to connect to the database", sqle);
		}
	}

	/**
	 * Get a connection to the database. The same connection is returned with each
	 * call, unless it is closed.
	 * 
	 * @return A database connection
	 * @throws SQLException If a connection cannot be established
	 */
	private Connection getConnection() throws SQLException {
		if (conn != null && !conn.isValid(2)) {
			logger.warn("Database connection is no longer valid, reconnecting");
			setupConnection();
		}

		return conn;
	}

	/**
	 * Creates a new statement for DB interaction.
	 *
	 * @return Statement The new statement
	 *
	 * @throws SQLException          If the statement cannot be created
	 * @throws IllegalStateException If the database connection properties have not
	 *                               been set
	 */
	public Statement getStatement() throws SQLException {
		if (dbConnectionProperties == null) {
			logger.error("Database not initialized");
			throw new IllegalStateException(
					"The database connection has not been initialized. Must call setDatabaseProperties()");
		}

		return getConnection().createStatement();
	}

	/**
	 * Create a prepared statement using the supplied SQL statement.
	 * 
	 * @param statement The SQL statement to execute
	 * @return PreparedStatement The prepared statement instance
	 * @throws SQLException If an error occurs
	 */
	public PreparedStatement getPreparedStatement(String statement) throws SQLException {
		if (dbConnectionProperties == null) {
			logger.error("Database not initialized");
			throw new IllegalStateException(
					"The database connection has not been initialized. Must call setDatabaseProperties()");
		}

		return getConnection().prepareStatement(statement);
	}

	/**
	 * Sets-up the DB connection based on the database configuration in the
	 * properties file.
	 *
	 * @throws SQLException If the DB connection cannot be made.
	 */
	private void setupConnection() throws SQLException {
		logger.debug("Setting up data source connection.");

		conn = DriverManager.getConnection(dbConnectionProperties.getProperty(PROP_URL),
				dbConnectionProperties.getProperty(PROP_UID), dbConnectionProperties.getProperty(PROP_PASSWORD));
		// EncryptionLibrary.decrypt(dbConnectionProperties.getProperty(PROP_PASSWORD)));
		logger.debug("Done setting up data source connection");
	}

	/**
	 * Close the database connection and unload the JDBC drivers.
	 */
	public void closeDatabaseConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Throwable t) {
				logger.warn("Unable to close JDBC connection", t);
			}
		}

		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {

			Driver driver = drivers.nextElement();
			try {
				logger.info("Deregister JDBC driver: " + driver.toString());
				DriverManager.deregisterDriver(driver);
			} catch (SQLException sqlExc) {
				logger.warn("Failed to deregister a JDBC driver: " + driver.toString());
			}
		}
		db = null;
	}
}
