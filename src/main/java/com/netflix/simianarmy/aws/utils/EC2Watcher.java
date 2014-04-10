package com.netflix.simianarmy.aws.utils;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EC2Watcher {
    public double getAverageCPUUtilization(String regionName, String instanceId, Date startTime, Date endTime, int period) {

        Region region = Region.getRegion(Regions.fromName(regionName));
        AmazonCloudWatchClient cloudWatch = new AmazonCloudWatchClient();
        cloudWatch.setRegion(region);
        GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
                .withStartTime(startTime)
                .withNamespace("AWS/EC2")
                .withPeriod(period)
                .withMetricName("CPUUtilization")
                .withStatistics("Average")
                .withDimensions(new Dimension().withName("InstanceId").withValue(instanceId))
                .withEndTime(endTime);
        GetMetricStatisticsResult result = cloudWatch.getMetricStatistics(request);
        double avgCPUUtilization = 0;
        List dataPoint = result.getDatapoints();
        for (Object aDataPoint : dataPoint) {
            Datapoint dp = (Datapoint) aDataPoint;
            Double average = dp.getAverage();
            System.out.println(average);
            avgCPUUtilization += average;
        }

        return avgCPUUtilization/dataPoint.size();
    }
}
