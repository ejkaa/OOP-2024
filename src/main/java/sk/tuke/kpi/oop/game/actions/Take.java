package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.Keeper;

public class Take<A extends Keeper> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        setDone(true);

        Keeper keeper = getActor();
        if (keeper == null) return;
        Scene scene = keeper.getScene();
        if (scene == null) return;

        for (Actor actor : scene.getActors()) {
            if (actor instanceof Collectible && keeper.intersects(actor)) {
                try {
                    keeper.getBackpack().add((Collectible) actor);
                    scene.removeActor(actor);
                } catch (IllegalStateException exception) {
                    Overlay overlay = scene.getOverlay();
                    overlay.drawText(exception.getMessage(), 0, 0).showFor(2);
                }

                break;
            }
        }
    }
}
