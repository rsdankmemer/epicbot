package com.dankscripts.dankfletcher.settings;

import com.dankscripts.dankfletcher.data.ActionType;
import com.dankscripts.dankfletcher.data.ArrowType;
import com.dankscripts.dankfletcher.data.BowType;

public class UserSettings {

    private BowType bowType;
    private ActionType actionType;
    private ArrowType arrowType;

    public BowType getBowType() {
        return bowType;
    }

    public void setBowType(BowType bowType) {
        this.bowType = bowType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public ArrowType getArrowType() {
        return arrowType;
    }

    public void setArrowType(ArrowType arrowType) {
        this.arrowType = arrowType;
    }
}
