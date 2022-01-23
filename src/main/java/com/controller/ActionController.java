package com.controller;

import com.backend.cards.*;
import com.backend.players.*;
import com.dto.GameState;
import com.repository.MessageRepository;
import com.repository.UserRepository;
import com.web.Message;
import com.web.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

/**
 * This controller is responsible for all the actions hapenning in the game.
 * The game works with Autorefresh, calling the get method of the controller.
 * This is mapped under "/action/{currentUserId}" so that each user can have
 * his/her own game view. Apart from this it contains post methods for:
 * - Posting a message in the common chat (/action/{currentUserId})
 * - Closing the round of a game and starting a new one (/action/{currentUserId}/newRound)
 * - Simulating a run of the main player from a monster (/action/{currentUserId}/run)
 * - Simulating a game fight (/action/{currentUserId}/fight)
 * - Selling items for gaining levels (/action/{currentUserId}/sell)
 * - Participating/no-participating to a fight (/action/{currentUserId}/fightState)
 * - Marking a headgear to be sold (/action/{currentUserId}/sellHeadgear)
 * - Marking an armour to be sold (/action/{currentUserId}/sellArmour)
 * - Marking boots to be sold (/action/{currentUserId}/sellBoots)
 * - Marking a SPECIFIC item to be sold (/action/{currentUserId}/{itemName})
 * Lastly, it contains supporting methods to complete the above actions
 * @author Theofanis Tsiantas
 * @version  2021.01.05 - version 6
 */
@Controller
@RequestMapping // (path="/users")
public class ActionController {

    // Repo with all signed up users.
    private final UserRepository userRepository;
    // Repo with all communicated messages.
    private final MessageRepository messageRepository;
    // DTO which holds the actual game state.
    private final GameState gameState;
    // String  which passes the phase info to the ActionPage.
    private String dynamicInformation = "";
    // Boolean which controls if the action (fight/run/curse action)
    // of a current round has been performed.
    private boolean doorActionPerformed = false;

    @Autowired
    public ActionController(UserRepository userRepository,
                            MessageRepository messageRepository,
                            GameState gameState) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.gameState= gameState;
    }

    // Get method. User-dependent. Passes all necessary
    // Information to the ActionPage.html
    @GetMapping("/action/{currentUserId}")
    public String ActionPage(@PathVariable("currentUserId") long cuID,
                              Map<String, Object> model) {

        // Action bar/window information
        // GeneralInfo -> changes in every round, stays at the top of the window
        String gen="Runde: "+ gameState.getRound()+", Hauptspieler: " + getMainPlayerName();
        model.put("GeneralInfo", gen);
        // DynamicInfo -> changes in every round phase
        model.put("DynamicInfo", dynamicInformation);

        // User-specific information for URL
        model.put("currentUser", userRepository.findById(cuID));
        model.put("users", userRepository.findAll());

        // Show monster/curse icon and activate corresponding
        // buttons by door opening.
        if (gameState.getNewRound()){
            doorModel((DoorCard)gameState.doorOpen(),cuID, model);
            gameState.setNewRound(false);
            doorActionPerformed = false;
        }
        else{
            doorModel((DoorCard)gameState.getCurrentDoorCard(), cuID, model);
        }

        // If the door was a curse, it immediately acts to the main player.
        // Acts during the get of the main player, and only once/round!
        if ((gameState.getCurrentDoorCard() instanceof Curse))
            if ((cuID==gameState.getMainPlayer().getId()) && (doorActionPerformed==false)){
            ((Curse) gameState.getCurrentDoorCard()).curse(gameState.getMainPlayer());
            // Fighting strength must be recalculated after the curse action.
            gameState.getMainPlayer().calculateFightStrength();
            // Set to true for the remaining of the round.
            doorActionPerformed=true;
            dynamicInformation = "Die Fluch hat auf Spieler: "+getMainPlayerName()+" gewirkt.";
            model.put("DynamicInfo", dynamicInformation);
            }

        // Add player information bar
        playerModel(cuID, model);
        // Add boots information, if any...
        bootsModel(cuID, model);
        // Add armour information, if any...
        armourModel(cuID, model);
        // Add headgear information, if any...
        headgearModel(cuID, model);
        // Add items information, if any...
        itemsModel(cuID, model);

        // Communication handling (show messaging history)
        model.put("messages", messageRepository.findAll());

        // Check if game has started, otherwise go to WaitPage
        if (userRepository.count()==gameState.getNumberOfPlayers()) {
            // Check if game has ended
            if (gameState.getAllPlayers().stream().filter(p -> p.getLevel() >= gameState.getMaxLevel()).count() > 0) {
                List<Player> winners = new ArrayList<>();
                for (Player p : gameState.getAllPlayers()) {
                    if (p.getLevel() >= gameState.getMaxLevel()) {
                        winners.add(p);
                    }
                }
                String winnerNames = userRepository.findById(winners.get(0).getId()).get().getName();
                for (int i = 1; i < winners.size(); i++) {
                    winnerNames += ", ";
                    winnerNames += userRepository.findById(winners.get(i).getId()).get().getName();
                }
                model.put("winners", winnerNames);
                return "WinPage";
            }
            return "ActionPage";
        }
        return "WaitPage";
    }

    // Post method which posts a new message to the server.
    @PostMapping("/action/{currentUserId}")
    public String ChatMessage(@Valid Message message,
                              @PathVariable("currentUserId") long cuID,
                              Map<String, Object> model) {
        // Add new message to repository and redirect to ActionPage.
            String sender = "";
        if (userRepository.findById(cuID).isPresent()){
            Optional<User> pageUser = userRepository.findById(cuID);
            model.put("currentUser", pageUser);
            sender = pageUser.get().getName();
        }
        if(message.getMessageText() != null){
            message.setMessageText(sender+": "+message.getMessageText());
            messageRepository.save(message);
        }
        return "redirect:/action/"+ cuID;
    }

    // Post method which starts a new round.
    @PostMapping("/action/{currentUserId}/newRound")
    public String newRound(@PathVariable("currentUserId") long cuID){
        gameState.nextRound();
        return "redirect:/action/"+ cuID;
    }

    // Post method which simulates the attempt of the main player
    // to escape (run).
    @PostMapping("/action/{currentUserId}/run")
    public String run(@PathVariable("currentUserId") long cuID){
        dynamicInformation = "HS versucht wegzulaufen.";
        run();
        // Disables the fight button for the remaining of the round.
        doorActionPerformed = true;
        return "redirect:/action/"+ cuID;
    }

    // Post method which simulates a complete fight with all participating players.
    // It will work only once per round.
    @PostMapping("/action/{currentUserId}/fight")
    public String fight(@PathVariable("currentUserId") long cuID,
                            Map<String, Object> model){

        // Message that fight has begun
        dynamicInformation = " Kampf angefangen.";
        // Calculation of fighting players (w/t main player)
        dynamicInformation += " Kaempfende Spieler: "+getMainPlayerName();
        List<Player> fightingPlayers = new ArrayList<>();
        for (Player p:gameState.getSecondaryPlayers()){
            if(p.getFights()){
                String secPlayerName = userRepository.findById(p.getId()).get().getName();
                dynamicInformation += ", "  + secPlayerName;
                fightingPlayers.add(p);
            }
        }
        // totalPlayerStrength: Strength of all players which participate in fight
        int totalPlayerStrength = gameState.getMainPlayer().getFightStrength();
        Monster monster = (Monster) gameState.getCurrentDoorCard();
        // Check if the monster gets additional points because of the race of a player
        monster.monsterExtraStrength(gameState.getMainPlayer());
        for (Player p:gameState.getSecondaryPlayers()){
            if (p.getFights()){
                totalPlayerStrength += p.getFightStrength();
                monster.monsterExtraStrength(p);
            }
        }
        // Information on fight
        dynamicInformation += ". Gesamte Monsterkampfwert: " + monster.getLevelValue();
        dynamicInformation += ". Gesamte Spielerkampfwert: " + totalPlayerStrength;
        // Monster wins fight
        if (monster.getLevelValue()>=totalPlayerStrength) {
            dynamicInformation += ". Monster hat gewonnen. ";
            run();
        }
        // Players win fight
        else{
            // Won treasures
            int wonTreasures = monster.getTreasureValue();
            dynamicInformation += ". Monster wurde erschlagen. Gewonnene Schaetze: "+wonTreasures;
            // Each supporting (fighting player) gets a treasure, if one remains.
            for (Player p:fightingPlayers){
                if (wonTreasures>0){
                    Card wonTreasure = gameState.treasureOpen();
                    String secPlayerName = userRepository.findById(p.getId()).get().getName();
                    dynamicInformation += ". Player "+secPlayerName+" gewinnt: "+wonTreasure.getName()
                    +" ("+getTreasureType((TreasureCard) wonTreasure)+")";
                    p.applyTreasureCard((TreasureCard) wonTreasure);
                    wonTreasures--;
                }
            }
            // The main player gets all remaining treasures + a level.
            dynamicInformation += ". HS gewinnt: ";
            for (int i=1;i<=wonTreasures;i++){
                Card wonTreasure = gameState.treasureOpen();
                dynamicInformation += wonTreasure.getName()
                        +" ("+getTreasureType((TreasureCard) wonTreasure)+")"+", ";
                gameState.getMainPlayer().applyTreasureCard((TreasureCard) wonTreasure);
            }
            gameState.getMainPlayer().setLevel(gameState.getMainPlayer().getLevel()+1);
            gameState.getMainPlayer().calculateFightStrength();
            if (wonTreasures>0) dynamicInformation += "und ";
            dynamicInformation += "eine Stuffe. Aktuelle Stufe: "+gameState.getMainPlayer().getLevel();
        }
        // Disables the fight button for the remaining of the round.
        doorActionPerformed = true;
        return "redirect:/action/"+ cuID;
    }

    // Post method which sells all equipment that a user has selected to sell.
    @PostMapping("/action/{currentUserId}/sell")
    public String sellItems(@PathVariable("currentUserId") long cuID){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        currentPlayer.sell();
        currentPlayer.calculateFightStrength();
        dynamicInformation = "Spieler: "+getMainPlayerName()+" hat Teile seiner Ausruestung verkauft. ";
        dynamicInformation += "Aktuelle Stufe: "+gameState.getMainPlayer().getLevel();

        return "redirect:/action/"+ cuID;
    }

    // Post method which sets the fighting state of a player appropriately.
    @PostMapping("/action/{currentUserId}/fightState")
    public String fightHelp(@PathVariable("currentUserId") long cuID,
                            Map<String, Object> model){
        fightState(cuID);
        return "redirect:/action/"+ cuID;
    }

    // Post method which sets the sell attribute of Headgear.
    @PostMapping("/action/{currentUserId}/sellHeadgear")
    public String sellHeadgear(@PathVariable("currentUserId") long cuID){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        sellState(currentPlayer.getHeadgear());
        return "redirect:/action/"+ cuID;
    }

    // Post method which sets the sell attribute of an Armour.
    @PostMapping("/action/{currentUserId}/sellArmour")
    public String sellArmour(@PathVariable("currentUserId") long cuID){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        sellState(currentPlayer.getArmour());
        return "redirect:/action/"+ cuID;
    }

    // Post method which sets the sell attribute of Boots.
    @PostMapping("/action/{currentUserId}/sellBoots")
    public String sellBoots(@PathVariable("currentUserId") long cuID){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        sellState(currentPlayer.getBoots());
        return "redirect:/action/"+ cuID;
    }

    // Post method which sets the sell attribute of an item.
    @PostMapping("/action/{currentUserId}/{itemName}")
    public String sellItem(@PathVariable("currentUserId") long cuID,
                            @PathVariable("itemName") String itemName){

        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        for (Item i:currentPlayer.getItems()){
            if (i.getName().equals(itemName)){
                sellState(i);
                break;
            }
        }
        return "redirect:/action/"+ cuID;
    }


    // ----------------------------------------------------------------------------------------
    //      -------------------------- SUPPORTING METHODS -------------------------------
    // ----------------------------------------------------------------------------------------

    // Method which populates the current model view with the open door information
    // (and the corresponding available buttons).
    private Map<String, Object> doorModel(DoorCard dc, long cuID, Map<String, Object> currentModel){
        // Check if the door is monster or curse
        // Monster door
        if (dc instanceof Monster) {
            // Show monster icon
            currentModel.put("door","monster");
            // If the door just opened, show message in  the dynamic section.
            if (gameState.getNewRound()){
                dynamicInformation = "Monster hinten der Tuer!";
                currentModel.put("DynamicInfo", dynamicInformation);
            }
            // For the main player the gamePhase is returned. This shows 4-buttons!
            // If the action (fight/run) has not been performed, then only "fight"/"run" are active.
            if (cuID==gameState.getMainPlayer().getId() && !doorActionPerformed) {
                currentModel.put("gamePhase","fight");
            }
            // If the action (fight/run) has been performed, then only "sell"/"close round" are active.
            else if (cuID==gameState.getMainPlayer().getId() && doorActionPerformed) {
                currentModel.put("gamePhase","nofight");
            }
            // For all other players only the fight participation factor is shown.
            else{
                currentModel.put("fightButton",true);
            }
        }
        // Curse door
        else{
            currentModel.put("door","curse");
            // If the door just opened, add to the game history.
            if (gameState.getNewRound()){
                dynamicInformation = "Fluch hinten der Tuer!";
                currentModel.put("DynamicInfo", dynamicInformation);
            }
            // For the main player the gamePhase is returned. This shows 4-buttons!
            if (cuID==gameState.getMainPlayer().getId()) currentModel.put("gamePhase","nofight");
        }

        currentModel.put("doorName",dc.getName());
        if (dc instanceof Monster) currentModel.put("doorName",dc.getName()+" (lvl: "+
                ((Monster) dc).getLevelValue()+")");
        currentModel.put("doorDescription",dc.getDescription());

        return currentModel;
    }

    // Method which sets the "sell" attribute of an equipment to true/false based on
    // whether the player wants to sell it or not.
    private void sellState(Equipment equipment){
        equipment.setSell(!equipment.getSell());
    }

    // Method which populates the current model view with the boots information.
    private Map<String, Object> itemsModel(long cuID, Map<String, Object> currentModel){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        if (currentPlayer.getItems().size()>0){
            currentModel.put("items", currentPlayer.getItems());

        }
        List<String> NoItemList=new ArrayList<>();
        for (int i=0;i<(4-currentPlayer.getItems().size());i++){
            NoItemList.add("KeinGegenstand");
        }
        currentModel.put("Noitems", NoItemList);

        return currentModel;
    }

    // Method which populates the current model view with the boots information.
    private Map<String, Object> bootsModel(long cuID, Map<String, Object> currentModel){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        if (currentPlayer.getBoots()!=null){
            currentModel.put("bootsName", currentPlayer.getBoots().getName());
            currentModel.put("bootsBonus", currentPlayer.getBoots().getBonus());
            currentModel.put("bootsValue", currentPlayer.getBoots().getValue());
            currentModel.put("bootsSell", currentPlayer.getBoots().getSell());
        }
        else{
            currentModel.put("bootsName", "Keine Schuhe");
        }
        return currentModel;
    }

    // Method which populates the current model view with the armour information.
    private Map<String, Object> armourModel(long cuID, Map<String, Object> currentModel){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        if (currentPlayer.getArmour()!=null){
            currentModel.put("armourName", currentPlayer.getArmour().getName());
            currentModel.put("armourBonus", currentPlayer.getArmour().getBonus());
            currentModel.put("armourValue", currentPlayer.getArmour().getValue());
            currentModel.put("armourSell", currentPlayer.getArmour().getSell());
        }
        else{
            currentModel.put("armourName", "Keine Rüstung");
        }
        return currentModel;
    }

    // Method which populates the current model view with the headgear information.
    private Map<String, Object> headgearModel(long cuID, Map<String, Object> currentModel){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        if (currentPlayer.getHeadgear()!=null){
            currentModel.put("headgearName", currentPlayer.getHeadgear().getName());
            currentModel.put("headgearBonus", currentPlayer.getHeadgear().getBonus());
            currentModel.put("headgearValue", currentPlayer.getHeadgear().getValue());
            currentModel.put("headgearSell", currentPlayer.getHeadgear().getSell());
        }
        else{
            currentModel.put("headgearName", "Keine Kopfbedeckung");
        }
        return currentModel;
    }

    // Method which sets the "fights" attribute of a player to true/false based on
    // whether the player wants to participate in the fight or not.
    private void fightState(long cuID){
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        // Change the button name and the player attribute accordingly
        if (currentPlayer.getFights()==true){
            currentPlayer.setFights(false);
        }
        else{
            currentPlayer.setFights(true);
        }
    }

    // Method which populates the current model view with player information
    // (class, race, level, fighting strength, fight participation, etc...).
    private Map<String, Object> playerModel(long cuID, Map<String, Object> currentModel){

        // Info of the current player
        Player currentPlayer = findPlayerbyID(cuID, gameState.getAllPlayers());
        String playerName = userRepository.findById(cuID).get().getName();
        currentModel.put("currentPlayerName", playerName);
        currentModel.put("currentPlayerClass", getPlayerClass(currentPlayer.getPlayerClass()));
        currentModel.put("currentPlayerRace", getPlayerRace(currentPlayer.getPlayerRace()));
        currentModel.put("currentPlayerLevel", currentPlayer.getLevel());
        currentModel.put("currentPlayerStrength", String.valueOf(currentPlayer.getFightStrength()));

        // Changes the name of the fight button to be the opposite of the
        // player fighting state. The button will be shown only for the non-main players
        // in case of a monster door (as per doorModel controller!).
        if (currentPlayer.getFights()==true){
            currentModel.put("fightingState","Nicht mitkaempfen!");
        }
        else{
            currentModel.put("fightingState","Mitkaempfen!");
        }

        // Info of the other players
        int pCount = 1;
        for (Player p:gameState.getAllPlayers()){
            if ((p != currentPlayer) && (userRepository.existsById(p.getId()))){
                String otherPlayerName = userRepository.findById(p.getId()).get().getName();
                currentModel.put("otherPlayerName"+pCount, otherPlayerName);
                currentModel.put("otherPlayerClass"+pCount, getPlayerClass(p.getPlayerClass()));
                currentModel.put("otherPlayerRace"+pCount, getPlayerRace(p.getPlayerRace()));
                currentModel.put("otherPlayerLevel"+pCount, p.getLevel());
                currentModel.put("otherPlayerStrength"+pCount, String.valueOf(p.getFightStrength()));
                pCount++;
            }
        }

        return currentModel;
    }

    // Method which returns a player object from the player list
    // based on the player id.
    private Player findPlayerbyID(long playerID, List<Player> players){
        for (Player p:players) {
            if (p.getId() == playerID) {
                return p;
            }
        }
        return null;
    }

    // Method which returns a player class name (in german)
    // based on the player class type.
    private String getPlayerClass(PlayerClass pC){
        String out = "Klasse: N/A";
        if (pC instanceof Dwarf){
            out = "Zwerg";
        }
        else if (pC instanceof Elf){
            out = "Elf";
        }
        else if (pC instanceof Halbling){
            out = "Halbling";
        }
        else if (pC instanceof Human){
            out = "Mensch";
        }
        return out;
    }

    // Method which returns a player race name (in german)
    // based on the player race type.
    private String getPlayerRace(PlayerRace pR){
        String out = "Rasse: N/A";
        if (pR instanceof Priest){
            out = "Priester";
        }
        else if (pR instanceof Thief){
            out = "Dieb";
        }
        else if (pR instanceof Warrior){
            out = "Krieger";
        }
        else if (pR instanceof Wizard){
            out = "Zauberer";
        }
        return out;
    }

    // Method which returns the name of the main player from the GameState DTO.
    private String getMainPlayerName(){
        return userRepository.findById(gameState.getMainPlayer().getId()).get().getName();
    }

    // Method which returns a string with the name of a treasure card.
    private String getTreasureType(TreasureCard tc){
        if (tc instanceof Boots) return "Schuhe";
        else if (tc instanceof Armour) return "Ruestung";
        else if (tc instanceof Headgear) return "Kopfbedeckung";
        else if (tc instanceof Item) return "Gegenstand";
        else if (tc instanceof LevelSpell) return "LevelUp";
        return "Unbekannt";
    }

    // This method simulates the dice throwing during the board game.
    // If >= is thrown (>=3 for Elfs) the player escapes!
    private void run(){
        int playerScore = (new Random()).nextInt(0,6)+1;
        dynamicInformation += " Gewuerfelt: "+playerScore;
        if (gameState.getMainPlayer().getPlayerClass() instanceof Elf) {
            playerScore++;
            dynamicInformation += ". HS ist ein Elf (+1 beim Weglaufen).";
        }
        if (playerScore>3) {
            dynamicInformation += ". HS konnte weglaufen!";
        }
        else{
            Monster monster = (Monster)gameState.getCurrentDoorCard();
            dynamicInformation += " "+monster.monsterWinsFight(gameState.getMainPlayer());
            gameState.getMainPlayer().calculateFightStrength();
        }
    }
}
