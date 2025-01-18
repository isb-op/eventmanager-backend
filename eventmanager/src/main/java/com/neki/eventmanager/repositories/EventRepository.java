package com.neki.eventmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.neki.eventmanager.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	public List<Event> findByUser_IdUser(Long id);
}
