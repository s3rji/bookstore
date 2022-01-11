package com.example.MyBookShopApp.dto;

import java.util.Map;

public class BookRatingTo {
    private int votes;

    private short averageRating;

    private Map<Short, Long> votesByRating;

    public BookRatingTo(int votes, short averageRating, Map<Short, Long> votesByRating) {
        this.votes = votes;
        this.averageRating = averageRating;
        this.votesByRating = votesByRating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public short getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(short averageRating) {
        this.averageRating = averageRating;
    }

    public Long getVotesByRating(Short rating) {
        return votesByRating.get(rating);
    }

    public void setVotesByRating(Map<Short, Long> votesByRating) {
        this.votesByRating = Map.copyOf(votesByRating);
    }
}
