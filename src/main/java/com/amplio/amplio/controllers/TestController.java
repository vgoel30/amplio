package com.amplio.amplio.controllers;

import com.amplio.amplio.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

//    @Autowired
//    private AdvertisementDao advertisementDao;
//
//    @Autowired
//    private AdvertiserDao advertiserDao;
//
//    @Autowired
//    private AlbumDao albumDao;
//
//    @Autowired
//    private ArtistDao artistDao;
//
//    @Autowired
//    private ConcertDao concertDao;
//
//    @Autowired
//    private LabelDao labelDao;
//
//    @Autowired
//    private PlaylistDao playlistDao;
//
//    @Autowired
//    private SongDao songDao;
//
//    @Autowired
//    private SongQueueDao songQueueDao;
//
//    @Autowired
//    private StatisticsDao statisticsDao;
//
//    @Autowired
//    private UserDao userDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getMessage() {
        return new ResponseEntity<String>("Message", HttpStatus.OK);
    }

}
