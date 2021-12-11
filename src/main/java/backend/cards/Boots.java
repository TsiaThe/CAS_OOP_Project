package backend.cards;

/**
 * Models a pair of boots.
 * Boots give a special power  and
 * some points.
 * @author Theofanis Tsiantas
 * @version  2021.12.04 - version 1
 */
public class Boots extends Equipment{

    public Boots(String name, String description, int value, int bonus) {
        super(name, description, value, bonus);
    }
}
