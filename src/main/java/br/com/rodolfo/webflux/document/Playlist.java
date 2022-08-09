package br.com.rodolfo.webflux.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(value = "playlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = { "id", "name" })
public class Playlist implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name;
}