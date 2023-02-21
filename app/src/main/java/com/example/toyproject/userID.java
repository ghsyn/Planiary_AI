package com.example.toyproject;

public enum userID {
        INSTANCE;

        private String userId;

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }
    }

