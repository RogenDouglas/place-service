package br.com.rd.placeservice.api;

import java.time.LocalDateTime;

public record PlaceResponse(Long id, String name, String slug, String state, LocalDateTime createAt, LocalDateTime updateAt) {

}
