package com.picpaysimplificado.DTOs;

import com.picpaysimplificado.Domain.users.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
}
