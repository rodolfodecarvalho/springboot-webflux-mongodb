package br.com.rodolfo.webflux.services.impl;

import java.util.Collections;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodolfo.webflux.document.Playlist;
import br.com.rodolfo.webflux.repository.PlaylistRepository;
import br.com.rodolfo.webflux.services.PlaylistService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistServiceImpl implements PlaylistService {

	private final ReactiveMongoTemplate reactiveMongoTemplate;

	private final PlaylistRepository playlistRepository;

	@Override
	public Flux<Playlist> findAll() {
		return playlistRepository.findAll();
	}

	@Override
	public Mono<Playlist> findById(String id) {
		return playlistRepository.findById(id);
	}

	@Override
	public Mono<Playlist> create(Playlist playlist) {
		return playlistRepository.save(playlist);
	}

	@Override
	public Mono<Playlist> update(String id, Playlist playlist) {
		return playlistRepository.findById(id).flatMap(dbPlaylist -> {
			dbPlaylist.setName(playlist.getName());
			return playlistRepository.save(dbPlaylist);
		});
	}

	@Override
	public Mono<Playlist> deleteById(String id) {
		return playlistRepository.findById(id).flatMap(
				existingPlaylist -> playlistRepository.delete(existingPlaylist).then(Mono.just(existingPlaylist)));
	}

	@Override
	public Flux<Playlist> fetchUsers(String name) {
		Query query = new Query().with(Sort.by(Collections.singletonList(Sort.Order.asc("name"))));
		query.addCriteria(Criteria.where("name").regex(name));

		return reactiveMongoTemplate.find(query, Playlist.class);
	}
}