package br.com.character.classe;

public enum Classe {
    NOVATO("01tHs000009NaXTIA0", 1L),
    PROFESSOR("", 2L);

    private final String id;

    private final Long idJpa;

    Classe(String id, Long idJpa) {
        this.id = id;
        this.idJpa = idJpa;
    }

    public String getId() {
        return id;
    }
}
