package com.gestion.eventos.api.service;

import com.gestion.eventos.api.domain.Event;
import com.gestion.eventos.api.dto.EventRequestDto;
import com.gestion.eventos.api.dto.EventResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEventService {

    Page<EventResponseDto> findAll(String name, Pageable pageable);
    Event save(EventRequestDto requestDto);
    Event findById(Long id);
    Event update(Long id, EventRequestDto requestDto);
    void deleteById(Long id);
    List<Event> getAllEventsAndTheirDetailsProblematic();
    List<Event> getAllEventsAndTheirDetailsOptimizedWithJoinFetch();
    List<Event> findAllEventsWithAllDetailsOptimized();
}
