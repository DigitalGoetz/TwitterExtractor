package com.digitalgoetz;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class Main {

	public static void main(String[] args) throws Exception {
		Extractor extractor = new Extractor();

		// Performs a best effort search (2500 or as many as available) for
		// positively termed tweets containing the string movies
		List<Status> positiveTweets = extractor.getTweets("movies :)", 2500);

		// Performs a best effort search (2500 or as many as available) for
		// negatively termed tweets containing the string movies
		List<Status> negativeTweets = extractor.getTweets("movies :(", 5000);

		// Combine positive tweets and negative tweets in a single Collection
		List<Status> tweets = new ArrayList<>();
		tweets.addAll(positiveTweets);
		tweets.addAll(negativeTweets);

		// for each Tweet (Status object) perform desired processing to
		// determine Classification
		for (Status status : tweets) {
			System.out.println("===========================================");
			System.out.println("Username:\t" + status.getUser().getScreenName());
			System.out.println("Text:\t" + status.getText());
		}
	}

}
