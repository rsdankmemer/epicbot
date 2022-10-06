package com.dankscripts.dankfletcher.data;

public enum ArrowType {

    HEADLESS("Feather", "Arrow shaft"),
    BRONZE("Bronze arrowtips", "Headless arrow"),
    IRON("Iron arrowtips", "Headless arrow"),
    STEEL("Steel arrowtips", "Headless arrow"),
    MITHRIL("Mithril arrowtips", "Headless arrow"),
    BROAD("Broad arrowheads", "Headless arrow"),
    ADAMANT("Adamant arrowtips", "Headless arrow"),
    RUNE("Rune arrowtips", "Headless arrow"),
    AMETHYST("Amethyst arrowtips", "Headless arrow"),
    DRAGON("Dragon arrowtips", "Headless arrow");

    private String arrowheadName, combinationItemName;

    ArrowType(String arrowheadName, String combinationItemName) {
        this.arrowheadName = arrowheadName;
        this.combinationItemName = combinationItemName;
    }

    public String getArrowheadName() {
        return arrowheadName;
    }

    public String getCombinationItemName() {
        return combinationItemName;
    }

    @Override
    public String toString() {
        String s = super.toString().replace('_',' ');
        return s.charAt(0) + s.substring(1, s.length()).toLowerCase();
    }
}
