package com.digitalgoetz;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class Extractor {

	// Twitter4J core object
	private Twitter twitter;

	// Parameterized Constructor
	public Extractor(Twitter instance) {
		twitter = instance;
	}
	
	// Default constructor
	public Extractor(){
		twitter = TwitterFactory.getSingleton();
	}

	public List<Status> getTweets(String queryString, int total) throws Exception {
		List<Status> tweets = new ArrayList<>();
		
		long lowestId = Long.MAX_VALUE;

		Query query = new Query(queryString + " -RT");
		query.setCount(100);

		long lastSize = -1;

		while (tweets.size() < total) {

			if (lowestId != Long.MAX_VALUE) {
				query.setMaxId(lowestId);
			}

			QueryResult search = twitter.search(query);
			List<Status> statusList = search.getTweets();

			for (Status status : statusList) {
				if (status.getId() < lowestId) {
					lowestId = status.getId();
				}
			}
			lowestId--;

			if (lastSize == tweets.size()) {
				break;
			}
			lastSize = tweets.size();
		}
		return tweets;
	}
}

