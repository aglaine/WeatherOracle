package weatherOracle.forecastData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

/**
 * A single data-point of forecast data for the forecast at a given time and location
 * 
 */
public class ForecastData {
	// TODO : include everything, verify units, add constructor, add accessors
	private double temperature_hourly; // in degrees F
	private double temperature_dewPoint; // in degrees F
	private double probabilityOfPrecipitation; // percent
	private double windSpeed_sustained; // mph?
	private double windSpeed_gust; // mph?
	private double windSpeed_direction; // in degrees true
	private double cloudAmount; // in percent
	private double humidity_relative; // in percent
	private double hourly_qpf; // inches 
		
	// Time
	private Calendar dateTime;
	
	/**
	 * 
	 * Constructor for a new ForecastData object, takes a billion parameters and stores everything in the structure
	 */
	public ForecastData(
			Calendar dateTime,
			double temperature_hourly,
			double temperature_dewPoint,
			double probabilityOfPrecipitation,
			double windSpeed_sustained,
			double windSpeed_gust,
			double windSpeed_direction,
			double cloudAmount,
			double humidity_relative,
			double hourly_qpf){
		this.dateTime=dateTime;
		this.temperature_hourly=temperature_hourly;
		this.temperature_dewPoint=temperature_dewPoint;
		this.probabilityOfPrecipitation=probabilityOfPrecipitation;
		this.windSpeed_sustained=windSpeed_sustained;
		this.windSpeed_gust=windSpeed_gust;
		this.windSpeed_direction=windSpeed_direction;
		this.cloudAmount=cloudAmount;
		this.humidity_relative=humidity_relative;
		this.hourly_qpf=hourly_qpf;
	}
	
	/**
	 * 
	 * @return the current temperature
	 */
	public double getTemperature() {
		return this.temperature_hourly;
	}
	
	/**
	 * 
	 * @return the current dew point
	 */
	public double getDewpoint() {
		return this.temperature_dewPoint;
	}
	
	/**
	 * 
	 * @return the probability of precipitation
	 */
	public double getProbPrecipitation() {
		return this.probabilityOfPrecipitation;
	}
	
	/**
	 * 
	 * @return the amount of cloud cover
	 */
	public double getCloudCover() {
		return this.cloudAmount;
	}
	
	/**
	 * 
	 * @return the amount of relative humidity
	 */
	public double getHumidity() {
		return this.humidity_relative;
	}
	
	/**
	 * 
	 * @return the day of the week
	 */
	public int getDayOfWeek() {
		return dateTime.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 
	 * @return the time in milliseconds
	 */
	public long getMillisTime(){
		return dateTime.getTimeInMillis();
	}
	
	/**
	 * 
	 * @return the time zone
	 */
	public TimeZone getTimeZone() {
		return dateTime.getTimeZone();
	}
	
	/**
	 * 
	 * @return a String representation of the Time
	 */
	public String getTimeString(){
		long standardOffset = dateTime.getTimeZone().getRawOffset();
		String zone = "";
		if (standardOffset == -36000000) {
			zone = "HA";
		} else if (standardOffset == -28800000) {
			zone = "AK";
		} else if (standardOffset == -25200000) {
			zone = "PT";
		} else if (standardOffset == -21600000) {
			zone = "MT";
		} else if (standardOffset == -18000000) {
			zone = "CT";
		} else if (standardOffset == -14400000) {
			zone = "ET";
		}
		
		SimpleDateFormat f=new SimpleDateFormat("EEE MM/dd hh a");
		f.setTimeZone(dateTime.getTimeZone());
		Date d=dateTime.getTime();
		String s=d.toLocaleString();;
		s=f.format(d);
		return s + " " + zone;
	}
	
	/**
	 * 
	 * @return the sustained wind speed
	 */
	public double getSustainedWindSpeed() {
		return this.windSpeed_sustained;
	}
	
	/**
	 * 
	 * @return the gust wind speed
	 */
	public double getGustWindSpeed() {
		return Math.max(this.windSpeed_gust,this.windSpeed_sustained);
	}
	
	/**
	 * 
	 * @return the quantatative precipitation forecast
	 */
	public double getQPF() {
		return this.hourly_qpf;
	}
}
