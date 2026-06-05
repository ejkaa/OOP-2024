package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Teleport extends AbstractActor {
    private Teleport destination;
    private boolean playerInside;

    public Teleport(Teleport teleport) {
        this.destination = teleport;
        playerInside = false;
        Animation animation = new Animation("sprites/lift.png");
        setAnimation(animation);
    }

    public Teleport getDestination() { return destination; }
    public void setDestination(Teleport destination) {
        if (destination == this) return;
        this.destination = destination;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::checkPlayerInside)).scheduleFor(this);
    }

    private void checkPlayerInside(){
        Player player = (Player) getScene().getFirstActorByName("Player");
        if (destination == null || player == null) return;

        Point2D.Float center = new Point2D.Float(player.getPosX() +16, player.getPosY() +16);
        Rectangle2D.Float rectangle = new Rectangle2D.Float(getPosX(), getPosY(), 48, 48);

        if (playerInside && !rectangle.contains(center)) playerInside = false;
        else if (!playerInside && rectangle.contains(center)) destination.teleportPlayer(player);
    }

    public void teleportPlayer(Player player) {
        player.setPosition(getPosX() + 8, getPosY() + 8);
        playerInside = true;
    }
}
