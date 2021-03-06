package weatherOracle.filter;

import java.util.HashMap;
import java.util.Map;

import android.util.Pair;
import weatherOracle.forecastData.ForecastData;

/**
 * A Rule subclass that keeps track of a single condition and the min and max values associated with
 * that Rule.
 */
public class ConditionRule implements Rule {
	private static final long serialVersionUID = 2695287363381416690L;
	
	/**
	 * All possible conditions for ConditionRules
	 */
	public static final String[] conditions = new String[]{"Temperature", "Dewpoint", "Gust Wind Speed", "Sustained Wind Speed", "Cloud Cover",
													"Precipitation Percent", "Precipitation Amount", "Humidity"};

	/**
	 * The units for the different weather conditions
	 */
	public static final Map<String, String> units = new HashMap<String, String>() {
		private static final long serialVersionUID = -8447558203204357059L;
		{
		put("Temperature", "\u00b0F");
		put("Dewpoint", "\u00b0F");
		put("Gust Wind Speed", "mph");
		put("Sustained Wind Speed","mph");
		put("Cloud Cover", "%");
		put("Precipitation Percent", "%");
		put("Precipitation Amount", " 100ths (in)");
		put("Humidity", "%");
		}
	};
	
	/**
	 * The bounds for values for the different weather conditions
	 */
	public static final Map<String, Pair<Integer, Integer>> bounds = new HashMap<String, Pair<Integer, Integer>>() {
		private static final long serialVersionUID = -2443552375204329459L;
		{
			put("Temperature", new Pair<Integer, Integer>(-150, 200));
			put("Dewpoint", new Pair<Integer, Integer>(-150, 200));
			put("Gust Wind Speed", new Pair<Integer, Integer>(0, 300));
			put("Sustained Wind Speed", new Pair<Integer, Integer>(0, 300));
			put("Cloud Cover", new Pair<Integer, Integer>(0, 100));
			put("Precipitation Percent", new Pair<Integer, Integer>(0, 100));
			put("Precipitation Amount", new Pair<Integer, Integer>(0, 1500));
			put("Humidity", new Pair<Integer, Integer>(0, 100));
		}
	};
	
	/**
	 * the portions of the urls for each weather condition when displaying tabular forecast
	 */
	public static final Map<String, String> urlSpecifiers = new HashMap<String, String>() {
		private static final long serialVersionUID = -7067679511309105567L;
		{
			put("Temperature", "w0=t");
			put("Dewpoint", "w1=td");
			put("Gust Wind Speed", "w3=sfcwind");
			put("Sustained Wind Speed", "w3=sfcwind");
			put("Cloud Cover", "w4=sky");
			put("Precipitation Percent", "w5=pop");
			put("Precipitation Amount", "w8=rain");
			put("Humidity", "w6=rh");
		}
	};

	
	/**
	 * The condition of this ConditionRule
	 */
	private String condition;
	
	/**
	 * The minimum value for this ConditionRule
	 */
	private int min;
	
	/**
	 * the maximum value for this ConditionRule
	 */
	private int max;
	
	/**
	 * Default Constructor
	 * @param condition the condition of the Rule
	 * @param min the minimum value for that condition
	 * @param max the maximum value for that condition
	 * @throws IllegalArgumentException if the condition is null or
	 * 			the min is greater than the max
	 */
	public ConditionRule(String condition, int min, int max) {
		if (condition == null || min > max) {
			throw new IllegalArgumentException("Illegal parameters for ConditionRule!");
		} else {
			this.condition = condition;
			this.min = min;
			this.max = max;
		}
	}
	
	/**
	 * Gives the condition associated with this Rule
	 * 
	 * @return the condition String for this Rule
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * Gives the min and max values for this Rule
	 * 
	 * @return a Pair with the min and max
	 */
	public Pair<Integer, Integer> getMinMax() {
		return new Pair<Integer, Integer>(min, max);
	}

	/**
	 * Checks if this ConditionRule is satisfied by the ForecastData
	 * @param data the ForecastData to compare to the Rule
	 */
	public Boolean apply(ForecastData data) {
		if (condition.equals(conditions[0])) {
			// Temperature
			double temp = data.getTemperature();
			return temp >= min && temp <= max;
		} else if (condition.equals(conditions[1])) {
			// Dew point
			double dew = data.getDewpoint();
			return dew >= min && dew <= max;
		} else if (condition.equals(conditions[2])) {
			// Gust wind speed
			double gust = data.getGustWindSpeed();
			return gust > min && gust <= max;
		} else if (condition.equals(conditions[3])) {
			// Sustained wind speed
			double sustained = data.getSustainedWindSpeed();
			return sustained > min && sustained <= max;
		} else if (condition.equals(conditions[4])) {
			// Cloud Cover
			double cover = data.getCloudCover();
			return cover > min && cover <= max;
		} else if (condition.equals(conditions[5])) {
			// Precipitation Percent
			double percent = data.getProbPrecipitation();
			return percent > min && percent <= max;
		} else if (condition.equals(conditions[6])) {
			// QPF
			double qpf = 100.0 * data.getQPF();			// Tenths of an inch
			return qpf > min && qpf <= max;
		} else if (condition.equals(conditions[7])) {
			// Humidity
			double humidity = data.getHumidity();
			return humidity > min && humidity <= max;
		}  
		return Boolean.FALSE;
	}
	
	/**
	 * Gets the unit type for a weather condition
	 * @return the unit type for the given weather condition
	 */
	public static String getUnits(String type) {
		return units.get(type);
	}
	
	/**
	 * Gets the value bounds for a weather condition
	 * @return the bounds for the given weather condition
	 */
	public static Pair<Integer, Integer> getBounds(String type) {
		return bounds.get(type);
	}
	
	/**
	 * Gets the url specifier for a weather condition
	 * @return the url specifier for the given weather condition
	 */
	public static String geturlSpecifier(String type) {
		return urlSpecifiers.get(type);
	}
	
	// Generated hashcode method
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + condition.hashCode();
		result = prime * result + max;
		result = prime * result + min;
		return result;
	}

	// Generated equals method
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConditionRule other = (ConditionRule) obj;
		if (!condition.equals(other.condition))
			return false;
		if (max != other.max)
			return false;
		if (min != other.min)
			return false;
		return true;
	}

	/**
	 * CompareTo method
	 * @param otherRule the other Rule to compare with
	 */
	public int compareTo(Rule otherRule) {
		if (getClass() == otherRule.getClass()) {
			ConditionRule other = (ConditionRule) otherRule;
			if (!condition.equals(other.getCondition())) {
				return condition.compareTo(other.getCondition());
			} else {
				Pair<Integer, Integer> otherP = other.getMinMax();
				if (min != otherP.first) {
					return ((Integer)min).compareTo(otherP.first);
				} else if (max != otherP.second) {
					return ((Integer)max).compareTo(otherP.second);
				}
				return 0;
			}
		} else {
			return 1;
		}
	}
	
	@Override
	public String toString() {
		String condition = this.condition;
		String units = ConditionRule.getUnits(condition);
		condition += " " + this.min + units + " to " + this.max + units ;
		return condition;
	}
}