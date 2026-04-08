package com.gestion.eventos.api.mapper;

import com.gestion.eventos.api.domain.Event;
import com.gestion.eventos.api.domain.Role;
import com.gestion.eventos.api.domain.User;
import com.gestion.eventos.api.dto.EventSummaryDto;
import com.gestion.eventos.api.dto.UserResponseDto;
import com.gestion.eventos.api.security.dto.RegisterDto;
import com.gestion.eventos.api.exception.ResourceNotFoundException;
import com.gestion.eventos.api.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected EventMapper eventMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", source = "dto.roles", qualifiedByName = "mapRoleStringstoRoles")
    @Mapping(target = "attendedEvents", ignore = true)
    public abstract User registerDtoToUser(RegisterDto dto);

    @Named("mapRoleStringstoRoles")
    public Set<Role> mapRoleStringstoRoles(Set<String> roleNames){
        if(roleNames == null || roleNames.isEmpty()){
            return roleRepository.findByName("ROLE_USER").map(Collections::singleton).orElseThrow(() -> new ResourceNotFoundException("Error: Rol 'ROLE_USER' no encontrado en base de datos. " +
                    "Asegúrate de que el rol 'ROLE_USER' exista al iniciar la aplicación"));
        }
        return roleNames.stream().map(role -> roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Rol no encontrado " + role))).collect(Collectors.toSet());
    }

    //--------------------------------------------------------------------------------
//    @Mapping(target = "password" , ignore = true)
//    @Mapping(target = "roles", source = "user.roles",qualifiedByName = "mapSetRolesToListRoles")
//    @Mapping(target = "attendedEvents", source = "user.attendedEvents", qualifiedByName = "mapSetRolesToListRoles")
    public abstract UserResponseDto toUserResponseDto(User user);


//    @Mapping(target = "password", ignore = true)
//    @Mapping(target = "roles", source = "user.roles", qualifiedByName = "mapSetRolesToListRoles")
//    @Mapping(target = "attendedEvents", source = "user.attendedEvents", qualifiedByName = "mapSetRolesToListRoles")
    public abstract List<UserResponseDto> toUserResponseDtoList(List<User> users);


//    @Named("mapSetRolesToListRoles")
//    public List<Role> mapSetRolesToListRoles(Set<Role> roles){
//        return roles.stream().toList();
//    }
//
//    @Named("mapSetAttendedEventsToEventSummaryList")
//    public List<EventSummaryDto> mapSetAttendedEventsToEventSummaryList(Set<Event> attendedEvents){
//        return eventMapper.toSummaryDtoList(attendedEvents.stream().toList());
//    }


}
