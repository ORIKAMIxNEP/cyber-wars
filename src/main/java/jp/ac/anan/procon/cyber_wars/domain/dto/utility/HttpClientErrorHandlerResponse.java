package jp.ac.anan.procon.cyber_wars.domain.dto.utility;

import org.springframework.http.ResponseEntity;

public record HttpClientErrorHandlerResponse(boolean error, ResponseEntity<?> responseEntity) {}
