package com.gdsc.solutionChallenge.posts;

public enum Score {
    post(10),
    like(1),
    dislike(-1);

    private final Integer score;

    Score(Integer score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }
}
