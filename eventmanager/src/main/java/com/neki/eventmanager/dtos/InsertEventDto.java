package com.neki.eventmanager.dtos;

import java.time.LocalDateTime;

import com.neki.eventmanager.models.Event;

public class InsertEventDto {
	private String eventTitle;
	private LocalDateTime eventDate;
	private String photoUrl;
	private String postalCode;
	private int number;
	private String complement;

	public InsertEventDto() {
	}

	public InsertEventDto(Event event) {
		this.eventTitle = event.getEventTitle();
		this.eventDate = event.getEventDate();
		this.photoUrl = event.getPhotoUrl();
		this.postalCode = event.getAddress().getPostalCode();
		this.number = event.getAddress().getNumber();
		this.complement = event.getAddress().getComplement();
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Override
	public String toString() {
		return "InsertEventDto [eventTitle=" + eventTitle + ", eventDate=" + eventDate + ", photoUrl=" + photoUrl
				+ ", postalCode=" + postalCode + ", number=" + number + ", complement=" + complement + "]";
	}

}
