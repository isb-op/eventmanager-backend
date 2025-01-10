package com.neki.eventmanager.dtos;

public class AddressDto {
	private Long idAddress;
	private String postalCode;
	private String street;
	private String neighborhood;
	private String city;
	private int number;
	private String complement;
	private String state;

	public AddressDto() {

	}

	public AddressDto(Long idAddress, String postalCode, String street, String neighborhood, String city, int number,
			String complement, String state) {
		this.idAddress = idAddress;
		this.postalCode = postalCode;
		this.street = street;
		this.neighborhood = neighborhood;
		this.city = city;
		this.number = number;
		this.complement = complement;
		this.state = state;
	}

	public Long getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(Long idAddress) {
		this.idAddress = idAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "AddressDto{" + "idAddress=" + idAddress + ", postalCode='" + postalCode + '\'' + ", street='" + street
				+ '\'' + ", neighborhood='" + neighborhood + '\'' + ", city='" + city + '\'' + ", number=" + number
				+ ", complement='" + complement + '\'' + ", state='" + state + '\'' + '}';
	}
}