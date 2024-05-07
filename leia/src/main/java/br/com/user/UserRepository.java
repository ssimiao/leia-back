package br.com.user;

import br.com.character.CharacterEntity;
import br.com.character.race.RaceEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserEntity, Long> {

    public UserEntity findByEmail(String email){
        return find("email", email).firstResult();
    }
}
