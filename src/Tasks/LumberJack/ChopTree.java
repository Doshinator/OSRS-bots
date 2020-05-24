package Tasks.LumberJack;

import Tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

public class ChopTree extends Task<ClientContext> {
    public ChopTree(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().animation() == -1
                && !ctx.players.local().inMotion()
                && ctx.objects.select().name("Willow").nearest().poll().valid();
    }

    @Override
    public void execute() {
        System.out.println("Player not animating && Player not in motion && Trees valid. Chopping Trees...");
        ctx.objects.select().name("Willow").nearest().poll().interact("Chop", "Willow");
        Condition.sleep(3500);
    }
}
