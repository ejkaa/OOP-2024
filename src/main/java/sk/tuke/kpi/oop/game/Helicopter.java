package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {
    private Player player;
    public Helicopter() {
        Animation animation = new Animation("sprites/heli.png", 64, 64);
        setAnimation(animation);
    }

    public void searchAndDestroy() {
        player = (Player) getScene().getFirstActorByName("Player");
        new Loop<>(new Invoke<>(this::follow)).scheduleFor(this);
    }

    private void follow(){
        if (player == null) return;
        int hp = player.getEnergy();
        if (hp < 1) return;

        int xP = player.getPosX(); int yP = player.getPosY();
        int xH = this.getPosX(); int yH = this.getPosY();

        if (xP > xH) xH++;
        else if (xP < xH) xH--;

        if (yP > yH) yH++;
        else if (yP < yH) yH--;

        setPosition(xH,yH);
        if (this.intersects(player)) player.setEnergy(hp - 1);
    }
}
