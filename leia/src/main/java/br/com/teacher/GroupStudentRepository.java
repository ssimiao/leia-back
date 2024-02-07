package br.com.teacher;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GroupStudentRepository implements PanacheRepositoryBase<GroupStudentEntity, Long> {
}
