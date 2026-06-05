package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable> {

    @Override
    public void setUp(Movable actor) {
        if (actor == null) return;
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> {
                    Direction direction = Direction.getRandomDirection();
                    Move<Movable> move = new Move<>(direction, 0.5f);
                    move.setActor(actor);
                    move.scheduleFor(actor);
                }),
                new Wait<>(0.5f)
            )
        ).scheduleFor(actor);
    }
}
