package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A> {
    private int uses;

    public BreakableTool(int uses) { this.uses = uses; }
    public int getRemainingUses() { return uses; }

    public void useWith(A actor) {
        if (actor == null) return;
        uses--;
        if (uses < 1) getScene().removeActor(this);
    }
}
