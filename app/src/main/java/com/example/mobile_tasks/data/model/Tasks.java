package com.example.mobile_tasks.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tasks {

        @SerializedName("task_id")
        @Expose
        private String taskId;
        @SerializedName("task_tittle")
        @Expose
        private String taskTittle;
        @SerializedName("task_details")
        @Expose
        private String taskDetails;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("surname")
        @Expose
        private String surname;
        @SerializedName("user_image")
        @Expose
        private String userImage;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getTaskTittle() {
            return taskTittle;
        }

        public void setTaskTittle(String taskTittle) {
            this.taskTittle = taskTittle;
        }

        public String getTaskDetails() {
            return taskDetails;
        }

        public void setTaskDetails(String taskDetails) {
            this.taskDetails = taskDetails;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }
}
