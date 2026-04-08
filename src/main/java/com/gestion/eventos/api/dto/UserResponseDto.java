package com.gestion.eventos.api.dto;



import com.gestion.eventos.api.domain.Event;

import java.util.List;

public class UserResponseDto {
    private Long id;
    private String name;
    private String username;
    private String email;

    private List<RoleDto> roles;
    private List<EventSummaryDto> attendedEvents;

}
