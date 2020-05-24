package scripts;

import Tasks.LumberJack.ChopTree;
import Tasks.LumberJack.DropLog;
import Tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Random;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Script.Manifest(name = "LumberJack", description = "dynamic woodcutter", properties = "client=4; topic=0;")



public class LumberJack extends PollingScript<ClientContext>{

    private List<Task> taskList = new ArrayList<Task>();


    @Override
    public void start(){
        System.out.println("Started script");
        taskList.addAll(Arrays.asList(new DropLog(ctx), new ChopTree(ctx)));
    }

    @Override
    public void stop(){
        System.out.println("Started script");
    }

    @Override
    public void poll(){
        for(Task task : taskList){
            if(task.activate()){
                task.execute();
            }
        }
    }
}
