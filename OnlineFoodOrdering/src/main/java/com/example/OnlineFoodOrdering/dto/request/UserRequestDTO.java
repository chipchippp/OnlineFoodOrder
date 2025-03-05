package com.example.OnlineFoodOrdering.dto.request;

import com.example.OnlineFoodOrdering.dto.validator.*;
import com.example.OnlineFoodOrdering.util.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

import static com.example.OnlineFoodOrdering.dto.validator.Gender.*;

@Getter
public class UserRequestDTO {
    @NotBlank(message = "Full name must not be blank")
    private String fullName;
    @Email(message = "Email invalid format")
    private String email;

    @PhoneNumber
    private String phone;

    @NotNull(message = "Date of birth must not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @NotNull(message = "Username must not be null")
    private String username;
    @NotNull(message = "Password must not be null")
    private String password;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})
    private Gender gender;

    @EnumPattern(name = "status", regexp = "^ACTIVE|INACTIVE|DELETED$", message = "Invalid status")
    private UserStatus status;

    @NotEmpty(message = "Address must not be empty")
    private Set<AddressDTO> addresses;
}
