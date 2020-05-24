package Tasks.SpiderKiller;

import Tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Interactive;

public class CookFish extends Task<ClientContext> {
    public final int RAW_SALMON = 331;
    public final int RAW_TROUT = 335;
    public final int COOKED_TROUT = 333;
    public final int COOKED_SALMON = 329;
    public final int BURNT_FISH = 343;
    public final int FIRE = 26185;

//    FindFish findfish = new FindFish(ctx);
    static public boolean hasCooked = false;
    public CookFish(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return FindFish.hasLooted
                && !hasCooked;
    }

    @Override
    public void execute() {
        if(ctx.inventory.select().id(RAW_TROUT, RAW_SALMON).poll().valid()){
            System.out.println("Inv contains raw food");
            System.out.println("Cooking...");

            // uses the food with fire and cooks
            if(ctx.players.local().animation() == -1){
                if(ctx.objects.select().id(FIRE).nearest().poll().inViewport()){
                    ctx.inventory.select().id(RAW_TROUT, RAW_SALMON).poll().interact("Use");
                    ctx.objects.select().id(FIRE).nearest().poll().interact("Use", "Fire");
                    Condition.wait(() -> ctx.players.local().animation() == 1);
                    ctx.widgets.component(270,14).click();
                } else{
                    ctx.movement.step(ctx.objects.select().id(FIRE).nearest().poll());
                    ctx.camera.turnTo(ctx.objects.select().id(FIRE).nearest().poll());
                }
            }
        }else{
            System.out.println("Inv does not contain raw food.. hasCooked = true. moving onto checking burnt fish");
            hasCooked = true;
        }

    }
}
