public enum PersonalityType {
    MYSTIC_WANDERER("The Mystic Wanderer", "You walk the paths less traveled, seeking wisdom in shadows."),
    COSMIC_DREAMER("The Cosmic Dreamer", "Your mind dances among stars, painting reality with imagination."),
    SHADOW_SAGE("The Shadow Sage", "You embrace the darkness, finding strength in mystery."),
    RADIANT_WARRIOR("The Radiant Warrior", "Bold and fierce, you light up the world with your courage."),
    ETHEREAL_GUIDE("The Ethereal Guide", "You lead others with grace, a beacon of hope and understanding."),
    LUMINARY_SOUL("The Luminary Soul", "Pure light emanates from you, touching all who cross your path.");

    private final String displayName;
    private final String description;

    PersonalityType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}
