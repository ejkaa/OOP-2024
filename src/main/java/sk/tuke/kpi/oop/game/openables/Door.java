package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.map.SceneMap;
import sk.tuke.kpi.gamelib.messages.MessageBus;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import static sk.tuke.kpi.oop.game.scenarios.EscapeRoom.Factory.FRONT_OPENED;
import static sk.tuke.kpi.oop.game.scenarios.EscapeRoom.Factory.BACK_OPENED;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private boolean state;
    private MessageBus messageBus;
    private MapTile tile1;
    private MapTile tile2;
    private final Orientation orientation;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public static final Topic<Door> EXIT_OPENED = new Topic<>("exit door opened", Door.class);

    public enum Orientation { VERTICAL, HORIZONTAL }

    public Door(String name, Orientation orientation) {
        super(name);
        Animation vertical = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        Animation horizontal = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        this.orientation = orientation;
        if (orientation == Orientation.VERTICAL) setAnimation(vertical);
        else setAnimation(horizontal);
        state = false;
        tile1 = null;
        tile2 = null;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        SceneMap map = scene.getMap();
        messageBus = scene.getMessageBus();

        tile1 = map.getTile(getPosX() / 16, getPosY() / 16);
        if (orientation == Orientation.VERTICAL)
            tile2 = map.getTile(getPosX() / 16, getPosY() / 16 +1);
        else
            tile2 = map.getTile(getPosX() / 16 +1, getPosY() / 16);
        close();
    }

    @Override
    public boolean isOpen() { return state; }
    @Override
    public void open() {
        state = true;
        getAnimation().setPlayMode(Animation.PlayMode.ONCE);
        String name = getName();

        messageBus.publish(DOOR_OPENED, this);
        switch (name) {
            case "front door":
                messageBus.publish(FRONT_OPENED, this);
                break;
            case "back door":
                messageBus.publish(BACK_OPENED, this);
                break;
            case "exit door":
                messageBus.publish(EXIT_OPENED, this);
                break;
            default:
                break;
        }

        tile1.setType(MapTile.Type.CLEAR);
        tile2.setType(MapTile.Type.CLEAR);
    }
    @Override
    public void close() {
        state = false;
        getAnimation().setPlayMode(Animation.PlayMode.ONCE_REVERSED);

        tile1.setType(MapTile.Type.WALL);
        tile2.setType(MapTile.Type.WALL);

        messageBus.publish(DOOR_CLOSED, this);
    }

    @Override
    public void useWith(Actor actor) {
        if (state) close();
        else open();
    }

    @Override
    public Class<Actor> getUsingActorClass() { return Actor.class; }
}
