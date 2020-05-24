package Tasks.SpiderKiller;

import Tasks.Task;
import jdk.nashorn.internal.runtime.regexp.joni.constants.Traverse;
import org.powerbot.script.Condition;
import org.powerbot.script.Locatable;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Movement;

public class TraverseToSpiders extends Task<ClientContext> {
    final public int STRONGHOLD_ENTRANCE = 20790;
    final public int MINOTAUR_PORTAL = 20786;
    final public int LADDER_TO_FLESH_CRAWLER = 20785;
    final public int LADDER_TO_GIANT_SPIDER = 19004;
    final public int FLESH_CRAWLER_PORTAL = 19005;
    final public int OOZING_DOOR = 23653;
    public final int FIRE = 26185;
    final public int FLOWERS = 1197;
    final public int SPIDER_PORTAL = 23707;



    static public boolean hasTraversedToSpiders = false;

    public static final Tile[] STRONGHOLD_ENTRANCE_AREA_TILE = new Tile[]{
            new Tile(3103, 3430, 0),
            new Tile(3090, 3420, 0),
            new Tile(3081, 3421, 0)
    };

    public TraverseToSpiders(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return FindFish.hasLooted
                && CookFish.hasCooked
                && DropBurntFish.hasDroppedBurntFish
                && !hasTraversedToSpiders;
    }

    @Override
    public void execute() {
        if(ctx.movement.energyLevel() > 35 && !ctx.movement.running()){
            ctx.movement.running(true);
        }

       // traverse to entrance
        if(ctx.objects.select().id(STRONGHOLD_ENTRANCE).nearest().poll().valid()
                && ctx.objects.select().id(FIRE).nearest().poll().valid()){
            if(!ctx.players.local().inMotion()
                    || ctx.movement.destination().distanceTo(ctx.players.local()) < 5
                    || ctx.movement.destination().equals(Tile.NIL)){
                System.out.println("traversing to entrance");
                ctx.movement.newTilePath(STRONGHOLD_ENTRANCE_AREA_TILE).traverse();
            }
        }
        // Stronghold entrance
        if(ctx.objects.select().id(STRONGHOLD_ENTRANCE).nearest().poll().inViewport()
                && !ctx.players.local().inMotion()
                && !ctx.objects.select().id(MINOTAUR_PORTAL).nearest().poll().valid()){
            System.out.println("Climbing down stronghold entrance");
            ctx.objects.select().id(STRONGHOLD_ENTRANCE).poll().interact("Climb-down");
            Condition.wait(()-> ctx.objects.select().id(MINOTAUR_PORTAL).nearest().poll().valid(),250,5);
        }

        // Minotaur portal traverse
        if(ctx.objects.select().id(MINOTAUR_PORTAL).poll().valid()){
            // yaw 144, pitch 99
            System.out.println("Valid portal - minotaur");
            ctx.camera.turnTo(ctx.objects.select().id(MINOTAUR_PORTAL).poll());
            ctx.objects.select().id(MINOTAUR_PORTAL).poll().interact("Use");
            Condition.wait(() -> !ctx.objects.select().id(MINOTAUR_PORTAL).poll().valid());
            System.out.println("Finished taking the portal...");
        }
        // minotaur ladder to flesh crawler
        if(ctx.objects.select().id(LADDER_TO_FLESH_CRAWLER).nearest().poll().valid()
                && !ctx.players.local().inMotion()
                && ctx.objects.select().id(FLOWERS).nearest().poll().valid()) {
            System.out.println("Moving to the ladder");
            ctx.movement.step(new Tile(1093, 5222, 0));
            Condition.wait(() -> ctx.objects.select().id(LADDER_TO_FLESH_CRAWLER).nearest().poll().inViewport());
            // tile to take the ladder down
            System.out.println("Taking the ladder - flesh crawler ladder");
            ctx.objects.select().id(LADDER_TO_FLESH_CRAWLER).poll().interact("Climb-down");
            Condition.wait(()-> ctx.npcs.select().name("Flesh crawler").nearest().poll().valid());
        }


        // flesh crawler portal traverse
        if(ctx.objects.select().id(FLESH_CRAWLER_PORTAL).nearest().poll().inViewport()
                && !ctx.players.local().inMotion()) {
            System.out.println("Valid portal - flesh crawler");
            ctx.objects.select().id(FLESH_CRAWLER_PORTAL).nearest().poll().interact("Use");
            Condition.wait(()-> !ctx.objects.select().id(FLESH_CRAWLER_PORTAL).nearest().poll().inViewport());
            System.out.println("Finished taking the portal...");
        }
        // flesh crawler ladder to giant spider
        if(ctx.objects.select().id(LADDER_TO_GIANT_SPIDER).nearest().poll().valid()
                && !ctx.players.local().inMotion()) {
            ctx.camera.turnTo(ctx.objects.select().id(LADDER_TO_GIANT_SPIDER).nearest().poll());
            ctx.objects.select().id(LADDER_TO_GIANT_SPIDER).poll().interact("Climb-down");
            Condition.wait(() -> ctx.npcs.select().name("Giant spider").nearest().poll().valid());
        }

        // 23707
        // finish up the spider traversal
        if(ctx.objects.select().id(SPIDER_PORTAL).nearest().poll().valid()){
            System.out.println("At spiders");

            ctx.camera.turnTo(ctx.objects.select().id(OOZING_DOOR).nearest().poll());
            // opening the first oozing door
            ctx.objects.select().at(new Tile(2132, 5257, 0)).id(OOZING_DOOR).poll().interact("Open");
            Condition.wait(()-> !ctx.players.local().inMotion() && ctx.players.local().animation() == 1);
            // opening the second oozing door
            ctx.objects.select().at(new Tile(2132, 5260, 0)).id(OOZING_DOOR).poll().interact("Open");
            Condition.wait(() -> ctx.players.local().animation() == -1);
            hasTraversedToSpiders = true;
            System.out.println("Has traversed = true");
        }

    }

}
