package br.com.character.classe;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClasseRepository implements PanacheRepositoryBase<ClasseEntity, String> {
}
