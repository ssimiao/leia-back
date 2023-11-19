package br.com.book;

import br.com.character.classe.ClasseEntity;
import br.com.user.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class BookRepository implements PanacheRepositoryBase<BookEntity, Long> {

    public BookEntity findByIsbn(String isbn){
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", isbn);
        return find("isbn = :isbn or isbn13 = :isbn", params).firstResult();
    }

}
