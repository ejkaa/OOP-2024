package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class ChainBomb extends TimeBomb{
    private final int radius;

    public ChainBomb(float time) {
        super(time);
        radius = 50;
    }

    @Override
    public void activate() {
        super.activate();
        new When<>(
            this::isExploded,
            new Invoke<>(this::activateOthers)
        ).scheduleFor(this);
    }

    private void activateOthers() {
        Scene scene = getScene();
        if (scene == null) return;

        Ellipse2D.Float ellipse = new Ellipse2D.Float(getPosX() - radius +8, getPosY() - radius +8, radius *2, radius *2);

        for (Actor actor : scene.getActors()) {
            if (actor instanceof ChainBomb) {
                if (((ChainBomb) actor).isActivated()) continue;
                Rectangle2D.Float rectangle = new Rectangle2D.Float(actor.getPosX(), actor.getPosY(), actor.getWidth(), actor.getHeight());

                if (ellipse.intersects(rectangle)) {
                    ((ChainBomb) actor).activate();
                }
            }
        }
    }
}
