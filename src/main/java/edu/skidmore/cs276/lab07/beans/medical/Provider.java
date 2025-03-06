package edu.skidmore.cs276.lab07.beans.medical;

import java.io.Serializable;

/**
 * Java bean represents a medical provider
 * 
 * @author David Read
 * @version 01.00.00
 *
 */
public class Provider implements Serializable {
	/**
	 * The internal version id of this class
	 */
	private static final long serialVersionUID = 19620501;

	/**
	 * A unique identifier for the provider.
	 */
	private String id;

	/**
	 * National provider identifier
	 */
	private String npi;

	/**
	 * First name
	 */
	private String firstName;

	/**
	 * Last name
	 */
	private String lastName;

	/**
	 * City
	 */
	private String city;

	/**
	 * State
	 */
	private String state;

	/**
	 * Zip code
	 */
	private String zip;

	/**
	 * Medical speciality
	 */
	private String speciality;

	/**
	 * Facility type
	 */
	private String facility;

	/**
	 * Create a provider
	 */
	public Provider() {
	}

	/**
	 * Get the system provider identifier
	 * 
	 * @return The provider identifier
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the system provider identifier
	 * 
	 * @param id
	 *          The provider identifier
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the national provider identifier
	 * 
	 * @return
	 */
	public String getNpi() {
		return npi;
	}

	/**
	 * Set the national provider identifier
	 * 
	 * @param npi
	 *          The national provider identifier
	 */
	public void setNpi(String npi) {
		this.npi = npi;
	}

	/**
	 * Get the provider's first name
	 * 
	 * @return The first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set the provider's first name
	 * 
	 * @param firstName
	 *          The first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the provider's last name
	 * 
	 * @return The last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the provider's last name
	 * 
	 * @param lastName
	 *          The last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the provider's city
	 * 
	 * @return The city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Set the provider's city
	 * 
	 * @param city
	 *          The city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Get the provider's state
	 * 
	 * @return The state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Set the provider's state
	 * 
	 * @param state
	 *          The state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Get the provider's zip code
	 * 
	 * @return The zip code
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Set the provider's zip code
	 * 
	 * @param zip
	 *          The zip code
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Get the provider's speciality
	 * 
	 * @return The speciality
	 */
	public String getSpeciality() {
		return speciality;
	}

	/**
	 * Set the provider's speciality
	 * 
	 * @param speciality
	 *          The speciality
	 */
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	/**
	 * Get the provider's facility type
	 * 
	 * @return The facility type
	 */
	public String getFacility() {
		return facility;
	}

	/**
	 * Set the provider's facility type
	 * 
	 * @param facility
	 *          The facility type
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}

	/**
	 * Get a String representation of the provider. This returns the id, name and
	 * NPI
	 * 
	 * @return The provider's id, name and NPI
	 */
	public String toString() {
		return getId() + ":" + getFirstName() + " " + getLastName() + " ("
				+ getNpi() + ")";
	}
}
