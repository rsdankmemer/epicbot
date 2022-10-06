package com.dankscripts.dankfletcher.nodes;


import com.epicbot.api.shared.util.time.Time;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankfletcher.AutoFletcherPro;
import com.dankscripts.dankfletcher.data.ActionType;
import com.dankscripts.dankfletcher.settings.UserSettings;

public class BankNode extends Node {

    AutoFletcherPro script;
    UserSettings userSettings;

    public BankNode(AutoFletcherPro script, UserSettings userSettings) {
        this.script = script;
        this.userSettings = userSettings;
    }

    @Override
    public boolean validate() {
        if(userSettings.getActionType().equals(ActionType.CUT_BOWS)) {
            return !script.getAPIContext().inventory().contains(userSettings.getBowType().getLogName()) || !script.getAPIContext().inventory().contains(AutoFletcherPro.KNIFE_STRING);
        } else if(userSettings.getActionType().equals(ActionType.STRING_BOWS)) {
            return !script.getAPIContext().inventory().contains(userSettings.getBowType().getUnstrungName()) || !script.getAPIContext().inventory().contains(AutoFletcherPro.BOWSTRING_STRING);
        } else if(userSettings.getActionType().equals(ActionType.MAKE_ARROWS)) {
            return !script.getAPIContext().inventory().contains(userSettings.getArrowType().getArrowheadName()) || !script.getAPIContext().inventory().contains(userSettings.getArrowType().getCombinationItemName());
        }
        return false;
    }

    @Override
    public void execute() {
        if(!script.getAPIContext().bank().isOpen() && script.getAPIContext().bank().open()) {
            Time.sleep(5000, () -> script.getAPIContext().bank().isOpen());
        }

        if(userSettings.getActionType().equals(ActionType.CUT_BOWS)) {
            if(script.getAPIContext().bank().depositAll(item -> !item.getName().equals(AutoFletcherPro.KNIFE_STRING))) {
                if (!script.getAPIContext().inventory().contains(AutoFletcherPro.KNIFE_STRING)) {
                    if (script.getAPIContext().bank().contains(AutoFletcherPro.KNIFE_STRING)) {
                        if (script.getAPIContext().bank().withdraw(1, AutoFletcherPro.KNIFE_STRING)) {
                            Time.sleep(3000, () -> script.getAPIContext().inventory().contains(AutoFletcherPro.KNIFE_STRING));
                        }
                    } else {
                        script.stop("No knife in bank or inventory, stopping script.");
                    }
                }
                if(script.getAPIContext().bank().contains(userSettings.getBowType().getLogName())) {
                    if(script.getAPIContext().bank().withdrawAll(userSettings.getBowType().getLogName())) {
                        Time.sleep(3000, () -> script.getAPIContext().inventory().contains(userSettings.getBowType().getLogName()));
                    }
                } else {
                    script.stop("Ran out of items, stopping script");
                }
            }
        } else if(userSettings.getActionType().equals(ActionType.STRING_BOWS)) {
            if(!script.getAPIContext().inventory().onlyContains(AutoFletcherPro.BOWSTRING_STRING, userSettings.getBowType().getUnstrungName())) {
                if(script.getAPIContext().bank().depositInventory()) {
                    Time.sleep(4000, () -> script.getAPIContext().inventory().isEmpty());
                }
            }
            if(!script.getAPIContext().inventory().contains(AutoFletcherPro.BOWSTRING_STRING)) {
                if(script.getAPIContext().bank().contains(AutoFletcherPro.BOWSTRING_STRING)) {
                    if(script.getAPIContext().bank().withdraw(14, AutoFletcherPro.BOWSTRING_STRING)) {
                        Time.sleep(4000, () -> script.getAPIContext().inventory().contains(AutoFletcherPro.BOWSTRING_STRING));
                    }
                } else {
                    script.stop("Ran out of Bow string, stopping script.");
                }
            }
            if(!script.getAPIContext().inventory().contains(userSettings.getBowType().getUnstrungName())) {
                if(script.getAPIContext().bank().contains(userSettings.getBowType().getUnstrungName())) {
                    if(script.getAPIContext().bank().withdraw(14, userSettings.getBowType().getUnstrungName())) {
                        Time.sleep(4000, () -> script.getAPIContext().inventory().contains(userSettings.getBowType().getUnstrungName()));
                    }
                } else {
                    script.stop("Ran out of unstrung bows, stopping script.");
                }
            }
        } else if(userSettings.getActionType().equals(ActionType.MAKE_ARROWS)) {
            if(!script.getAPIContext().inventory().onlyContains(userSettings.getArrowType().getArrowheadName(), userSettings.getArrowType().getCombinationItemName())) {
                if(script.getAPIContext().bank().depositInventory()) {
                    Time.sleep(4000, () -> script.getAPIContext().inventory().isEmpty());
                }
            }
            if(!script.getAPIContext().inventory().contains(userSettings.getArrowType().getArrowheadName())) {
                if (script.getAPIContext().bank().contains(userSettings.getArrowType().getArrowheadName())) {
                    if (script.getAPIContext().bank().withdrawAll(userSettings.getArrowType().getArrowheadName())) {
                        Time.sleep(4000, () -> script.getAPIContext().inventory().contains(userSettings.getArrowType().getArrowheadName()));
                    }
                } else {
                    script.stop("Ran out of arrowheads, stopping script.");
                }
            }
            if(!script.getAPIContext().inventory().contains(userSettings.getArrowType().getCombinationItemName())) {
                if (script.getAPIContext().bank().contains(userSettings.getArrowType().getCombinationItemName())) {
                    if (script.getAPIContext().bank().withdrawAll(userSettings.getArrowType().getCombinationItemName())) {
                        Time.sleep(4000, () -> script.getAPIContext().inventory().contains(userSettings.getArrowType().getCombinationItemName()));
                    }
                } else {
                    script.stop("Ran out of shafts/headless arrows, stopping script.");
                }
            }

        } else if(userSettings.getActionType().equals(ActionType.MAKE_DARTS)){

        }
    }

    @Override
    public Priority priority() {
        return Priority.VERY_LOW;
    }

    @Override
    public String getName() {
        return "Banking...";
    }

}
