package com.example.mycourseratingapp.model;

public class Rating {

    private String lessonId;
    private Float q1;
    private Float q2;
    private Float q3;
    private Float q4;
    private Float q5;

    public Rating() {
    }

    public Rating(String lessonId, Float q1, Float q2, Float q3, Float q4, Float q5) {
        this.lessonId = lessonId;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
    }

    public String getLessonId() {
        return lessonId;
    }

    public Float getQ1() {
        return q1;
    }

    public Float getQ2() {
        return q2;
    }

    public Float getQ3() {
        return q3;
    }

    public Float getQ4() {
        return q4;
    }

    public Float getQ5() {
        return q5;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "lessonId='" + lessonId + '\'' +
                ", q1=" + q1 +
                ", q2=" + q2 +
                ", q3=" + q3 +
                ", q4=" + q4 +
                ", q5=" + q5 +
                '}';
    }
}

