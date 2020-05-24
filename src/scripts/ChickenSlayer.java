package scripts;


import Tasks.Task;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Script.Manifest(name = "Chicken Slayer", description = "Kills the chicken in lumbridge", properties = "client=4; topic=0;")

public class ChickenSlayer extends PollingScript<ClientContext> {

    private List<Task> taskList = new ArrayList<Task>();


    @Override
    public void start(){
        System.out.println("Started script");
        taskList.addAll(Arrays.asList());
    }

    @Override
    public void stop(){
        System.out.println("Stopped script");
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
