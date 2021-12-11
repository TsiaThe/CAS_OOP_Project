package backend.cards;

/**
 * Generic class which models a card.
 * - Cards can be Doors/Treasures.
 * - Cards are further divided based
 *   on their functionality.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public abstract class Card {

    private String name;
    private String description;

    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
