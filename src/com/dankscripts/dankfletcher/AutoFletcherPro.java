package com.dankscripts.dankfletcher;



import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.event.ChatMessageEvent;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.paint.frame.PaintFrame;
import com.dankscripts.api.framework.Controller;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Task;
import com.dankscripts.api.utils.Calculations;
import com.dankscripts.api.utils.Timer;
import com.dankscripts.dankfletcher.gui.ProgressiveGUI;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

@ScriptManifest(name = "Dank Fletcher", gameType = GameType.OS)
public class AutoFletcherPro extends LoopScript {

    private Controller controller;
    private ProgressiveGUI progressiveGUI;
    private List<Task> taskList = new LinkedList<>();
    private int taskNumber, actionsPerformed, startExp = 0;
    private PaintFrame paintFrame;
    private long startTime;

    public static final String KNIFE_STRING = "Knife";
    public static final String BOWSTRING_STRING = "Bow string";

    @Override
    public boolean onStart(String... strings) {
        progressiveGUI = new ProgressiveGUI(this);
        progressiveGUI.start();
        controller = new Controller();
        startTime = System.currentTimeMillis();
        startExp = getAPIContext().skills().fletching().getExperience();
        return true;
    }

    @Override
    public void onChatMessage(ChatMessageEvent m) {
            if(m.getMessage().getText().contains("cut") || m.getMessage().getText().contains("string")) {
                actionsPerformed++;
            } else if(m.getMessage().getText().contains("You attach")) {
                actionsPerformed += 15;
            } else if(m.getMessage().getText().contains("You fletch")) {
                actionsPerformed += 10;
            }
    }

    @Override
    protected int loop() {
        if(progressiveGUI.isVisible()) {
            return 500;
        }
        if (taskList.size() > 0) {
            if (!taskList.get(taskNumber).getTerminateCondition().getAsBoolean()) {
                Node n = controller.getCurrentNode();
                if (n != null) {
                    n.execute();
                }
            } else {
                taskNumber++;
                actionsPerformed = 0;
                if (taskNumber < taskList.size()) {
                    controller.clearNodes();
                    addNodes();
                } else {
                    stop("Finished tasks, stopping script");
                }
            }
        }
        return 50;
    }

    private String currentNode() {
        if(controller.getCurrentNode() != null) {
            return controller.getCurrentNode().getName();
        }
        return "Waiting...";
    }

    @Override
    protected void onPaint(Graphics2D g, APIContext a2) {
        if(taskList.size() > 0) {
            int xpGained = a2.skills().fletching().getExperience() - startExp;
            long timeElapsed = System.currentTimeMillis() - startTime;
            paintFrame = new PaintFrame("Auto Fletcher Pro");
            paintFrame.addLine("Time Running: ", Timer.formatTime(timeElapsed));
            paintFrame.addLine("XP Gained: ", xpGained);
            paintFrame.addLine("XP/Hour: ", Calculations.getHourly(xpGained, timeElapsed));
            paintFrame.addLine("Item: ", taskList.get(taskNumber).toString());
            paintFrame.addLine("Status: ", currentNode());
            paintFrame.draw(g, 10, 100, a2);
        }
    }

    public void addNodes() {
        for (int i = 0; i < taskList.get(taskNumber).getNodeList().size(); i++) {
            controller.addNodes(taskList.get(taskNumber).getNodeList().get(i));
        }
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public int getActionsPerformed() {
        return actionsPerformed;
    }

}
