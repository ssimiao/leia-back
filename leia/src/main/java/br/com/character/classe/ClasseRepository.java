package br.com.character.classe;

import br.com.character.race.RaceEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClasseRepository implements PanacheRepositoryBase<ClasseEntity, Long> {

    public ClasseEntity findByName(String name){
        return find("name", name).firstResult();
    }
}
