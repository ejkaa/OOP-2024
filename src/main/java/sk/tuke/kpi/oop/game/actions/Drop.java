package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Backpack;

public class Drop<A extends Keeper> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        setDone(true);
        Keeper keeper = getActor();
        if (keeper == null) return;
        Scene scene = keeper.getScene();
        if (scene == null) return;
        Backpack backpack = keeper.getBackpack();
        Collectible item = backpack.peek();

        if (item != null) {
            backpack.remove(item);
            int x = keeper.getPosX() + keeper.getWidth() / 2 - item.getWidth() / 2;
            int y = keeper.getPosY() + keeper.getHeight() / 2 - item.getHeight() / 2;
            scene.addActor(item, x, y);
        }

    }
}
