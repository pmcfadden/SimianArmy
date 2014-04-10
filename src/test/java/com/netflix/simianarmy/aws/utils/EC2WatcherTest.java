package com.netflix.simianarmy.aws.utils;

import org.testng.annotations.Test;

import java.util.Date;

import static junit.framework.Assert.*;

public class EC2WatcherTest {
    //@Test
    public void shouldWatchCPUUtilization(){
        EC2Watcher watcher = new EC2Watcher();
        String instanceId = "i-1beb9113";
        Date endTime = new Date();
        Date startTime = new Date(endTime.getTime() - 24*60*60*1000);
        int period = 60*5;

        assertTrue(watcher.getAverageCPUUtilization("us-west-2", instanceId, startTime, endTime, period) > 0 );
    }
}
