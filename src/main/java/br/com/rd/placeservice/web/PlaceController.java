package br.com.rd.placeservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rd.placeservice.api.PlaceRequest;
import br.com.rd.placeservice.api.PlaceResponse;
import br.com.rd.placeservice.domain.PlaceService;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places")
public class PlaceController {
	private PlaceService placeService;

	public PlaceController(PlaceService placeService) {
		this.placeService = placeService;
	}

	@GetMapping
	public ResponseEntity<Flux<PlaceResponse>> getAll() {
		var response = placeService.getAll().map(PlaceMapper::fromPlaceToResponse);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Mono<PlaceResponse>> getById(@PathVariable("id") Long id) {
		var response = placeService.getById(id).map(PlaceMapper::fromPlaceToResponse);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	public ResponseEntity<Mono<PlaceResponse>> create(@Valid @RequestBody PlaceRequest placeRequest) {
		var response = placeService.create(placeRequest).map(PlaceMapper::fromPlaceToResponse);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Mono<PlaceResponse>> update(@Valid @PathVariable("id") Long id,
			@RequestBody PlaceRequest placeRequest) {
		var response = placeService.update(id, placeRequest).map(PlaceMapper::fromPlaceToResponse);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Mono<Void>> delete(@PathVariable("id") Long id) {
		placeService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}
