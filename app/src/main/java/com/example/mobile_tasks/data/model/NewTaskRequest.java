package com.example.mobile_tasks.data.model;

public class NewTaskRequest {
    private String sender;
    private String reciver;
    private String task_tittle;
    private String task_details;

    public String getSender() {
        return sender;
    }

    public String getReciver() {
        return reciver;
    }

    public String getTask_tittle() {
        return task_tittle;
    }

    public String getTask_details() {
        return task_details;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public void setTask_tittle(String task_tittle) {
        this.task_tittle = task_tittle;
    }

    public void setTask_details(String task_details) {
        this.task_details = task_details;
    }
}
