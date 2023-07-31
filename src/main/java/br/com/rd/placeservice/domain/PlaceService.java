package br.com.rd.placeservice.domain;

import com.github.slugify.Slugify;

import br.com.rd.placeservice.api.PlaceRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PlaceService {
	private PlaceRepository placeRepository;
	private Slugify slg;

	public PlaceService(PlaceRepository placeRepository) {
		this.placeRepository = placeRepository;
		this.slg = Slugify.builder().build();
	}

	public Flux<Place> getAll() {
		return placeRepository.findAll();
	}

	public Mono<Place> getById(Long id) {
		return placeRepository.findById(id);
	}

	public Mono<Place> create(PlaceRequest placeRequest) {
		var place = new Place(null, placeRequest.name(), this.slg.slugify(placeRequest.name()), placeRequest.state(),
				null, null);
		return placeRepository.save(place);
	}

	public Mono<Place> update(Long id, PlaceRequest placeRequest) {
		var placeFound = placeRepository.findById(id);

		return placeFound.flatMap(placeF -> {
			var place = new Place(placeF.id(), placeRequest.name(), this.slg.slugify(placeRequest.name()), placeRequest.state(),
					placeF.createAt(), null);
			return placeRepository.save(place);
		});
	}

	public Mono<Void> delete(Long id) {
		return placeRepository.deleteById(id);
	}

}
