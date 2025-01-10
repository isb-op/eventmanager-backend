package com.neki.eventmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.neki.eventmanager.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByLogin(String login);

	 
}

