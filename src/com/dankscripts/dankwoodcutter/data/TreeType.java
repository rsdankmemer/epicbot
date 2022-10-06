package com.dankscripts.dankwoodcutter.data;

import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;
import com.dankscripts.api.utils.Location;

public enum TreeType {

    TREE(1,
            new Location("Lumbridge", new Area(3192, 3239, 3202, 3249)),
            new Location("North Seers' Village", new Area(2706, 3499, 2717, 3504)),
            new Location("Draynor Village", new Area(3103, 3226, 3108, 3232)),
            new Location("Grand Exchange", new Area(3174, 3465, 3144, 3443))),
    OAK(15,
            new Location("Lumbridge", new Area(3187, 3252, 3208, 3237)),
            new Location("West Varrock", new Area(3160, 3410, 3171, 3423)),
            new Location("Seers' Village Bank", new Area(2731, 3490, 2734, 3494))),
    WILLOW(30,
            new Location("Draynor Village", new Area(3080, 3238, 3091, 3224)),
            new Location("North Seers' Village", new Area(2707, 3506, 2714, 3514)),
            new Location("Rimmington", new Area(2957, 3201, 2975, 3191))),
    TEAK(35,
            new Location("Castle Wars", new Area(2332, 3046, 2336, 3050))),
    MAPLE_TREE(45,
            new Location("South Seers' Village Bank", new Area(2728, 3480, 2731, 3482)),
            new Location("North Seers' Village Bank", new Area(2720, 3498, 2734, 3502))),
    MAHOGANY(50),
    YEW(60,
            new Location("Grand Exchange", new Area(
                    new Tile(3201, 3501),
                    new Tile(3201, 3507),
                    new Tile(3226, 3507),
                    new Tile(3226, 3498),
                    new Tile(3207, 3498),
                    new Tile(3207, 3499),
                    new Tile(3206, 3499),
                    new Tile(3206, 3500),
                    new Tile(3205, 3500),
                    new Tile(3205, 3501)

            )),
            new Location("Falador", new Area(2994, 3309, 2998, 3314)),
            new Location("Port Sarim", new Area(3050, 3268, 3056, 3273)),
            new Location("Varrock South", new Area(3250, 3361, 3254, 3365)),
            new Location("Varrock Church", new Area(3247, 3470, 3251, 3475)),
            new Location("Edgeville", new Area(
                    new Tile(3085, 3468),
                    new Tile(3085, 3483),
                    new Tile(3090, 3483),
                    new Tile(3090, 3474),
                    new Tile(3092, 3474),
                    new Tile(3092, 3468)
            )),
            new Location("Seers' Village", new Area(
                    new Tile(2704, 3457),
                    new Tile(2704, 3467),
                    new Tile(2708, 3467),
                    new Tile(2708, 3461),
                    new Tile(2718, 3461),
                    new Tile(2718, 3457)
            )),
            new Location("Catherby", new Area(
                    new Tile(2761, 3425),
                    new Tile(2761, 3428),
                    new Tile(2762, 3428),
                    new Tile(2762, 3433),
                    new Tile(2759, 3433),
                    new Tile(2759, 3435),
                    new Tile(2752, 3435),
                    new Tile(2752, 3427),
                    new Tile(2757, 3427),
                    new Tile(2757, 3425)
            )),
            new Location("Woodcutting Guild", new Area(1579, 3501, 1598, 3476))),
    BLISTERWOOD(62, new Location("Darkmeyer", new Area(3625, 3354, 3638, 3371))),
    MAGIC_TREE(75,
            new Location("Sorcerer's Tower", new Area(2698, 3395, 2707, 3400)),
            new Location("Woodcutting Guild", new Area(1575, 3497, 1585, 3480)));
    //REDWOOD(90);

    private int requiredLevel;
    private Location[] locations;

    TreeType(int requiredLevel, Location... locations) {
        this.requiredLevel = requiredLevel;
        this.locations = locations;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public Location[] getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        return sb.charAt(0) + sb.substring(1).toLowerCase().replace("_", " ");
    }
}
