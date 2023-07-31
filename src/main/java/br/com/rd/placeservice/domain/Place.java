package br.com.rd.placeservice.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

public record Place(@Id Long id, String name, String slug, String state, @CreatedDate LocalDateTime createAt,
		@LastModifiedDate LocalDateTime updateAt) {

}
