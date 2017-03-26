package br.com.rsystem;

import java.util.concurrent.TimeUnit;

import br.com.rsystem.config.ConfigDuration;
import br.com.rsystem.config.defaults.UnitBitValues;

public enum Units {
	YEAR(UnitBitValues.YEAR_MAX_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert((config.getHoursInDay() * config.getDaysInYear()), TimeUnit.HOURS);
		}
	},
	MONTH(UnitBitValues.MONTH_MAX_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert((config.getHoursInDay() * config.getDaysInMonth()), TimeUnit.HOURS);
		}
	},
	WEEK(UnitBitValues.WEEK_MAX_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert((config.getHoursInDay() * config.getDaysInWeek()), TimeUnit.HOURS);
		}
	},
	DAY(UnitBitValues.DAY_MAX_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert(config.getHoursInDay(), TimeUnit.HOURS);
		}
	},
	HOUR(UnitBitValues.HOUR_MAX_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert(1L, TimeUnit.HOURS);
		}
	},
	MINUTE(UnitBitValues.MINUTE_MAX_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert(1L, TimeUnit.MINUTES);
		}
	},
	SECOND(UnitBitValues.SECOND_MAX_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return TimeUnit.MILLISECONDS.convert(1L, TimeUnit.SECONDS);
		}
	},
	MILLISECOND(UnitBitValues.MILLISECOND_MAX_BIT) {
		@Override
		Long getInMillis(final ConfigDuration config) {
			return 1L;
		}
	};
	
	private int valueForMaxUnit;
	
	private Units(int valueForMaxUnit) {
		this.valueForMaxUnit = valueForMaxUnit;
	}
	
	public int valueForMaxUnit() {
		return this.valueForMaxUnit;
	}

	abstract Long getInMillis(ConfigDuration config);
}
