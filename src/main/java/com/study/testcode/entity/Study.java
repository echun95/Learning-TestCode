package com.study.testcode.entity;

public class Study {
    private StudyStatus status;
//    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;

    public Study(int limit) {
        this.limit = limit;
    }

    public StudyStatus getStatus() {
            return this.status;
    }

    public int getLimit() {
        return limit;
    }
}