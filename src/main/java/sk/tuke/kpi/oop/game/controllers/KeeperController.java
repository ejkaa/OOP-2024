package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener {
    private final Keeper keeper;

    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if (key == Input.Key.ENTER)
            new Take<>().scheduleFor(keeper);

        if (key == Input.Key.BACKSPACE)
            new Drop<>().scheduleFor(keeper);

        if (key == Input.Key.S)
            new Shift<>().scheduleFor(keeper);

        if (key == Input.Key.U)
            useActor();

        if (key == Input.Key.B)
            useItem();
    }

    private void useActor(){
        for (Actor actor : keeper.getScene().getActors())
            if (actor instanceof Usable<?> && keeper.intersects(actor)) {
                Usable<?> usable = (Usable<?>) actor;
                new Use<>(usable).scheduleForIntersectingWith(keeper);
                break;
            }
    }

    private void useItem(){
        Collectible collectible = keeper.getBackpack().peek();
        if (collectible == null) return;

        if (collectible instanceof Usable<?>) {
            Usable<?> item = (Usable<?>) collectible;
            new Use<>(item).scheduleForIntersectingWith(keeper);
        }
    }
}
