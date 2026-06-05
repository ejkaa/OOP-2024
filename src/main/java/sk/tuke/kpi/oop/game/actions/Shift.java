package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Backpack;

public class Shift<A extends Keeper> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        setDone(true);
        Keeper keeper = getActor();
        if (keeper == null) return;
        Backpack backpack = keeper.getBackpack();
        backpack.shift();
    }
}
