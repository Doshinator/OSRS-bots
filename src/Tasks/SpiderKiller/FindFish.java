package Tasks.SpiderKiller;

import Tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;

public class FindFish extends Task<ClientContext> {
    public final int RAW_SALMON = 331;
    public final int RAW_TROUT = 335;
    public final int COOKED_TROUT = 333;
    public final int COOKED_SALMON = 329;
    public final int BURNT_FISH = 343;
    public final int FIRE = 26185;

    static public boolean hasLooted = false;

    public FindFish(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        return !hasLooted;
    }

    @Override
    public void execute() {
        if(!ctx.inventory.isFull() && atFishingSpot()){
            System.out.println("hasLooted = false .... querying for food");
            ctx.groundItems.select().id(RAW_SALMON, RAW_TROUT, COOKED_SALMON, COOKED_TROUT).nearest()/*.limit(5).shuffle()*/.poll().interact("Take");
        } else {
            hasLooted = true;
            System.out.println("hasLooted = true ... moving onto cooking food");
        }

    }

    public boolean atFishingSpot() {
        return ctx.npcs.select().name("Rod Fishing spot").nearest().poll().valid();
    }
}
