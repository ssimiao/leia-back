package br.com.character.race;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class RaceRepository implements PanacheRepositoryBase<RaceEntity, String> {

    public RaceEntity findByNameAndColor(String name, String color){
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("color", color);
        return find("name = :name and color = :color", params).firstResult();
    }

}
