package com.dankscripts.dankwoodcutter.data;

import java.util.Arrays;

public enum AxeType {

    BRONZE_AXE(1, 1),
    IRON_AXE(1, 1),
    STEEL_AXE(6, 5),
    BLACK_AXE(6, 10),
    MITHRIL_AXE(21, 20),
    ADAMANT_AXE(31, 30),
    RUNE_AXE(41, 40),
    DRAGON_AXE(61, 60);

    private int requiredLevel, levelToWeild;

    AxeType(int requiredLevel, int levelToWeild) {
        this.requiredLevel = requiredLevel;
        this.levelToWeild = levelToWeild;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getlevelToWeild() {
        return levelToWeild;
    }

    /*/
    public boolean canEquip() {
        return Skills.getCurrentLevel(Skill.ATTACK) >= getlevelToWeild();
    }

    public boolean canUse() {
        return Skills.getCurrentLevel(Skill.WOODCUTTING) >= getRequiredLevel();
    }
    /*/

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        return sb.charAt(0) + sb.substring(1).toLowerCase().replace("_", " ");
    }

    public static String[] names() {
        return Arrays.toString(AxeType.values()).replaceAll("^.|.$", "").split(", ");
    }

}
