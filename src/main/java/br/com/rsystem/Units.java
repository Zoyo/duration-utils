package br.com.rsystem;

import java.util.concurrent.TimeUnit;

import br.com.rsystem.config.ConfigDuration;
import br.com.rsystem.config.defaults.UnitBitValues;

public enum Units {
	YEAR(UnitBitValues.YEAR_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert((config.getHoursInDay() * config.getDaysInYear()), TimeUnit.HOURS);
		}
	},
	MONTH(UnitBitValues.MONTH_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert((config.getHoursInDay() * config.getDaysInMonth()), TimeUnit.HOURS);
		}
	},
	WEEK(UnitBitValues.WEEK_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert((config.getHoursInDay() * config.getDaysInWeek()), TimeUnit.HOURS);
		}
	},
	DAY(UnitBitValues.DAY_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert(config.getHoursInDay(), TimeUnit.HOURS);
		}
	},
	HOUR(UnitBitValues.HOUR_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert(1L, TimeUnit.HOURS);
		}
	},
	MINUTE(UnitBitValues.MINUTE_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert(1L, TimeUnit.MINUTES);
		}
	},
	SECOND(UnitBitValues.SECOND_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert(1L, TimeUnit.SECONDS);
		}
	},
	MILLISECOND(UnitBitValues.MILLISECOND_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return 1L;
		}
	};
	
	private int bitCode;
	
	private Units(int bitCode) {
		this.bitCode = bitCode;
	}
	
	public int bitCode() {
		return this.bitCode;
	}

	abstract Long getInMillis(ConfigDuration config);
	
	public static Units getFromPredicate(String predicate) {
		if(predicate == null) {
			return null;
		} else {
			predicate = predicate.trim();
		}
		
		for (Units u : values()) {
			if(u.name().equalsIgnoreCase(predicate)
			   || (u.getDeclaringClass().getName() + "." + u.name()).equalsIgnoreCase(predicate)
			   || String.valueOf(u.bitCode()).equalsIgnoreCase(predicate)) {
				
				return u;
			}
		}
		
		return null;
	}
}
