package scripts;


import Tasks.SpiderKiller.*;
import Tasks.Task;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name = "Spider Killer", description = "f2p stronghold spider killer", properties = "client=4; topic=0;")

public class SpiderKiller extends PollingScript<ClientContext> {

    private List<Task> taskList = new ArrayList<Task>();


    @Override
    public void start(){
        taskList.addAll(Arrays.asList(new FindFish(ctx), new CookFish(ctx), new DropBurntFish(ctx), new TraverseToSpiders(ctx)
                , new CombatSpiders(ctx)));
    }


    @Override
    public void stop(){

    }


    @Override
    public void poll() {
        for(Task task : taskList){
            if(task.activate()){
                task.execute();
            }
        }
    }




}
