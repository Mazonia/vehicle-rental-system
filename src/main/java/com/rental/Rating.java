package com.rental;

public class Rating {
    private final int score;
    private final String comment;
    private final String raterName;

    public Rating(int score, String comment, String raterName) {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.score = score;
        this.comment = comment;
        this.raterName = raterName;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public String getRaterName() {
        return raterName;
    }
} 