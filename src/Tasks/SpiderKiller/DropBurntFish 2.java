package Tasks.SpiderKiller;

import Tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;

public class DropBurntFish extends Task<ClientContext> {
    public final int BURNT_FISH = 343;
    public static boolean hasDroppedBurntFish = false;
    public DropBurntFish(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return FindFish.hasLooted
                && CookFish.hasCooked
                && !hasDroppedBurntFish;
    }

    @Override
    public void execute() {
        if(ctx.inventory.select().id(BURNT_FISH).poll().valid()){
            System.out.println("Dropping burnt fish...");

            // dropping burnt fish from inv
            if(ctx.players.local().animation() == -1){
                for(Item i : ctx.inventory.name("Burnt fish")){
                    i.interact("Drop", "Burnt fish");
                }
            }
            ctx.inventory.select().name("Burnt fish").poll().interact("Drop");
            FindFish.hasLooted = false;
            CookFish.hasCooked = false;

        } else {
            System.out.println("No burnt fish in inv. hasLooted & hasCooked");
            hasDroppedBurntFish = true;
        }
    }
}
