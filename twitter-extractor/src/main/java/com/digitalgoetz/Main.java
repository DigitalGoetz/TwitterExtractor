package com.digitalgoetz;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class Main {

	public static void main(String[] args) throws Exception {

		// SEARCH API Examples:
		Search search = new Search();

		// Performs a best effort search (2500 or as many as available) for
		// positively termed tweets containing the string movies
		List<Status> positiveTweets = search.searchTweets("movies :)", 2500);

		// Performs a best effort search (2500 or as many as available) for
		// negatively termed tweets containing the string movies
		List<Status> negativeTweets = search.searchTweets("movies :(", 5000);

		// Combine positive tweets and negative tweets in a single
		// Collection
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

		// STREAM API Examples:
		Stream stream = new Stream();

		System.out.println("Starting Twitter Stream Listener");
		List<Status> streamTweets = stream.streamTweets("yes", 1000, 1000 * 60 * 1);

		for (Status status : streamTweets) {
			System.out.println("===========================================");
			System.out.println("Username:\t" + status.getUser().getScreenName());
			System.out.println("Text:\t" + status.getText());
		}

	}

}
