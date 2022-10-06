package com.dankscripts.dankfletcher.data;

public enum BowType {

    ARROW_SHAFTS("Logs", 270, 14),
    SHORTBOW("Logs", 270, 16),
    LONGBOW("Logs", 270, 17),
    OAK_SHORTBOW("Oak logs", 270, 15),
    OAK_LONGBOW("Oak logs", 270, 16),
    WILLOW_SHORTBOW("Willow logs", 270, 15),
    WILLOW_LONGBOW("Willow logs", 270, 16),
    MAPLE_SHORTBOW("Maple logs", 270, 15),
    MAPLE_LONGBOW("Maple logs", 270, 16),
    YEW_SHORTBOW("Yew logs", 270, 15),
    YEW_LONGBOW("Yew logs", 270, 16),
    MAGIC_SHORTBOW("Magic logs", 270, 15),
    MAGIC_LONGBOW("Magic logs", 270, 16);

    private String logName;
    private int parentInterfaceId, childInterfaceId;

    BowType(String logName, int parentInterfaceId, int childInterfaceId) {
        this.logName = logName;
        this.parentInterfaceId = parentInterfaceId;
        this.childInterfaceId = childInterfaceId;
    }

    public int getParentInterfaceId() {
        return parentInterfaceId;
    }

    public int getChildInterfaceId() {
        return childInterfaceId;
    }

    public String getLogName() {
        return logName;
    }

    @Override
    public String toString() {
        String s = super.toString().replace('_',' ');
        return s.charAt(0) + s.substring(1, s.length()).toLowerCase();
    }

    public String getUnstrungName() {
        String s = super.toString().replace('_',' ');
        return s.charAt(0) + s.substring(1, s.length()).toLowerCase() + " (u)";
    }

}
