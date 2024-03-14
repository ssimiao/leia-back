package br.com.book.note;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public class NotesRepository implements PanacheRepositoryBase<NotesEntity, Long> {
}
