package br.com.character;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CharacterRepository implements PanacheRepositoryBase<CharacterEntity, Long> {

}
