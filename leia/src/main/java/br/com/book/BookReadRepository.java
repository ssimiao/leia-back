package br.com.book;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class BookReadRepository implements PanacheRepositoryBase<BookReadEntity, Long> {

    public List<BookReadEntity> findByUserId(Long id){
        return find("user.id", id).stream().toList();
    }

    public List<BookReadEntity> findByUserIdAndIsbn(Long id, String isbn){
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", isbn);
        params.put("id", id);
        return find("user.id =:id and (book.isbn = :isbn or book.isbn13 = :isbn)", params).stream().toList();
    }
}
