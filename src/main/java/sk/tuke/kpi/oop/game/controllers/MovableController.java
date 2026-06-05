package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private final Movable movable;
    private Move<Movable> move;
    private final Set<Direction> set = new HashSet<>();

    private final Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.LEFT, Direction.WEST)
    );

    public MovableController(Movable movable) {
        this.movable = movable;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if (movable == null || !keyDirectionMap.containsKey(key)) return;

        set.add(keyDirectionMap.get(key));
        update();
    }

    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if (movable == null || !keyDirectionMap.containsKey(key)) return;

        set.remove(keyDirectionMap.get(key));
        update();
    }

    private void update() {
        if (move != null && !move.isDone()) move.stop();

        Direction direction = Direction.NONE;
        for (Direction dir : set)
            direction = direction.combine(dir);

        if (direction == Direction.NONE) return;
        move = new Move<Movable>(direction, Float.MAX_VALUE);
        move.scheduleFor(movable);
    }
}
