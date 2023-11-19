package br.com.character;

import br.com.book.BookReadEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CharacterRepository implements PanacheRepositoryBase<CharacterEntity, Long> {

    public CharacterEntity findByUserId(Long id){
        return find("user.id", id).firstResult();
    }
}
