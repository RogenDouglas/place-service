package br.com.rd.placeservice.web;

import br.com.rd.placeservice.api.PlaceResponse;
import br.com.rd.placeservice.domain.Place;

public class PlaceMapper {

	public static PlaceResponse fromPlaceToResponse(Place place) {
		return new PlaceResponse(place.id(), place.name(), place.slug(), place.state(), place.createAt(), place.updateAt());
	}

}
