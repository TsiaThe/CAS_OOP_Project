package com.backend;

import com.backend.cards.*;
import com.backend.players.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class can create a full-setup for a game:
 * 1. A player can be generated in a random combination of class and race.
 * 2. Different methods are available for generating different card types.
 * @author Theofanis Tsiantas
 * @version  2021.11.15 - version 1
 */
public class GameSetup {

    // The game supports for the moment four
    // different classes and races.
    private final int NUMBER_OF_CLASSES=4;
    private final int NUMBER_OF_RACES=4;

    // A HashMap which associates a class type
    // (Dwarf, Elf, Halbling, Human) with an integer.
    private HashMap<Integer, Class<?>> gameClasses;
    // A HashMap which associates a race type
    // (Priest, Thief, Warrior, Wizzard) with an integer.
    private HashMap<Integer, Class<?>> gameRaces;
    // Random generator.
    private Random randomInteger = new Random();

    public GameSetup(){
        gameClasses = generateClasses(NUMBER_OF_CLASSES);
        gameRaces = generateRaces(NUMBER_OF_RACES);
    }

    // Method which populates the HashMap with the game classes.
    private HashMap<Integer, Class<?>> generateClasses(int numberOfClasses){
        HashMap<Integer, Class<?>> myGameClasses = new HashMap<>(numberOfClasses);
        myGameClasses.put(0, Human.class);
        myGameClasses.put(1, Elf.class);
        myGameClasses.put(2, Halbling.class);
        myGameClasses.put(3, Dwarf.class);

        return myGameClasses;
    }

    // Method which populates the HashMap with the game races.
    private HashMap<Integer, Class<?>> generateRaces(int numberOfRaces){
        HashMap<Integer, Class<?>> myGameRaces = new HashMap<>(numberOfRaces);
        myGameRaces.put(0, Priest.class);
        myGameRaces.put(1, Thief.class);
        myGameRaces.put(2, Warrior.class);
        myGameRaces.put(3, Wizard.class);

        return myGameRaces;
    }

    // Method which generates a player with a random class and race.
    public Player generatePlayer() {
        try {
            Class<?> c = gameClasses.get(randomInteger.nextInt(NUMBER_OF_CLASSES));
            Constructor<?> cons = c.getConstructor();
            PlayerClass newPlayerClass = (PlayerClass) cons.newInstance();

            c = gameRaces.get(randomInteger.nextInt(NUMBER_OF_RACES));
            cons = c.getConstructor();
            PlayerRace newPlayerRace = (PlayerRace) cons.newInstance();

            Player newPlayer = new Player(newPlayerClass, newPlayerRace);

            // If class="Human" the fight strength has by default +1 (class property).
            if(newPlayer.getPlayerClass() instanceof Human) newPlayer.setFightStrength(2);

            return newPlayer;
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            System.out.println("Error by class player creation");
            System.out.println(e);
            return null;
        }
    }

    // Method which generates a list of cards for "Boots" object.
    public List<Card> generateBootsCards(List<Card> gameCards){
        gameCards.add(new Boots("Arschtritt Stiefel", "", 400, 2));
        gameCards.add(new Boots("Stiefel zum schnellen davonlaufen", "", 200, 1));
        return gameCards;
    }

    // Method which generates a list of cards for "Armour" object.
    public List<Card> generateArmourCards(List<Card> gameCards){
        gameCards.add(new Armour("Flammende Ruestung", "", 400, 2));
        gameCards.add(new Armour("Lederruestung", "", 200, 1));
        gameCards.add(new Armour("Schleimige Ruestung", "", 200, 1));
        gameCards.add(new Armour("Mithril Ruestung", "", 600, 3));
        gameCards.add(new Armour("Kurze breite Ruestung", "", 400, 3));
        return gameCards;
    }

    // Method which generates a list of cards for "Headgear" object.
    public List<Card> generateHeadgearCards(List<Card> gameCards){
        gameCards.add(new Headgear("Helf der Tapferkeit", "", 200, 1));
        gameCards.add(new Headgear("Geiler Helm", "", 600, 3));
        gameCards.add(new Headgear("Cooles Tuch fuer harte Kerle", "", 400, 2));
        return gameCards;
    }

    // Method which generates a list of cards for different "Item" objects.
    public List<Card> generateItemCards(List<Card> gameCards){

        gameCards.add(new Item("Ganzkoerper Schild.","Nur von Kriegern nutzbar.",600,4,
                Warrior.class,false, p -> {
                   if (p.getPlayerRace() instanceof Warrior){
                       p.setFightStrength(p.getFightStrength()+4);
                   }
                }));

        gameCards.add(new Item("Singendes und tanzendes Schwert.","Nur von Dieben nutzbar.",400,2 ,
                null,true, p -> {
            if (p.getPlayerRace() instanceof Thief){
                p.setFightStrength(p.getFightStrength()+2);
            }
        }));

        gameCards.add(new Item("Stange 11-Fuss.","",200,1 ,
                null,true, p -> {
                p.setFightStrength(p.getFightStrength()+2);}));

        gameCards.add(new Item("Hinterhaeltiges bastard-Schwert.","",400,2 ,
                null,true, p -> {
                p.setFightStrength(p.getFightStrength()+2);}));

        gameCards.add(new Item("Kettensäge der blutigen Zerstuckelung.","",600,3 ,
                null,false, p -> {
                p.setFightStrength(p.getFightStrength()+2);}));

        gameCards.add(new Item("Riesige Fells.","",0,3 ,
                null,false, p -> {
                p.setFightStrength(p.getFightStrength()+3);}));

        gameCards.add(new Item("Flotter Buckler.","",400,2 ,
                null,true, p -> {
                p.setFightStrength(p.getFightStrength()+2);}));

        gameCards.add(new Item("Spiessige Knie.","",200,1 ,
                null,true, p -> {
                p.setFightStrength(p.getFightStrength()+1);}));

        gameCards.add(new Item("Gentleman Keule.","",400,3 ,
                null,true, p -> {
                p.setFightStrength(p.getFightStrength()+3);}));

        gameCards.add(new Item("Bogen mit bunten Baendern.","Nur von Elfen nutzbar.",800,4 ,
                Elf.class,true, p -> {
            if (p.getPlayerClass() instanceof Elf){
                p.setFightStrength(p.getFightStrength()+4);
            }}));

        gameCards.add(new Item("Unfaires Rapier.","Nur von Elfen nutzbar.",600,3 ,
                Elf.class,true, p -> {
            if (p.getPlayerClass() instanceof Elf){
                p.setFightStrength(p.getFightStrength()+3);
            }}));

        gameCards.add(new Item("Trittleiter.","Nur von Halblingen nutzbar.",400,3 ,
                Halbling.class,false, p -> {
            if (p.getPlayerClass() instanceof Halbling){
                p.setFightStrength(p.getFightStrength()+3);
            }}));

        gameCards.add(new Item("Braut-Breitschwert.","",400,3 ,
                null,true, p -> {
                p.setFightStrength(p.getFightStrength()+3);}));

        gameCards.add(new Item("Strumpfhose der Riesenstaerke.","Nur von Kriegern nutzbar.",600,3 ,
                Warrior.class,true, p -> {
            if (p.getPlayerRace() instanceof Warrior){
                p.setFightStrength(p.getFightStrength()+3);
            }}));

        gameCards.add(new Item("Scharfer Streitkolben.","Nur von Priestern nutzbar.",600,4 ,
                Priest.class,true, p -> {
            if (p.getPlayerRace() instanceof Priest){
                p.setFightStrength(p.getFightStrength()+4);
            }}));

        gameCards.add(new Item("Kaesereibe des Friedens.","Nur von Priestern nutzbar.",400,3 ,
                Priest.class,true, p -> {
            if (p.getPlayerRace() instanceof Priest){
                p.setFightStrength(p.getFightStrength()+3);
            }}));

        gameCards.add(new Item("Limburger und Sardellen Sandwich.","Nur von Halblingen nutzbar.",400,3 ,
                null,true, p -> {
            if (p.getPlayerClass() instanceof Halbling){
                p.setFightStrength(p.getFightStrength()+3);
            }}));

        gameCards.add(new Item("Kniescheiben zertruemmernder Hammer.","Nur von Zwergen nutzbar.",600,4 ,
                Dwarf.class,true, p -> {
            if (p.getPlayerClass() instanceof Dwarf){
                p.setFightStrength(p.getFightStrength()+4);
            }}));

        gameCards.add(new Item("Verkleidungsumhang.","Nur von Dieben nutzbar.",600,4,
                Thief.class,true, p -> {
            if (p.getPlayerRace() instanceof Thief){
                p.setFightStrength(p.getFightStrength()+4);
            }}));

        gameCards.add(new Item("Napalmstab.","Nur von Zauberern nutzbar.",800,5 ,
                Wizard.class,true, p -> {
            if (p.getPlayerRace() instanceof Wizard){
                p.setFightStrength(p.getFightStrength()+5);
            }}));

        gameCards.add(new Item("Schweizer Armeehellbarde.","Nur von Menschen nutzbar.",600,4 ,
                Human.class,false, p -> {
            if (p.getPlayerClass() instanceof Human){
                p.setFightStrength(p.getFightStrength()+4);
            }}));

        gameCards.add(new Item("Dolch des Verrats.","Nur von Dieben nutzbar.",400,3 ,
                Thief.class,true, p -> {
            if (p.getPlayerRace() instanceof Thief){
                p.setFightStrength(p.getFightStrength()+3);
            }}));
        return gameCards;
    }

    // Method which generates a list of monster booster cards.
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

    // Method which generates a list of fight spell cards.
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

    // Method which generates a list of level up cards.
    public List<Card> generateLevelSpellCards(List<Card> gameCards){
        gameCards.add(new LevelSpell("Ameisenhuegel aufkochen","Level up!"));
        gameCards.add(new LevelSpell("Gelegen kommender Anddistionsfehler","Level up!"));
        gameCards.add(new LevelSpell("Bestich den Spielleiter mit Essen","Level up!"));
        gameCards.add(new LevelSpell("1.000 Goldstuecke","Level up!"));
        gameCards.add(new LevelSpell("Trank der beeindruckende Männlichkeit","Level up!"));
        gameCards.add(new LevelSpell("Auf obskure Regeln berufen","Level up!"));
        return gameCards;
    }

    // Method which generates a list of monster cards.
    public List<Card> generateMonsterCards(List<Card> gameCards){

        String standardMessage = "Du hast den Kampf verloren und konntest nicht weglaufen. ";

        gameCards.add(new Monster("Grosses Wuettendes Huhn",
                "Schlimme Dinge: Schmerzhaftes Hacken. Verliere eine Stufe.", 2, 1,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-1);
                        return standardMessage+"Du verlierst eine Stufe!";
                    }
                }));

        gameCards.add(new Monster("Pavilion",
                "Schlimme Dinge: Verliere eine Stufe.", 2, 1,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-1);
                        return standardMessage+"Du verlierst eine Stufe!";
                    }
                }));

        gameCards.add(new Monster("Gesichtssauger",
                "+1 gegen Diebe. Schlimme Dinge: Dein Gesicht wird rutergesaugt und deine " +
                        "Kopfbedeckung geht verloren. Du verlierst auch zwei Stufen dazu.", 5, 2,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Thief){m.setLevelValue(m.getLevelValue()+1);}
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        if (p.getHeadgear()!=null){p.setHeadgear(null);}
                        p.setLevel(p.getLevel()-2);
                        return standardMessage+"Du verlierst deine Kopfbedeckung und zwei Stufen!";
                    }
                }));

        gameCards.add(new Monster("Pikotzu",
                "Schlimme Dinge: Kotzestrahl-Angriff. Lege deine ganze Hand ab!.", 6, 3,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setHeadgear(null);
                        p.setArmour(null);
                        p.setBoots(null);
                        p.getItems().clear();
                        return standardMessage+"Du verlierst alle Handkarten!";
                    }
                }));

        gameCards.add(new Monster("Entikore",
                "+1 gegen Krieger. Schlimme Dinge: " +
                        "Schlimme Dinge: Lege deine ganze Hand ab!. ", 6, 3,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Warrior){m.setLevelValue(m.getLevelValue()+1);}
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setHeadgear(null);
                        p.setArmour(null);
                        p.setBoots(null);
                        p.getItems().clear();
                        return standardMessage+"Du verlierst alle Handkarten!";
                    }
                }));

        gameCards.add(new Monster("Untotes Pferd",
                "+1 gegen Priester. Schlimme Dinge:  Tritt, beisst und riecht fruchtbar. Verliere 2 Stufen", 5, 2,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Priest){m.setLevelValue(m.getLevelValue()+1);}
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-2);
                        return standardMessage+"Du verlierst zwei Stufen!";
                    }
                }));

        gameCards.add(new Monster("Sabbernder Schleim",
                "+1 gegen Zauberer. Schlimme Dinge:  Lege das Schuhwerk, das du trägst, ab. Verliere 1 Stufe " +
                        ", wenn du keins hast!", 5, 2,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Wizard){
                            m.setLevelValue(m.getLevelValue()+1);
                        }
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        if (p.getBoots()!=null){
                            p.setBoots(null);
                            return standardMessage+"Du verlierst dein Schuhwerk!";
                        }
                        else
                        {
                            p.setLevel(p.getLevel()-1);
                            return standardMessage+"Du verlierst eine Stufe (kein Schuhwerk)!";
                        }
                    }
                }));

        gameCards.add(new Monster("Topfpflanze",
                "Schlimme Dinge:  Keine", 1, 1,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        return "Ok du hast verloren, aber es passiert nichts!";
                    }
                }));

        gameCards.add(new Monster("Plutoniumdrache",
                "+2 gegen Diebe. Schlimme Dinge: Du wirst zerstampft und gemampft. Drachen sind besonderes" +
                        "aggresiv gegen Zauberer. Wenn du einer bist, dann stirbst du. Wenn du eine andere " +
                        "Rasse hast, dann verlierst du nur eine Stufe.", 7, 3,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Thief){
                            m.setLevelValue(m.getLevelValue()+2);
                        }
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        if(p.getPlayerRace() instanceof Wizard){
                            spielerTod(p);
                            return standardMessage+"Du bist leider ein Zauberer, deshalb du stirbst!";
                        }
                        else{
                            p.setLevel(p.getLevel()-1);
                        }
                        return standardMessage+"Du bist zum Gluck kein Zauberer. " +
                                "Du verlierst nur eine Stufe!";
                    }
                }));

        gameCards.add(new Monster("Bekiffter Golem",
                "+2 gegen Krieger. Schlimme Dinge: Unaussprechlicher Tod für alle Rassen ausser Zauberer. " +
                        "Ein Zauberer verliert einfach eine Stufe.", 7, 3,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Warrior){
                            m.setLevelValue(m.getLevelValue()+2);
                        }
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        if(p.getPlayerRace() instanceof Wizard){
                            p.setLevel(p.getLevel()-1);
                        }
                        else{
                            spielerTod(p);
                            return standardMessage+"Weil du kein Zauberer bist, stirbst du!";
                        }

                        return standardMessage+"Du bist zum Gluck ein Zauberer. " +
                                "Du verlierst nur eine Stufe!";
                    }
                }));

        gameCards.add(new Monster("Bullrog",
                "Schlimme Dinge: Du bist zu Tode gepeitscht", 11, 4,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        spielerTod(p);
                        return standardMessage+"Du bist gestorben!";
                    }
                }));

        gameCards.add(new Monster("Gruftige Gebrüder",
                "Schlimme Dinge:  Du wirst auf Stuffe eins reduziert!", 1, 1,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(1);
                        if (p.getPlayerClass() instanceof Human){
                            p.setFightStrength(2);
                        }
                        else{
                            p.setFightStrength(1);
                        }
                        return standardMessage+"Du bist auf Stufe eins reduziert!";
                    }
                }));

        gameCards.add(new Monster("Lahmer Gobling",
                "Schlimme Dinge:  Er verhaut dich mit seiner Krücke. Verliere eine Stufe!", 1, 1,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-1);
                        return standardMessage+"Du verlierst eine Stufe!";
                    }
                }));

        gameCards.add(new Monster("Filzläuse",
                "Schlimme Dinge: Du verlierst eine Stufe!", 1, 1,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-1);
                        return standardMessage+"Du verlierst eine Stufe!";
                    }
                }));

        gameCards.add(new Monster("Kreischender Depp",
                "+3 gegen Priester. Schlimme Dinge: Lege deine Rüstung und Schuwerk ab! Du verlierst auch eine Stufe dazu!", 3, 2,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Priest){
                            m.setLevelValue(m.getLevelValue()+3);
                        }
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-1);
                        p.setArmour(null);
                        p.setBoots(null);
                        return standardMessage+"Du verlierst deine Rüstung, Schuwerk und eine Stufe!";
                    }
                }));

        gameCards.add(new Monster("Hammer-Ratte",
                "Schlimme: Sie verhaut dich- Verliere eine Stufe!", 1, 1,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-1);
                        return standardMessage+"Du verlierst eine Stufe!";
                    }
                }));

        gameCards.add(new Monster("Fliegende Frösche",
                "Schlimme: Sie beissen. Verliere zwei Stufen!", 2, 1,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-2);
                        return standardMessage+"Du verlierst zwei Stufen!";
                    }
                }));

        gameCards.add(new Monster("Leprachaun",
                "Schlimme Dinge: Er nimmt zwei von deinen Gegenständen!", 4, 2,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        for (int i=0;i<2;i++){
                            if (p.getItems().size()>0){
                                p.getItems().remove(0);
                            }
                        }
                        return standardMessage+"Du verlierst zwei Gegenstände!";
                    }
                }));

        gameCards.add(new Monster("Harfien",
                "Sie wiederstehen Magie +3 gegen Zauberer. Schlimme Dinge: " +
                        "Ihre Musik  ist wirklich, wirklich schlecht. Verliere zwei Stufen!", 3, 2,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Wizard){
                            m.setLevelValue(m.getLevelValue()+3);
                        }
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-2);
                        return standardMessage+"Du verlierst zwei Stufen!";
                    }
                }));

        gameCards.add(new Monster("Zungedämon",
                "+2 gegen Diebe. Schlimme Dinge: " +
                        "Ein wirklich ekliger Kuss kostet dich 2 Stufen (3 für Elfen)!", 8, 3,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Thief){
                            m.setLevelValue(m.getLevelValue()+2);
                        }
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-2);
                        if (p.getPlayerClass() instanceof Elf)
                        {
                            p.setLevel(p.getLevel()-1);
                            return "Du hast den Kampf verloren und du bist ein Elf!" +
                                    "Du verlierst drei Stufen!";
                        }
                        return standardMessage+"Du verlierst zwei Stufen!";
                    }
                }));

        gameCards.add(new Monster("Gallert-Oktaeder",
                "Schlimme Dinge: Lasse alle deine grosse Gegenstaende fallen.", 2, 1,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        for (Item it:p.getItems()){
                            if (it.isSmallItem()) it=null;
                        }
                        return standardMessage+"Du verlierst alle deine grosse Gegenstaende!";
                    }
                }));

        gameCards.add(new Monster("Mr Bones",
                "Schlimme Dinge: Seine knochige Berührung kostet dich 2 Stufen.", 2, 1,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                       p.setLevel(p.getLevel()-2);
                        return standardMessage+"Du verlierst zwei Stufen!";
                    }
                }));

        gameCards.add(new Monster("BigFoot",
                "Schlimme Dinge: Quetscht dich flach und frisst deinen Hut." +
                        " Du verlierst deine Kopfbedeckung.", 4, 3,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setHeadgear(null);
                        return standardMessage+"Du verlierst deine Kopfbedeckung!";
                    }
                }));

        gameCards.add(new Monster("3.872 Orks",
                "Schlimme Dinge: Du verlierst von 1-4 Stufen! Es wird " +
                        "zufällig entschieden, wie viele...", 4, 3,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        // No special power on this card.
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        Random rand = new Random();
                        int lostLevels = rand.nextInt(4)+1;
                        p.setLevel(p.getLevel()-lostLevels);
                        String stufen = "Stufen";
                        if (lostLevels == 1) stufen = "Stufe";
                        return standardMessage+"Du verlierst "+lostLevels+" "+stufen+" !";
                    }
                }));

        gameCards.add(new Monster("Unglaublicher unaussprechlicher Schrecken",
                "+2 gegen Krieger. Schlimme Dinge: Du verlierst zwei Stufen. " +
                        "Ein Zwerg verliert noch eine dazu.", 8, 3,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Warrior){
                            m.setLevelValue(m.getLevelValue()+2);
                        }
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-2);
                        if(p.getPlayerClass() instanceof Dwarf){
                            p.setLevel(p.getLevel()-1);
                            return standardMessage+"Du bust ein Zwerg, also verlierst du drei Stufgen!";
                        }
                        return standardMessage+"Du bist zum Gluck kein Zwerg. Du verlierst nur zwei Stufen";
                    }
                }));

        gameCards.add(new Monster("Hippogreif",
                "+2 gegen Priester. Schlimme Dinge: Du verlierst zwei Stufen. " +
                        "Ein Halbling verliert noch eine dazu.", 8, 3,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Priest){
                            m.setLevelValue(m.getLevelValue()+2);
                        }
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-2);
                        if(p.getPlayerClass() instanceof Halbling){
                            p.setLevel(p.getLevel()-1);
                            return standardMessage+"Du bust ein Halbling, also verlierst du drei Stufgen!";
                        }
                        return standardMessage+"Du bist zum Gluck kein Halbling. Du verlierst nur zwei Stufen";
                    }
                }));

        gameCards.add(new Monster("Netz-troll",
                "+2 gegen Zauberer. Schlimme Dinge: Du verlierst zwei Stufen. " +
                        "Ein Mensch verliert noch eine dazu.", 8, 3,
                new MonsterEffects() {
                    public void monsterExtraStrength(Monster m, Player p) {
                        if (p.getPlayerRace() instanceof Wizard) {
                            m.setLevelValue(m.getLevelValue() + 2);
                        }
                    }
                    public String monsterWinsFight(Monster m, Player p) {
                        p.setLevel(p.getLevel()-2);
                        if(p.getPlayerClass() instanceof Human){
                            p.setLevel(p.getLevel()-1);
                            return standardMessage+"Du bust ein Mensch, also verlierst du drei Stufgen!";
                        }
                        return standardMessage+"Du bist zum Gluck kein Mensch. Du verlierst nur zwei Stufen";
                    }
                }));















        return gameCards;
    }

    // Method which generates a list of curse cards.
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
                    Item lostItem = new Item("Dummy","",0,0,null,true,null);
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

    // Method which resets the attributes of a player after it dies.
    public void spielerTod(Player p){
        p.setLevel(1);
        p.setBoots(null);
        p.setArmour(null);
        p.setHeadgear(null);
        List<Item> emptyList = new ArrayList<>();
        p.setItems(emptyList);
        if (p.getPlayerClass() instanceof Human){
            p.setFightStrength(2);
        }
        else{
            p.setFightStrength(1);
        }
    }
}
