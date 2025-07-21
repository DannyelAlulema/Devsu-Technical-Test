package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.exceptions.ResourceNotFoundException;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final ModelMapper mapper;

	public ClientServiceImpl(ClientRepository clientRepository, ModelMapper mapper) {
		this.clientRepository = clientRepository;
		this.mapper = mapper;
	}

	@Override
	public List<ClientDto> getAll() {
		List<ClientDto> clients = this.clientRepository.findAll()
				.stream()
				.map(client -> mapper.map(client, ClientDto.class))
				.collect(Collectors.toList());

		return clients;
	}

	@Override
	public ClientDto getById(Long id) {
		Client client = this.clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client with id " + id + " not found"));
		return this.mapper.map(client, ClientDto.class);
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		Client client = mapper.map(clientDto, Client.class);
		client.setId(null);
		Client clientSaved = this.clientRepository.save(client);
		return mapper.map(clientSaved, ClientDto.class);
	}

	@Override
	public ClientDto update(Long id, ClientDto clientDto) {
		Client client = this.clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client with id " + id + " not found"));

		client.setDni(clientDto.getDni());
		client.setName(clientDto.getName());
		client.setPassword(clientDto.getPassword());
		client.setGender(clientDto.getGender());
		client.setAge(clientDto.getAge());
		client.setAddress(clientDto.getAddress());
		client.setPhone(clientDto.getPhone());
		client.setActive(clientDto.isActive());

		Client clientSaved = this.clientRepository.save(client);
		return mapper.map(clientSaved, ClientDto.class);
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client client = this.clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client with id " + id + " not found"));

		client.setActive(partialClientDto.isActive());

		Client clientSaved = this.clientRepository.save(client);
		return mapper.map(clientSaved, ClientDto.class);
	}

	@Override
	public void deleteById(Long id) {
		if (!this.clientRepository.existsById(id))
			throw new ResourceNotFoundException("Client with id " + id + " not found");

		this.clientRepository.deleteById(id);
	}
}
