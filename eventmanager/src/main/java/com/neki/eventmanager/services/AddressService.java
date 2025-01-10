package com.neki.eventmanager.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neki.eventmanager.dtos.ViaCepDto;
import com.neki.eventmanager.models.Address;

@Service
public class AddressService {

	public static Address fillAddressViaCep(String postalCode, int number, String complement) {
		String url = "https://viacep.com.br/ws/" + postalCode + "/json";
		Address address = null;

		try {
			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
			HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

			if (httpResponse.statusCode() == 200) {
				ObjectMapper objectMapper = new ObjectMapper();
				ViaCepDto viaCepDto = objectMapper.readValue(httpResponse.body(), ViaCepDto.class);

				if (viaCepDto != null) {
					address = new Address();
					address.setPostalCode(viaCepDto.getCep());
					address.setStreet(viaCepDto.getLogradouro());
					address.setNeighborhood(viaCepDto.getBairro());
					address.setCity(viaCepDto.getLocalidade());
					address.setState(viaCepDto.getUf());
					address.setNumber(number);
					address.setComplement(complement);
				} else {
					throw new RuntimeException("CEP inválido ou não encontrado.");
				}
			} else {
				throw new RuntimeException(
						"Falha na comunicação com o serviço ViaCep. Status: " + httpResponse.statusCode());
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao consultar o serviço ViaCep: " + e.getMessage(), e);
		}

		return address;
	}
}
