package models.enums;

import org.dreambot.api.methods.map.Area;

public enum RuinsAreas {
    AIR(new Area(2976, 3299, 2993, 3284));

    private final Area area;

    RuinsAreas(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }
}
