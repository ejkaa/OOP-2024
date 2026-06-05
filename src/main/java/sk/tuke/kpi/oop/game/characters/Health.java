package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int hp;
    private int maxHp;
    private List<FatigueEffect> list;

    public Health(int maxHp) { this(maxHp, maxHp); }
    public Health(int hp, int maxHp) {
        this.hp = hp;
        this.maxHp = maxHp;
        list = new ArrayList<>();
    }

    public int getValue(){
        return hp;
    }
    public void restore() { hp = maxHp; }
    public void refill (int amount) {
        hp += amount;
        if (hp > maxHp) hp = maxHp;
    }
    public void exhaust() {
        hp = 0;
        for (FatigueEffect effect : list ) effect.apply();
        list.clear();
    }
    public void drain (int amount) {
        hp -= amount;
        if (hp <= 0) {
            hp = 0;
            exhaust();
        }
    }

    @FunctionalInterface
    public interface FatigueEffect { void apply(); }
    public void onFatigued(FatigueEffect effect) { list.add(effect); }
}
