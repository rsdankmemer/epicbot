package com.dankscripts.dankwoodcutter;

import com.dankscripts.api.utils.Calculations;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.event.ChatMessageEvent;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.dankscripts.api.framework.Controller;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Task;
import com.dankscripts.api.utils.Painter;
import com.dankscripts.dankwoodcutter.gui.ProgressiveGUI;
import com.dankscripts.dankwoodcutter.threads.CameraThread;
import com.epicbot.api.shared.util.time.Time;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

@ScriptManifest(name = "Dank Woodcutter", gameType = GameType.OS)
public class DankWoodcutter extends LoopScript {

    private Controller controller;
    private ProgressiveGUI gui;
    private int startExp;
    private List<Task> taskList = new LinkedList<>();
    private int taskNumber, numberOfLogsObtained = 0;
    private Painter painter;
    private Painter.PaintProperty time, node, level, xpGained, xpToLvl, logsChopped;
    private long startTime, elapsedTime;
    private CameraThread cameraThread;
    private boolean justDropped = false;

    @Override
    protected void onChatMessage(ChatMessageEvent a) {
        if(a.getMessage().getText().contains("You get some")) {
            numberOfLogsObtained++;
        }
    }

    @Override
    protected void onPaint(Graphics2D a, APIContext a2) {
        elapsedTime = System.currentTimeMillis() - startTime;
        int expGained = a2.skills().woodcutting().getExperience() - startExp;
        painter.properties(
                time.value("Time Running: " + painter.formatTime(elapsedTime)),
                node.value("Node: " + getStatus()),
                level.value("Level: " + a2.skills().woodcutting().getRealLevel()),
                xpGained.value("Exp Gained: " + expGained + " (" + Calculations.getHourly(expGained, elapsedTime) + ")"),
                xpToLvl.value("Exp to Lvl: " + a2.skills().woodcutting().getExperienceToNextLevel()),
                logsChopped.value("Logs Chopped: " + numberOfLogsObtained)
        ).draw(a);

    }

    @Override
    protected int loop() {
        if (gui.isVisible()) {
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
                numberOfLogsObtained = 0;
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

    @Override
    protected void onStop() {
        //cameraThread.setScriptExited(true);
    }

    @Override
    public boolean onStart(String... strings) {
        controller = new Controller();
        painter = new Painter("Dank Woodcutter", "1.0", new Color(43, 43, 43), new Color(145, 213, 255));
        time = new Painter.PaintProperty();
        node = new Painter.PaintProperty();
        level = new Painter.PaintProperty();
        xpGained = new Painter.PaintProperty();
        xpToLvl = new Painter.PaintProperty();
        logsChopped = new Painter.PaintProperty();
        startExp = getAPIContext().skills().woodcutting().getExperience();
        gui = new ProgressiveGUI(this);
        gui.open();
        startTime = System.currentTimeMillis();
        //cameraThread = new CameraThread(this);
        //cameraThread.start();
        return true;
    }

    public void addNodes() {
        for (int i = 0; i < taskList.get(taskNumber).getNodeList().size(); i++) {
            controller.addNodes(taskList.get(taskNumber).getNodeList().get(i));
        }
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public int getNumberOfLogsObtained() {
        return numberOfLogsObtained;
    }

    private String getStatus() {
        if(controller.getCurrentNode() != null) {
            return controller.getCurrentNode().getName();
        }
        return "Waiting...";
    }

    public boolean isJustDropped() {
        return justDropped;
    }

    public void setJustDropped(boolean justDropped) {
        this.justDropped = justDropped;
    }
}
