package com.ticketing.service;

import com.ticketing.dto.RoleDTO;
import com.ticketing.exception.TicketingProjectException;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();
    RoleDTO findById(Long id) throws TicketingProjectException;

}
