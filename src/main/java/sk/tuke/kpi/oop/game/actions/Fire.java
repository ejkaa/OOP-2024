package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

public class Fire<A extends Armed> extends AbstractAction<A> {
    private A actor;
    public Fire() {}

    @Override
    public void reset() { setDone(false); }
    @Override
    public @Nullable A getActor() { return actor; }
    @Override
    public void setActor(A actor) { this.actor = actor; }

    @Override
    public void execute(float deltaTime) {
        if (isDone()) return;
        setDone(true);

        actor = getActor();
        if (actor == null) return;

        Fireable bullet = actor.getFirearm().fire();
        if (bullet == null) return;

        int X = actor.getPosX() + 8;
        int Y = actor.getPosY() + 8;
        actor.getScene().addActor(bullet, X, Y);

        float angle = actor.getAnimation().getRotation();
        Move<Movable> move = new Move<>(Direction.fromAngle(angle), Float.MAX_VALUE);
        move.setActor(bullet);
        move.scheduleFor(bullet);
    }
}
