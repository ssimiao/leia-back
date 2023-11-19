package br.com.character.race;

public enum Race {
    DINO_GREEN(1L),
    DINO_BLUE(2L),
    DUCK_GREEN(3L),
    DUCK_WHITE(4L),
    OWL_BROWN(5L);

    private final Long id;

    Race(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
