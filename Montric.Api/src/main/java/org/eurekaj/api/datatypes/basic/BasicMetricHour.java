package org.eurekaj.api.datatypes.basic;

import org.eurekaj.api.datatypes.LiveStatistics;
import org.eurekaj.api.datatypes.LiveStatisticsUtil;
import org.eurekaj.api.enumtypes.UnitType;
import org.eurekaj.api.enumtypes.ValueType;

/**
 * Created with IntelliJ IDEA.
 * User: joahaa
 * Date: 3/3/13
 * Time: 1:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class BasicMetricHour {
    private String guiPath;
    private String accountName;
    private Long hoursSince1970;
    private Double [] metrics;
    private Integer[] measurementCount;
    private String valueType;
    private String unitType;

    public BasicMetricHour() {
    	metrics = new Double[240];
        measurementCount = new Integer[240];
	}
    
    public BasicMetricHour(String guiPath, String accountName, Long hoursSince1970, String valueType, String unitType) {
    	this();
        this.guiPath = guiPath;
        this.accountName = accountName;
        this.hoursSince1970 = hoursSince1970;
        this.valueType = valueType;
        this.unitType = unitType;
        
    }

    public void addStatistic(LiveStatistics liveStatistics) {
    	int fifteenSecondPeriodsSinceStartOfHour = LiveStatisticsUtil.getFifteensecondTimeperiodsSinceStartOfHour(liveStatistics.getTimeperiod() * 15);
        Double calculatedValue = LiveStatisticsUtil.calculateValueBasedOnUnitType(liveStatistics.getValue(), UnitType.fromValue(liveStatistics.getUnitType()));
        
        Double value = metrics[fifteenSecondPeriodsSinceStartOfHour];
        Integer count = measurementCount[fifteenSecondPeriodsSinceStartOfHour];
        
        if (count == null) {
        	count = 0;
        }
        
        if (value == null) {
        	value = calculatedValue;
        	count = 1;
        } else {
        	value = LiveStatisticsUtil.calculateValueBasedOnValueType(value, calculatedValue, ValueType.fromValue(liveStatistics.getValueType()), count);
        	count = count + 1;
        }
        
        if (this.getUnitType() == null) {
        	this.setUnitType(UnitType.MS.value());
        }
        
        if (this.getValueType() == null) {
        	this.setValueType(liveStatistics.getValueType());
        }
        
        metrics[fifteenSecondPeriodsSinceStartOfHour] = value;
        measurementCount[fifteenSecondPeriodsSinceStartOfHour] = count;
    }

    public String getGuiPath() {
        return guiPath;
    }

    public void setGuiPath(String guiPath) {
        this.guiPath = guiPath;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getHoursSince1970() {
        return hoursSince1970;
    }

    public void setHoursSince1970(Long hoursSince1970) {
        this.hoursSince1970 = hoursSince1970;
    }

    public Double[] getMetrics() {
        return metrics;
    }

    public void setMetrics(Double[] metrics) {
        this.metrics = metrics;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}
