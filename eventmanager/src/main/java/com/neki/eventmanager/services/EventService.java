package com.neki.eventmanager.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.neki.eventmanager.dtos.EventResponseDto;
import com.neki.eventmanager.dtos.InsertEventDto;
import com.neki.eventmanager.exceptions.ResourceNotFoundException;
import com.neki.eventmanager.models.Address;
import com.neki.eventmanager.models.Event;
import com.neki.eventmanager.models.User;
import com.neki.eventmanager.repositories.EventRepository;
import com.neki.eventmanager.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public EventResponseDto insertEventPhotoUrl(InsertEventDto insertEventDto) throws Exception {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByLogin(login);

		if (user == null) {
			throw new IllegalArgumentException("Usuário não encontrado");
		}

		Event event = new Event();
		event.setEventTitle(insertEventDto.getEventTitle());
		event.setEventDate(insertEventDto.getEventDate());
		event.setPhotoUrl(insertEventDto.getPhotoUrl());
		event.setRegisteredAt(LocalDateTime.now());

		Address address = null;
		try {
			address = AddressService.fillAddressViaCep(insertEventDto.getPostalCode(), insertEventDto.getNumber(),
					insertEventDto.getComplement());
		} catch (RuntimeException e) {
			address = new Address();
			address.setPostalCode(insertEventDto.getPostalCode());
			address.setNumber(insertEventDto.getNumber());
			address.setComplement(insertEventDto.getComplement());
		}
		event.setAddress(address);
		event.setUser(user);
		Event savedEvent = eventRepository.save(event);
		return new EventResponseDto(savedEvent);
	}

	public EventResponseDto getEventById(Long idEvent) {
		Optional<Event> event = eventRepository.findById(idEvent);
		if (event.isPresent()) {
			return new EventResponseDto(event.get());
		}
		throw new ResourceNotFoundException("Evento não encontrado");
	}

	public List<EventResponseDto> listEvents() {
		List<Event> events = eventRepository.findAll();
		List<EventResponseDto> eventResponseDtos = new ArrayList<>();
		for (Event event : events) {
			eventResponseDtos.add(new EventResponseDto(event));
		}
		return eventResponseDtos;
	}

	public List<EventResponseDto> getEventsByIdUser(Long idUser) {
		List<Event> events = eventRepository.findByUser_IdUser(idUser);
		List<EventResponseDto> eventResponseDtos = new ArrayList<>();
		for (Event event : events) {
			eventResponseDtos.add(new EventResponseDto(event));
		}
		return eventResponseDtos;
	}

	@Transactional
	public EventResponseDto updateEvent(Long idEvent, InsertEventDto insertEventDto) throws IOException {
		Event event = eventRepository.findById(idEvent)
				.orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));
		event.setEventDate(insertEventDto.getEventDate());
		event.setModifiedAt(LocalDateTime.now());

		Address address = null;
		try {
			address = AddressService.fillAddressViaCep(insertEventDto.getPostalCode(), insertEventDto.getNumber(),
					insertEventDto.getComplement());
		} catch (RuntimeException e) {
			address = new Address();
			address.setPostalCode(insertEventDto.getPostalCode());
			address.setNumber(insertEventDto.getNumber());
			address.setComplement(insertEventDto.getComplement());
		}
		event.setAddress(address);
		Event updatedEvent = eventRepository.save(event);
		return new EventResponseDto(updatedEvent);
	}

	@Transactional
	public ResponseEntity<Void> deleteEvent(Long idEvent) throws IOException {
		if (eventRepository.existsById(idEvent)) {
			eventRepository.deleteById(idEvent);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
