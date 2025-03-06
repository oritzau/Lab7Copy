package edu.skidmore.cs276.lab07.persistence;

import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import edu.skidmore.cs276.lab07.beans.medical.Provider;

/**
 * Object-Relational Mapping: handles data manipulation between Java objects and
 * the RDB.
 * 
 * Used as part of a secure coding training application Copyright 2014 by David
 * S. Read
 * 
 * @author David Read
 * @version 01.00.00
 */
public class ORM {
	/**
	 * Instance of this class - implemented as a singleton.
	 */
	private static ORM ormInstance;

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(ORM.class);

	/**
	 * Creates instance of this class - private constructor since this is a
	 * singleton.
	 */
	private ORM() {
	}

	/**
	 * Singleton instance access.
	 * 
	 * @return The instance of the class
	 */
	public static synchronized ORM instance() {
		if (ormInstance == null) {
			LOGGER.info("Creating new ORM instance");
			ormInstance = new ORM();
		}

		return ormInstance;
	}

	/**
	 * Load the provider details from the database and return a Provider object.
	 * 
	 * @param providerId The ID of the provider whose details are to be loaded
	 */
	public Provider getProviderDetails(String providerId) {
		String sql;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Provider provider = null;

		try {
			sql = "select * from general_info2.provider where PROVIDER_ID = ?";

			stmt = DatabaseConnection.instance().getPreparedStatement(sql);
			stmt.setString(1, providerId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				LOGGER.info("Found provider id: " + rs.getString("PROVIDER_ID") + " with NPI: " + rs.getString("NPI"));

				provider = new Provider();
				provider.setId(rs.getString("PROVIDER_ID"));
				provider.setNpi(rs.getString("NPI"));
				provider.setFirstName(rs.getString("FNAME"));
				provider.setLastName(rs.getString("LNAME"));
				provider.setCity(rs.getString("CITY"));
				provider.setState(rs.getString("STATE"));
				provider.setZip(rs.getString("ZIP"));
				provider.setSpeciality(rs.getString("SPECIALTY"));
				provider.setFacility(rs.getString("FACILITY_TYPE"));
			}
		} catch (SQLException sqlExc) {
			LOGGER.error("Cannot read the database", sqlExc);
			throw new IllegalStateException("Cannot read the provider details", sqlExc);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlExc) {
					LOGGER.error("Unable to close the result set", sqlExc);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlExc) {
					LOGGER.error("Unable to close the statement", sqlExc);
				}
			}
		}

		return provider;
	}
	
	private boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	/**
	 * Search for matching providers using the supplied criteria. Return a list of
	 * matches.
	 * 
	 * @param firstName  The beginning of the first name (optional)
	 * @param lastName   The beginning of the last name (optional)
	 * @param speciality The provider's speciality (required)
	 */
	public List<Provider> getProviderList(String firstName, String lastName, String speciality) {
		String sql;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Provider provider;
		List<Provider> providerList = new ArrayList<>();
		int index = 2;

		try {
			sql = "select * from general_info2.provider where SPECIALTY = ?";
			if (!isEmpty(lastName)) {
				sql += " and LNAME like ?";
			}

			if (!isEmpty(firstName)) {
				sql += " and FNAME like ?";
			}

			sql += " order by LNAME, FNAME, NPI";
			LOGGER.info("SQL before setting values: " + sql);
			stmt = DatabaseConnection.instance().getPreparedStatement(sql);
			stmt.setString(1, speciality);
			if (!isEmpty(lastName)) {
				stmt.setString(index, lastName + "%");
				index++;
			}

			if (!isEmpty(firstName)) {
				stmt.setString(index, firstName + "%");
			}
			LOGGER.info("SQL after setting values: " + stmt);
			rs = stmt.executeQuery();

			while (rs.next()) {
				LOGGER.info("Found provider id: " + rs.getString("PROVIDER_ID") + " with NPI: " + rs.getString("NPI"));

				provider = new Provider();
				provider.setId(rs.getString("PROVIDER_ID"));
				provider.setNpi(rs.getString("NPI"));
				provider.setFirstName(rs.getString("FNAME"));
				provider.setLastName(rs.getString("LNAME"));
				provider.setState(rs.getString("STATE"));
				provider.setSpeciality(rs.getString("SPECIALTY"));
				provider.setFacility(rs.getString("FACILITY_TYPE"));
				providerList.add(provider);
			}
		} catch (SQLException sqlExc) {
			LOGGER.error("Cannot read the database", sqlExc);
			throw new IllegalStateException("Cannot read the provider information", sqlExc);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlExc) {
					LOGGER.error("Unable to close the result set", sqlExc);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlExc) {
					LOGGER.error("Unable to close the statement", sqlExc);
				}
			}

		}

		LOGGER.info("Resulting provider list of size: " + providerList.size());
		return providerList;
	}

	/**
	 * Retrieve the list of known provider specialities from the database and return
	 * them in an array.
	 */
	public String[] getSpecialities() {
		String sql;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> specialities = new ArrayList<String>();

		try {
			sql = "select distinct(SPECIALTY) from general_info2.provider order by SPECIALTY";

			stmt = DatabaseConnection.instance().getPreparedStatement(sql);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				LOGGER.info("Found speciality: " + rs.getString(1));
				specialities.add(rs.getString(1));
			}
		} catch (SQLException sqlExc) {
			LOGGER.error("Cannot read the database", sqlExc);
			throw new IllegalStateException("Cannot read the provider specialities", sqlExc);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlExc) {
					LOGGER.error("Unable to close the result set", sqlExc);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlExc) {
					LOGGER.error("Unable to close the statement", sqlExc);
				}
			}

		}

		return specialities.toArray(new String[specialities.size()]);
	}
}
