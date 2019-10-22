package de.bermuda.hero.backend;

public enum ComicUniversum {
    MARVEL("Marvel"),
    DC_COMICS ("DC Comics");

    private String displayName;

    ComicUniversum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
