package com.neki.eventmanager.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neki.eventmanager.dtos.EventResponseDto;
import com.neki.eventmanager.dtos.InsertEventDto;
import com.neki.eventmanager.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/events")
public class EventController {
	@Autowired
	private EventService eventService;

	@PostMapping
	@Operation(summary = "Criação de evento", description = "Cria um novo evento com inclusão de uma foto.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Evento criado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados fornecidos."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<EventResponseDto> createEvent(@RequestBody @Valid InsertEventDto insertEventDto)
			throws Exception {
		EventResponseDto eventResponseDto = eventService.insertEventPhotoUrl(insertEventDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(eventResponseDto);
	}

	@GetMapping
	@Operation(summary = "Listar todos os eventos", description = "Retorna uma lista com todos os eventos cadastrados.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<List<EventResponseDto>> listAllEvents() {
		return ResponseEntity.ok(eventService.listEvents());
	}

	@GetMapping("/{idEvent}")
	@Operation(summary = "Consulta de evento por ID", description = "Retorna os detalhes de um evento específico com base no seu ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso."),
			@ApiResponse(responseCode = "404", description = "Evento não encontrado. Certifique-se de que o ID está correto."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<EventResponseDto> searchEventById(@PathVariable Long idEvent) {
		return ResponseEntity.ok(eventService.getEventById(idEvent));
	}

	@GetMapping("/events-user/{userId}")
	@Operation(summary = "Listar eventos por usuário", description = "Retorna todos os eventos associados a um usuário específico com base no ID do usuário.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de eventos do usuário retornada com sucesso."),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado. Certifique-se de que o ID está correto."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<List<EventResponseDto>> getEventsByUserId(@PathVariable Long userId) {
		List<EventResponseDto> events = eventService.getEventsByIdUser(userId);
		return ResponseEntity.ok(events);
	}

	@PutMapping("/{idEvent}")
	@Operation(summary = "Atualizar evento", description = "Atualiza os dados de localização e data de um evento existente com base no ID fornecido.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados fornecidos."),
			@ApiResponse(responseCode = "404", description = "Evento não encontrado. Certifique-se de que o ID está correto."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<EventResponseDto> changeEvent(@PathVariable Long idEvent,
			@Valid @RequestBody InsertEventDto insertEventDto) throws IOException {
		EventResponseDto eventUpdate = eventService.updateEvent(idEvent, insertEventDto);
		return ResponseEntity.ok(eventUpdate);
	}

	@DeleteMapping("/{idEvent}")
	@Operation(summary = "Remover evento", description = "Remove um evento existente com base no ID fornecido.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Evento removido com sucesso."),
			@ApiResponse(responseCode = "404", description = "Evento não encontrado. Certifique-se de que o ID está correto."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<EventResponseDto> removeEvent(@PathVariable Long idEvent) throws IOException {
		eventService.deleteEvent(idEvent);
		return ResponseEntity.noContent().build();
	}

}
