package jp.ac.anan.procon.cyber_wars.domain.dto.user.credential;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LogInRequest(
    @NotBlank @Size(max = 20) String name, @NotBlank @Size(max = 100) String password) {}
