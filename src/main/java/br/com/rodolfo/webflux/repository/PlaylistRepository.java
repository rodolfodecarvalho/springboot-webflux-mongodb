package br.com.rodolfo.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import br.com.rodolfo.webflux.document.Playlist;

@Repository
public interface PlaylistRepository extends ReactiveMongoRepository<Playlist, String> {

}