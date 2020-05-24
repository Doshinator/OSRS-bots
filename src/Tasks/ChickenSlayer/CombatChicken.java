package Tasks.ChickenSlayer;

import Tasks.Task;
import org.powerbot.script.rt4.ClientContext;

public class CombatChicken extends Task<ClientContext> {
    public CombatChicken(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        // if in the correct area &  not interacting w/ an npc  & player's health is > 30%
        return false;
    }

    @Override
    public void execute() {

    }
}
