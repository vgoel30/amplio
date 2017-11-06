package com.amplio.amplio;

import com.amplio.amplio.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmplioApplication {

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private AdvertiserDao advertiserDao;

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private ArtistDao artistDao;

    @Autowired
    private ConcertDao concertDao;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private PlaylistDao playlistDao;

    @Autowired
    private SongDao songDao;

    @Autowired
    private SongQueueDao songQueueDao;

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private UserDao userDao;

    public static void main(String[] args) {
		SpringApplication.run(AmplioApplication.class, args);
	}
}
