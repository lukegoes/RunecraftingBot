package models.enums;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;

public enum RuinsAreas {
    AIR(new Area(2976, 3299, 2993, 3284), "Air rune", "Air tiara", new Tile(2844, 4834, 0)),
    FIRE(new Area(3304, 3260, 3316, 3249), "Fire rune", "Fire tiara", new Tile(2584, 4838, 0));

    private final Area area;
    private final String rune;
    private final String equipment;
    private final Tile altarTile;

    public Tile getAltarTile() {
        return altarTile;
    }

    public String getEquipment() {
        return equipment;
    }

    RuinsAreas(Area area, String rune, String equipment, Tile altarTile) {
        this.area = area;
        this.rune = rune;
        this.equipment = equipment;
        this.altarTile = altarTile;
    }

    public String getRune() {
        return rune;
    }

    public Area getArea() {
        return area;
    }
}
