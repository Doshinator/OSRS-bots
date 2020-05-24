package Tasks.ChickenSlayer;

import Tasks.Task;
import org.powerbot.script.rt4.ClientContext;

public class Navigate extends Task<ClientContext> {

    public boolean isAtChickens = false;

    public Navigate(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        // if out of bound, get in bound
        return false;
    }

    @Override
    public void execute() {

    }
}
