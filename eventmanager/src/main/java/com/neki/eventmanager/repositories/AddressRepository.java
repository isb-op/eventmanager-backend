package com.neki.eventmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neki.eventmanager.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	Address findByPostalCode(String postalCode);
}
