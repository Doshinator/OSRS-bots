package Tasks.LumberJack;


import Tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;

public class DropLog extends Task<ClientContext> {

    public DropLog(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate(){
        return ctx.inventory.isFull();
    }
    @Override
    public void execute(){
        System.out.println("Inventory is full... Dropping logs.");
        // * could make this dynamic later to drop detected logs
        // drops willow logs
        for(Item i : ctx.inventory.select().name("Willow logs")){
            i.interact("drop");
            Condition.sleep(500);

        }
    }
}
