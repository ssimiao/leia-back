package br.com.book.note;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotesRepository implements PanacheRepositoryBase<NotesEntity, Long> {
}
