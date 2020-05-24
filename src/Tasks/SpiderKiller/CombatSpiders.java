package Tasks.SpiderKiller;

import Tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class CombatSpiders extends Task<ClientContext> {
    public final int COOKED_TROUT = 333;
    public final int COOKED_SALMON = 329;
    public static boolean hasRunOutOfFood = false;

    public CombatSpiders(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return TraverseToSpiders.hasTraversedToSpiders
                && !hasRunOutOfFood;
    }

    @Override
    public void execute() {
        if(!ctx.inventory.select().id(COOKED_SALMON, COOKED_TROUT).poll().valid()){
            hasRunOutOfFood = true;
        }
        System.out.println("Combat spiders");

        if(!ctx.players.local().inMotion()
                || ctx.movement.destination().distanceTo(ctx.players.local()) < 5
                || ctx.movement.destination().equals(Tile.NIL)){
            ctx.movement.newTilePath(new Tile(2128, 5270, 0)).traverse();
        }
        if(ctx.players.local().healthPercent() < 25 && ctx.inventory.select().id(COOKED_SALMON, COOKED_TROUT).poll().valid()){
            ctx.inventory.select().id(COOKED_TROUT, COOKED_SALMON).poll().interact("Eat");
            Condition.wait(() -> ctx.players.local().animation() == -1);
        }

    }
}
