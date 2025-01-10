package com.neki.eventmanager.dtos;

import java.time.LocalDateTime;
import com.neki.eventmanager.models.Event;

public class EventResponseDto {
	private Long userId;
	private String name;
	private Long idEvent;
	private String eventTitle;
	private LocalDateTime eventDate;
	private String photoUrl;
	private String postalCode;
	private String street;
	private String neighborhood;
	private String city;
	private int number;
	private String complement;
	private String state;

	public EventResponseDto() {
	}

	public EventResponseDto(Long userId, String name, Long idEvent, String eventTitle, LocalDateTime eventDate,
			String photoUrl, String postalCode, String street, String neighborhood, String city, int number,
			String complement, String state) {
		this.userId = userId;
		this.name = name;
		this.idEvent = idEvent;
		this.eventTitle = eventTitle;
		this.eventDate = eventDate;
		this.photoUrl = photoUrl;
		this.postalCode = postalCode;
		this.street = street;
		this.neighborhood = neighborhood;
		this.city = city;
		this.number = number;
		this.complement = complement;
		this.state = state;
	}

	public EventResponseDto(Event event) {
		this.userId = event.getUser().getIdUser();
		this.name = event.getUser().getName();
		this.idEvent = event.getIdEvent();
		this.eventTitle = event.getEventTitle();
		this.eventDate = event.getEventDate();
		this.photoUrl = event.getPhotoUrl();

		if (event.getAddress() != null) {
			this.postalCode = event.getAddress().getPostalCode();
			this.street = event.getAddress().getStreet();
			this.neighborhood = event.getAddress().getNeighborhood();
			this.city = event.getAddress().getCity();
			this.number = event.getAddress().getNumber();
			this.complement = event.getAddress().getComplement();
			this.state = event.getAddress().getState();
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Long idEvent) {
		this.idEvent = idEvent;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
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
}