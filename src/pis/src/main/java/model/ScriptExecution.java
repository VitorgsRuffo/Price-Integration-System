/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author vitor
 */
public class ScriptExecution {
    private int storeName;
    private int scriptVersionNum;
    private Date date;
    private Time time;

    public int getStoreName() {
        return storeName;
    }

    public void setStoreName(int storeName) {
        this.storeName = storeName;
    }

    public int getScriptVersionNum() {
        return scriptVersionNum;
    }

    public void setScriptVersionNum(int scriptVersionNum) {
        this.scriptVersionNum = scriptVersionNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}