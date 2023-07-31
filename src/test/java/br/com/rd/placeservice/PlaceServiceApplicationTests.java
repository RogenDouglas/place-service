package br.com.rd.placeservice;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.rd.placeservice.api.PlaceRequest;
import br.com.rd.placeservice.api.PlaceResponse;
import br.com.rd.placeservice.domain.PlaceService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PlaceServiceApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@Autowired
	PlaceService placeService;

	@Test
	public void testGetAllPlaceSuccess() {
		var name1 = "name valid 1";
		var state1 = "state valid 1";

		placeService.create(new PlaceRequest(name1, state1));

		var name2 = "name valid 2";
		var state2 = "state valid 2";

		placeService.create(new PlaceRequest(name2, state2));

		webTestClient.get().uri("/places").exchange().expectStatus().isOk().expectBodyList(PlaceResponse.class);

	}

	@Test
	public void testGetByIdPlaceSuccess() {
		var name = "name valid";
		var state = "state valid";
		var slug = "name-valid";

		var newPlace = placeService.create(new PlaceRequest(name, state)).block();

		webTestClient.get().uri("/places/{id}", Collections.singletonMap("id", newPlace.id())).exchange().expectStatus()
				.isOk().expectBody().jsonPath("name").isEqualTo(name).jsonPath("slug").isEqualTo(slug).jsonPath("state")
				.isEqualTo(state).jsonPath("createAt").isNotEmpty().jsonPath("updateAt").isNotEmpty();
	}

	@Test
	public void testCreatePlaceSuccess() {
		var name = "name valid";
		var state = "state valid";
		var slug = "name-valid";

		webTestClient.post().uri("/places").bodyValue(new PlaceRequest(name, state)).exchange().expectStatus()
				.isCreated().expectBody().jsonPath("name").isEqualTo(name).jsonPath("slug").isEqualTo(slug)
				.jsonPath("state").isEqualTo(state).jsonPath("createAt").isNotEmpty().jsonPath("updateAt").isNotEmpty();

	}

	@Test
	public void testUpdatePlaceSuccess() {
		var name = "name valid";
		var state = "state valid";

		var newPlace = placeService.create(new PlaceRequest(name, state)).block();

		var nameUP = "name valid up";
		var stateUP = "state valid up";
		var slugUP = "name-valid-up";

		webTestClient.put().uri("/places/{id}", Collections.singletonMap("id", newPlace.id()))
				.bodyValue(new PlaceRequest(nameUP, stateUP)).exchange().expectStatus().isOk().expectBody()
				.jsonPath("name").isEqualTo(nameUP).jsonPath("slug").isEqualTo(slugUP).jsonPath("state")
				.isEqualTo(stateUP);

	}

	@Test
	public void testDeleteByIdPlaceSuccess() {
		var name = "name valid";
		var state = "state valid";

		var newPlace = placeService.create(new PlaceRequest(name, state)).block();

		webTestClient.delete().uri("/places/{id}", Collections.singletonMap("id", newPlace.id())).exchange()
				.expectStatus().isNoContent();
	}
}
