package br.com.challenge;

import br.com.book.BookReadEntity;
import br.com.book.BookReadRepository;
import br.com.character.CharacterEntity;
import br.com.character.CharacterRepository;
import br.com.shared.exception.ReadException;
import br.com.shared.exception.ResourceNotFoundException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.Objects;

import static java.lang.Math.round;

@Path("/v1/reads/{idRead}/challenges")
public class ChallengeSolutionResource {

    @Inject
    private BookReadRepository bookReadRepository;

    @Inject
    private CharacterRepository characterRepository;

    @PATCH
    @Transactional
    public Response answerChallenge(@RestPath Long idRead, ChallengeRequest request) {
        BookReadEntity read = bookReadRepository.findById(idRead);

        if (read == null)
            throw new ResourceNotFoundException("Leitura não encontrada");

        Integer bookPages = read.getBook().getPages();
        if (!Objects.equals(read.getPagesRead(), bookPages))
            throw new ReadException("Você não completou a leitura ainda");

        read.setChallengeAnswered(true);
        CharacterEntity character = characterRepository.findByUserId(read.getUser().getId());
        character.setCoins(round(character.getCoins() + bookPages * 0.1f))
                .setXp(round(character.getXp() + bookPages * 0.2f))
                .setEnableAttributePoints(round(character.getEnableAttributePoints() + bookPages * 0.1f));

        if (character.getXpToLevelUp() < character.getXp()) {
            character.setLevel(character.getLevel() + 1);
        }

        characterRepository.persist(character);
        bookReadRepository.persist(read);

        return Response.ok().build();
    }
}
