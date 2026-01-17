package models.enums;

import org.dreambot.api.methods.map.Area;

public enum BanksAreas {
    FALADOR(new Area(3007, 3362, 3022, 3355));

    private final Area area;

    BanksAreas(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }
}
