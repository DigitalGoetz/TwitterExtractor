package com.digitalgoetz;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class Stream {
	// Twitter4J core object
	private TwitterStream twitterStream;

	// Parameterized Constructor
	public Stream(TwitterStream instance) {
		twitterStream = instance;
	}

	// Default constructor
	public Stream() {
		twitterStream = TwitterStreamFactory.getSingleton();
	}

	public List<Status> streamTweets(String queryString, int total, int durationInMilliseconds) throws Exception {
		List<Status> tweets = new ArrayList<>();
		Date startTime = new Date();
		twitterStream.addListener(new StreamListener(queryString, tweets, startTime, durationInMilliseconds));

		long endTime = startTime.getTime() + durationInMilliseconds;

		twitterStream.sample();

		while (new Date().getTime() <= endTime) {
			if (tweets.size() >= total) {
				break;
			}
		}

		twitterStream.shutdown();

		return tweets;
	}

	private class StreamListener implements StatusListener {

		List<Status> tweets;
		String queryString;
		Date startTime;
		long duration;
		long count = 0;
		long encountered = 0;

		DecimalFormat format = new DecimalFormat("#.##");

		public StreamListener(String queryString, List<Status> tweets, Date startTime, long duration) {
			this.tweets = tweets;
			this.queryString = queryString;
			this.startTime = startTime;
			this.duration = duration;
		}

		@Override
		public void onException(Exception ex) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatus(Status status) {
			Date currentTime = new Date();
			encountered++;
			if (status.getText().contains(queryString)) {
				count++;
				tweets.add(status);
			}

			long elapsed = currentTime.getTime() - startTime.getTime();

			double remaining = (elapsed * 1.0) / (duration * 1.0);
			Double percentage = remaining * 100.0;
			System.out.println("Tweets encountered: " + encountered + "\t Tweets Matched: " + count + "\t"
					+ format.format(percentage) + "% remaining");
		}

		@Override
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onScrubGeo(long userId, long upToStatusId) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStallWarning(StallWarning warning) {
			// TODO Auto-generated method stub

		}

	}
}
