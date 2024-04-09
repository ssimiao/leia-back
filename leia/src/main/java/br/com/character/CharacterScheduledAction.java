package br.com.character;

import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CharacterScheduledAction {

    @Inject
    private CharacterRepository characterRepository;

    @Scheduled(cron="0 0 0 * * ?")
    @Transactional
    void decreaseHealthFromAllCharacter(ScheduledExecution execution) {
        List<CharacterEntity> characters = characterRepository.findAll().stream().peek(characterEntity -> {
            Integer vitality = characterEntity.getVitality();
            if (vitality > 0)
                characterEntity.setVitality(vitality - 1);
        }).toList();
        characterRepository.persist(characters);
    }
}
