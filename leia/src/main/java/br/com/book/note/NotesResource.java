package br.com.book.note;

import br.com.book.BookReadEntity;
import br.com.book.BookReadRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/v1/notes")
public class NotesResource {

    @Inject
    private NotesRepository notesRepository;

    @Inject
    private BookReadRepository bookReadRepository;

    @POST
    @Transactional
    public Response createNote(NotesRequest request) {
        BookReadEntity read = bookReadRepository.findById(request.getIdRead());
        NotesEntity notesEntity = new NotesEntity(request.getDescription(), request.getRelatedPageChapter());
        notesRepository.persist(notesEntity);
        read.getNote().add(notesEntity);
        bookReadRepository.persist(read);

        return Response.ok(read.getNote()).build();
    }

    @PUT
    @Transactional
    public Response updateNote(NotesRequest request) {
        NotesEntity note = notesRepository.findById(request.getIdNote());
        note.setDescription(request.getDescription());
        note.setRelatedPageChapter(request.getRelatedPageChapter());
        notesRepository.persist(note);

        return Response.ok(note).build();
    }

    @DELETE
    @Transactional
    public Response deleteNote(NotesRequest request) {
        notesRepository.deleteById(request.getIdNote());
        return Response.noContent().build();
    }
}
