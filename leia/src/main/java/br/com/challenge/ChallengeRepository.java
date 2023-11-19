package br.com.challenge;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChallengeRepository implements PanacheRepositoryBase<ChallengeEntity, Long> {

}
