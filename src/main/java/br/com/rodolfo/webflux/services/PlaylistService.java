package br.com.rodolfo.webflux.services;

import br.com.rodolfo.webflux.document.Playlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlaylistService {

	Flux<Playlist> findAll();

	Mono<Playlist> findById(String id);

	Mono<Playlist> create(Playlist playlist);

	Mono<Playlist> update(String id, Playlist playlist);

	Mono<Playlist> deleteById(String id);

	Flux<Playlist> fetchUsers(String name);
}