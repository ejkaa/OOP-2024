package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {
    private final List<Collectible> items;
    private final String name;
    private final int capacity;

    public Backpack(String name, int capacity) {
        items = new ArrayList<>(capacity);
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return new ArrayList<>(items);
    }
    @Override
    public @NotNull String getName() {
        return name;
    }
    @Override
    public int getCapacity() {
        return capacity;
    }
    @Override
    public int getSize() {
        return items.size();
    }
    @Override
    public @NotNull Iterator<Collectible> iterator() {
        return items.iterator();
    }


    @Override
    public void add(@NotNull Collectible actor) {
        if (getSize() < capacity) items.add(actor);
        else throw new IllegalStateException(String.format("%s is full", name));
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        items.remove(actor);
    }

    @Override
    public @Nullable Collectible peek() {
        if (getSize() < 1) return null;
        return items.get(items.size() - 1);
    }

    @Override
    public void shift() {
        if (items.size() < 2) return;
        for (int i = items.size() - 1; i > 0; i--) {
            Collections.swap(items, i, i-1);
        }
    }
}
