package br.com.character.race;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RaceRepository implements PanacheRepositoryBase<RaceEntity, String> {


}
