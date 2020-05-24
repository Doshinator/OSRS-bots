package Tasks.ChickenSlayer;

import Tasks.Task;
import org.powerbot.script.rt4.ClientContext;

public class CombatChicken extends Task<ClientContext> {

    public int health;

    public CombatChicken(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        // if in the correct area &  not interacting w/ an npc  & player's health is > 30%
        if(ctx.players.local().healthBarVisible()){
            health = ctx.players.local().healthPercent();
        }
        return ctx.players.local().interacting().name().equals("Chicken") && health > 30;
    }

    @Override
    public void execute() {
        System.out.println(health);
    }
}
