package backend;

import backend.cards.*;
import backend.players.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameSetup {

    private final int NUMBER_OF_CLASSES=4;
    private final int NUMBER_OF_RACES=4;

    private HashMap<Integer, Class<?>> gameClasses;
    private HashMap<Integer, Class<?>> gameRaces;
    private Random randomInteger = new Random();

    public GameSetup(){
        gameClasses = generateClasses(NUMBER_OF_CLASSES);
        gameRaces = generateRaces(NUMBER_OF_RACES);
    }

    private HashMap<Integer, Class<?>> generateClasses(int numberOfClasses){
        HashMap<Integer, Class<?>> myGameClasses = new HashMap<>(numberOfClasses);
        myGameClasses.put(0, Human.class);
        myGameClasses.put(1, Elf.class);
        myGameClasses.put(2, Halbling.class);
        myGameClasses.put(3, Dwarf.class);

        return myGameClasses;
    }

    private HashMap<Integer, Class<?>> generateRaces(int numberOfRaces){
        HashMap<Integer, Class<?>> myGameRaces = new HashMap<>(numberOfRaces);
        myGameRaces.put(0, Priest.class);
        myGameRaces.put(1, Thief.class);
        myGameRaces.put(2, Warrior.class);
        myGameRaces.put(3, Wizard.class);

        return myGameRaces;
    }

    public Player generatePlayer(String PlayerName) {
        try {
            Class<?> c = gameClasses.get(randomInteger.nextInt(NUMBER_OF_CLASSES));
            Constructor<?> cons = c.getConstructor();
            PlayerClass newPlayerClass = (PlayerClass) cons.newInstance();

            c = gameRaces.get(randomInteger.nextInt(NUMBER_OF_RACES));
            cons = c.getConstructor();
            PlayerRace newPlayerRace = (PlayerRace) cons.newInstance();

            Player newPlayer = new Player(PlayerName, newPlayerClass, newPlayerRace);

            return newPlayer;
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            System.out.println("Error by class player creation");
            System.out.println(e);
            return null;
        }
    }

    public List<Card> generateBootsCards(List<Card> gameCards){
        gameCards.add(new Boots("Arschtritt Stiefel", "", 400, 2));
        gameCards.add(new Armour("Stiefel zum schnellen davonlaufen", "", 200, 1));
        return gameCards;
    }

    public List<Card> generateArmourCards(List<Card> gameCards){
        gameCards.add(new Armour("Flammende Ruestung", "", 400, 2));
        gameCards.add(new Armour("Lederruestung", "", 200, 1));
        gameCards.add(new Armour("Schleimige Ruestung", "", 200, 1));
        gameCards.add(new Armour("Mithril Ruestung", "", 600, 3));
        gameCards.add(new Armour("Kurze breite Ruestung", "", 400, 3));
        return gameCards;
    }

    public List<Card> generateHeadgearCards(List<Card> gameCards){
        gameCards.add(new Boots("Helf der Tapferkeit", "", 200, 1));
        gameCards.add(new Armour("Geiler Helm", "", 600, 3));
        gameCards.add(new Armour("Cooles Tuch fuer harte Kerle", "", 400, 2));
        return gameCards;
    }

    public List<Card> generateItemCards(List<Card> gameCards){

        gameCards.add(new Item("Ganzkoerper Schild.","Nur von Kriegern nutzbar.",600,4,
                1,Warrior.class,false, p -> {
                   if (p.getPlayerRace() instanceof Warrior){
                       p.setFightStrength(p.getFightStrength()+4);
                   }
                }));

        gameCards.add(new Item("Singendes und tanzendes Schwert.","Nur von Dieben nutzbar.",400,2 ,
                1,null,true, p -> {
            if (p.getPlayerRace() instanceof Thief){
                p.setFightStrength(p.getFightStrength()+2);
            }
        }));

        gameCards.add(new Item("Stange 11-Fuss.","",200,1 ,
                2,null,true, p -> {
                p.setFightStrength(p.getFightStrength()+2);}));

        gameCards.add(new Item("Hinterhaeltiges bastard-Schwert.","",400,2 ,
                1,null,true, p -> {
                p.setFightStrength(p.getFightStrength()+2);}));

        gameCards.add(new Item("Kettensäge der blutigen Zerstuckelung.","",600,3 ,
                2,null,false, p -> {
                p.setFightStrength(p.getFightStrength()+2);}));

        gameCards.add(new Item("Riesige Fells.","",0,3 ,
                2,null,false, p -> {
                p.setFightStrength(p.getFightStrength()+3);}));

        gameCards.add(new Item("Flotter Buckler.","",400,2 ,
                1,null,true, p -> {
                p.setFightStrength(p.getFightStrength()+2);}));

        gameCards.add(new Item("Spiessige Knie.","",200,1 ,
                0,null,true, p -> {
                p.setFightStrength(p.getFightStrength()+1);}));

        gameCards.add(new Item("Gentleman Keule.","",400,3 ,
                1,null,true, p -> {
                p.setFightStrength(p.getFightStrength()+3);}));

        gameCards.add(new Item("Bogen mit bunten Baendern.","Nur von Elfen nutzbar.",800,4 ,
                2,Elf.class,true, p -> {
            if (p.getPlayerClass() instanceof Elf){
                p.setFightStrength(p.getFightStrength()+4);
            }}));

        gameCards.add(new Item("Unfaires Rapier.","Nur von Elfen nutzbar.",600,3 ,
                1,Elf.class,true, p -> {
            if (p.getPlayerClass() instanceof Elf){
                p.setFightStrength(p.getFightStrength()+3);
            }}));

        gameCards.add(new Item("Trittleiter.","Nur von Halblingen nutzbar.",400,3 ,
                0,Halbling.class,false, p -> {
            if (p.getPlayerClass() instanceof Halbling){
                p.setFightStrength(p.getFightStrength()+3);
            }}));

        gameCards.add(new Item("Braut-Breitschwert.","",400,3 ,
                1,null,true, p -> {
                p.setFightStrength(p.getFightStrength()+3);}));

        gameCards.add(new Item("Strumpfhose der Riesenstaerke.","Nur von Kriegern nutzbar.",600,3 ,
                0,Warrior.class,true, p -> {
            if (p.getPlayerRace() instanceof Warrior){
                p.setFightStrength(p.getFightStrength()+3);
            }}));

        gameCards.add(new Item("Scharfer Streitkolben.","Nur von Priestern nutzbar.",600,4 ,
                1,Priest.class,true, p -> {
            if (p.getPlayerRace() instanceof Priest){
                p.setFightStrength(p.getFightStrength()+4);
            }}));

        gameCards.add(new Item("Kaesereibe des Friedens.","Nur von Priestern nutzbar.",400,3 ,
                1,Priest.class,true, p -> {
            if (p.getPlayerRace() instanceof Priest){
                p.setFightStrength(p.getFightStrength()+3);
            }}));

        gameCards.add(new Item("Limburger und Sardellen Sandwich.","Nur von Halblingen nutzbar.",400,3 ,
                0,null,true, p -> {
            if (p.getPlayerClass() instanceof Halbling){
                p.setFightStrength(p.getFightStrength()+3);
            }}));

        gameCards.add(new Item("Kniescheiben zertruemmernder Hammer.","Nur von Zwergen nutzbar.",600,4 ,
                1,Dwarf.class,true, p -> {
            if (p.getPlayerClass() instanceof Dwarf){
                p.setFightStrength(p.getFightStrength()+4);
            }}));

        gameCards.add(new Item("Verkleidungsumhang.","Nur von Dieben nutzbar.",600,4,
                0,Thief.class,true, p -> {
            if (p.getPlayerRace() instanceof Thief){
                p.setFightStrength(p.getFightStrength()+4);
            }}));

        gameCards.add(new Item("Napalmstab.","Nur von Zauberern nutzbar.",800,5 ,
                1,Wizard.class,true, p -> {
            if (p.getPlayerRace() instanceof Wizard){
                p.setFightStrength(p.getFightStrength()+5);
            }}));

        gameCards.add(new Item("Schweizer Armeehellbarde.","Nur von Menschen nutzbar.",600,4 ,
                2,Human.class,false, p -> {
            if (p.getPlayerClass() instanceof Human){
                p.setFightStrength(p.getFightStrength()+4);
            }}));

        gameCards.add(new Item("Dolch des Verrats.","Nur von Dieben nutzbar.",400,3 ,
                1,Thief.class,true, p -> {
            if (p.getPlayerRace() instanceof Thief){
                p.setFightStrength(p.getFightStrength()+3);
            }}));
        return gameCards;
    }

    public List<Card> generateMonsterBoosterCards(List<Card> gameCards){
        gameCards.add(new MonsterBooster("Gigantisch", "+10 for a monster",
                10, 2));
        gameCards.add(new MonsterBooster("Baby", "-5 for a monster",
                -5, -1));
        gameCards.add(new MonsterBooster("Uralt", "+10 for a monster",
                10, 2));
        gameCards.add(new MonsterBooster("Wuetend", "+5 for a monster",
                5, 1));
        gameCards.add(new MonsterBooster("Intelligent", "+5 for a monster",
                5, 1));
        return gameCards;
    }

    public List<Card> generateFightSpellCards(List<Card> gameCards){
        gameCards.add(new FightSpell("Ekliger Sportdrink", "+2 for one side",2));
        gameCards.add(new FightSpell("Gefrierender Explosivtrank", "+3 for one side",3));
        gameCards.add(new FightSpell("Trank des Mundgeruchs", "+2 for one side",2));
        gameCards.add(new FightSpell("Elektrisch radioaktiver Saeuretrank", "+5 for one side",5));
        gameCards.add(new FightSpell("Huebsche Luftballons", "+5 for one side",5));
        gameCards.add(new FightSpell("Flammender Gifttrank", "+3 for one side",3));
        gameCards.add(new FightSpell("Magisches Geschoss", "+5 for one side",5));
        gameCards.add(new FightSpell("Trank der Terwirrung", "+3 for one side",3));
        gameCards.add(new FightSpell("Trank des idiotischen Heldenmuts", "+2 for one side",2));
        gameCards.add(new FightSpell("Schlaftrank", "+2 for one side",2));
        return gameCards;
    }

    public List<Card> generateLevelSpellCards(List<Card> gameCards){
        gameCards.add(new LevelSpell("Ameisenhuegel aufkochen","Level up!"));
        gameCards.add(new LevelSpell("Gelegen kommender Anddistionsfehler","Level up!"));
        gameCards.add(new LevelSpell("Bestich den Spielleiter mit Essen","Level up!"));
        gameCards.add(new LevelSpell("1.000 Goldstuecke","Level up!"));
        gameCards.add(new LevelSpell("Trank der beeindruckende Männlichkeit","Level up!"));
        gameCards.add(new LevelSpell("Auf obskure Regeln berufen","Level up!"));
        return gameCards;
    }

    public List<Card> generateMonsterCards(List<Card> gameCards){

        gameCards.add(new Monster("Grosses Wuettendes Huhn",
                "Schlimme Dinge: Schmerzhaftes Hacken. Verliere eine Stufe.", 2, 1,
                new MonsterEffects() {
                    public void monsterSpecialPower(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public void monsterWinsFight(Monster m, Player p) {
                        System.out.println("Du hast den Kampf verloren. Du verlierst eine Stufe!");
                        p.setLevel(p.getLevel()-1);
                    }
                }));

        gameCards.add(new Monster("Pavilion",
                "Niemand kann dir helfen. Schlimme Dinge: Verliere drei Stufen.", 2, 1,
                new MonsterEffects() {
                    public void monsterSpecialPower(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public void monsterWinsFight(Monster m, Player p) {
                        System.out.println("Du hast den Kampf verloren. Du verlierst drei Stufen!");
                        p.setLevel(p.getLevel()-3);
                    }
                }));

        gameCards.add(new Monster("Gesichtssauger",
                "+6 gegen Elfs. Schlimme Dinge: Dein Gesicht wird rutergesaugt und deine " +
                        "Kopfbedeckung geht verloren. Du verlierst auch eine Stufe dazu.", 8, 2,
                new MonsterEffects() {
                    public void monsterSpecialPower(Monster m, Player p) {
                        if (p.getPlayerClass() instanceof Elf){m.setLevelValue(m.getLevelValue()+6);}
                    }
                    public void monsterWinsFight(Monster m, Player p) {
                        System.out.println("Du hast den Kampf verloren. Du verlierst deine Kopfbedeckung und eine Stufe!");
                        if (p.getHeadgear()!=null){p.setHeadgear(null);}
                        p.setLevel(p.getLevel()-1);
                    }
                }));

        gameCards.add(new Monster("Pikotzu",
                "Du erhälst eine Extrastufe, wenn du es ohne Hilfe besiegst." +
                        "Schlimme Dinge: Kotzestrahl-Angriff. Lege deine ganze Hand ab!.", 6, 2,
                new MonsterEffects() {
                    public void monsterSpecialPower(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public void monsterWinsFight(Monster m, Player p) {
                        System.out.println("Du hast den Kampf verloren. Du verlierst alle Handkarten!");
                        p.setHeadgear(null);
                        p.setArmour(null);
                        p.setBoots(null);
                        p.getItems().clear();
                    }
                }));

        gameCards.add(new Monster("Entikore",
                "Wiedersteht Magie: +6 gegen Zauberer. Schlimme Dinge: " +
                        "Schlimme Dinge: Lege deine ganze Hand ab!. ", 6, 2,
                new MonsterEffects() {
                    public void monsterSpecialPower(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Wizard){m.setLevelValue(m.getLevelValue()+6);}
                    }
                    public void monsterWinsFight(Monster m, Player p) {
                        System.out.println("Du hast den Kampf verloren. Du verlierst alle Handkarten!");
                        p.setHeadgear(null);
                        p.setArmour(null);
                        p.setBoots(null);
                        p.getItems().clear();
                    }
                }));

        gameCards.add(new Monster("Untotes Pferd",
                "+6 gegen Zwerge. Schlimme Dinge:  Tritt, beisst und riecht fruchtbar. Verliere 2 Stufen", 4, 2,
                new MonsterEffects() {
                    public void monsterSpecialPower(Monster m, Player p) {
                        if (p.getPlayerClass() instanceof Dwarf){m.setLevelValue(m.getLevelValue()+6);}
                    }
                    public void monsterWinsFight(Monster m, Player p) {
                        System.out.println("Du hast den Kampf verloren. Du verlierst zwei Stufen!");
                        p.setLevel(p.getLevel()-2);
                    }
                }));















        return gameCards;
    }

    public List<Card> generateCurseCards(List<Card> gameCards){

        gameCards.add(new Curse("FLUCH! Ente des Schreckens.",
                "Du solltest es besser wissen, als Ente in einem Dungeon aufzuheben. Verliere zwei Stufen.",
                p -> p.setLevel(p.getLevel()-2)));

        gameCards.add(new Curse("FLUCH!",
                "Verliere eine Stufe.",
                p -> p.setLevel(p.getLevel()-1)));

        gameCards.add(new Curse("FLUCH!",
                "Verliere eine Stufe.",
                p -> p.setLevel(p.getLevel()-1)));

        gameCards.add(new Curse("FLUCH!",
                "Verliere deine Ruestung.",
                p -> p.setArmour(null)));

        gameCards.add(new Curse("FLUCH!",
                "Verliere alles Schuhwerk, das du traest.",
                p -> p.setBoots(null)));

        gameCards.add(new Curse("FLUCH!",
                "Verliere die Kopfbedeckung,die du traest.",
                p -> p.setHeadgear(null)));

        gameCards.add(new Curse("FLUCH!",
                "Verliere dein grosses Gegenstand (wenn du ein hast).",
                p -> {
                    for (Item it:p.getItems()){
                        if (!it.isSmallItem()){
                            p.getItems().remove(it);
                            break;
                        }
                    }
                }
        ));

        gameCards.add(new Curse("FLUCH!",
                "Verliere ein kleines Gegenstand (wenn du ein hast).",
                p -> {
                    for (Item it:p.getItems()){
                        if (it.isSmallItem()){
                            p.getItems().remove(it);
                            break;
                        }
                    }
                }
        ));

        gameCards.add(new Curse("FLUCH!",
                "Verliere ein kleines Gegenstand (wenn du ein hast).",
                p -> {
                    for (Item it:p.getItems()){
                        if (it.isSmallItem()){
                            p.getItems().remove(it);
                            break;
                        }
                    }
                }
        ));

        gameCards.add(new Curse("FLUCH!",
                "Wirklich beschiessener Fluch! Du verlierst dein kleines Gegenstand mit dem groestem Bonus.",
                p -> {
                    Item lostItem = new Item("Dummy","",0,0,0,null,true,null);
                    for (Item it:p.getItems()){
                        if (it.getBonus()>lostItem.getBonus()){
                            lostItem = it;
                        }
                    }
                    if (lostItem.getName()!="Dummy") {
                        p.getItems().remove(lostItem);
                    }
                }
        ));
         return gameCards;
    }





}
