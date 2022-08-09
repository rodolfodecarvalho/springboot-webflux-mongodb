package br.com.rodolfo.webflux.handler;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import br.com.rodolfo.webflux.document.Playlist;
import br.com.rodolfo.webflux.services.PlaylistService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PlaylistHandler {

	private final PlaylistService playlistService;

	public Mono<ServerResponse> findAll(ServerRequest request) {
		return ok().contentType(MediaType.APPLICATION_JSON).body(playlistService.findAll(), Playlist.class);
	}

	public Mono<ServerResponse> findById(ServerRequest request) {
		return playlistService.findById(request.pathVariable("id"))
				.flatMap(playlist -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(playlist))
				.switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
						.bodyValue("Not found id " + request.pathVariable("id")));
	}

	public Mono<ServerResponse> create(ServerRequest request) {
		final Mono<Playlist> playlist = request.bodyToMono(Playlist.class);

		return playlist.flatMap(p -> ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
				.body(playlistService.create(p), Playlist.class));
	}

	public Mono<ServerResponse> update(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Playlist> updatedPlaylist = request.bodyToMono(Playlist.class);

		return updatedPlaylist.flatMap(p -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(playlistService.update(id, p), Playlist.class));
	}

	public Mono<ServerResponse> deleteById(ServerRequest request) {
		return playlistService.deleteById(request.pathVariable("id")).flatMap(p -> ServerResponse.ok().bodyValue(p))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}