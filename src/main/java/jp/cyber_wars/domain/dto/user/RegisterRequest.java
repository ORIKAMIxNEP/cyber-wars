package jp.cyber_wars.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Size(max = 20) String name, @NotBlank @Size(max = 100) String password) {}
