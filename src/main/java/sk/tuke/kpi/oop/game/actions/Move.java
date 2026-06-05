package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.gamelib.map.SceneMap;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Move<A extends Movable> implements Action<A> {
    private final Direction direction;
    private final float duration;
    private A actor;
    private boolean done;
    private boolean firstTime;
    private float wholeTime;

    public Move(Direction direction) {
        this(direction, 0);
    }
    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
        actor = null;
        done = false;
        firstTime = true;
        wholeTime = 0;
    }

    @Override
    public @Nullable A getActor() {
        return actor;
    }
    @Override
    public void setActor(@Nullable A actor) {
        this.actor = actor;
    }
    @Override
    public boolean isDone() { return done; }
    @Override
    public void reset() {
        done = false;
        firstTime = true;
    }
    public void stop(){
        done = true;
        if (actor != null)
            actor.stoppedMoving();
    }

    @Override
    public void execute(float deltaTime) {
        if (actor == null || done) return;
        if (firstTime) {
            firstTime = false;
            actor.startedMoving(direction);
        }
        if (actor.getScene() == null) return;
        SceneMap map = actor.getScene().getMap();

        int speed = actor.getSpeed();
        int oldX = actor.getPosX();
        int oldY = actor.getPosY();
        int newX = oldX + speed * direction.getDx();
        int newY = oldY + speed * direction.getDy();
        actor.setPosition(newX, newY);

        if (map.intersectsWithWall(actor)){
            actor.setPosition(oldX, oldY);
            actor.collidedWithWall();
        }

        wholeTime += deltaTime;
        if (wholeTime >= duration) stop();
    }
}
