package com.digitalgoetz;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class Search {
	// Twitter4J core object
	private Twitter twitter;

	// Parameterized Constructor
	public Search(Twitter instance) {
		twitter = instance;
	}

	// Default constructor
	public Search() {
		twitter = TwitterFactory.getSingleton();
	}

	public List<Status> searchTweets(String queryString, int maxTweets) throws Exception {
		List<Status> tweets = new ArrayList<>();

		long lowestId = Long.MAX_VALUE;
		long tweetCount = -1;

		// Generates the query object, avoiding retweets
		Query query = new Query(queryString + " -RT");
		// sets the query to obtain the maximum possible quantity per search
		// request
		query.setCount(100);

		while (tweets.size() < maxTweets) {

			if (lowestId != Long.MAX_VALUE) {
				// ensures that our search can only obtain tweets below the
				// current lowest ID to avoid duplicates
				query.setMaxId(lowestId);
			}

			// performs a search API request
			List<Status> statusList = twitter.search(query).getTweets();

			// Find the lowest ID value in the returned batch of tweets
			for (Status status : statusList) {
				if (status.getId() < lowestId) {
					lowestId = status.getId();
				}
			}

			// Decrement the lowest ID so that the next search API request gets
			// tweets excluding the lowest currently found
			lowestId--;

			// If the current search API request has not found additional
			// tweets, exit this loop
			if (tweetCount == tweets.size()) {
				break;
			}

			// Update the current tweetCount
			tweetCount = tweets.size();
		}
		return tweets;
	}
}
