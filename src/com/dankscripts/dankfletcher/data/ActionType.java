package com.dankscripts.dankfletcher.data;

public enum ActionType {

    CUT_BOWS,
    STRING_BOWS,
    MAKE_ARROWS,
    MAKE_DARTS;

    @Override
    public String toString() {
        String s = super.toString().replace('_',' ');
        return s.charAt(0) + s.substring(1, s.length()).toLowerCase();
    }

}
