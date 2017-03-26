package br.com.rsystem.config.defaults;

public class UnitBitValues {
	public static final int YEAR_BIT        = 128;
	public static final int MONTH_BIT       =  64;
	public static final int WEEK_BIT        =  32;
	public static final int DAY_BIT         =  16;
	public static final int HOUR_BIT        =   8;
	public static final int MINUTE_BIT      =   4;
	public static final int SECOND_BIT      =   2;
	public static final int MILLISECOND_BIT =   1;
	
	public static final int YEAR_MAX_BIT        = MILLISECOND_BIT | SECOND_BIT | MINUTE_BIT | HOUR_BIT | DAY_BIT | WEEK_BIT | MONTH_BIT | YEAR_BIT;
	public static final int MONTH_MAX_BIT       = MILLISECOND_BIT | SECOND_BIT | MINUTE_BIT | HOUR_BIT | DAY_BIT | WEEK_BIT | MONTH_BIT;
	public static final int WEEK_MAX_BIT        = MILLISECOND_BIT | SECOND_BIT | MINUTE_BIT | HOUR_BIT | DAY_BIT | WEEK_BIT;
	public static final int DAY_MAX_BIT         = MILLISECOND_BIT | SECOND_BIT | MINUTE_BIT | HOUR_BIT | DAY_BIT;
	public static final int HOUR_MAX_BIT        = MILLISECOND_BIT | SECOND_BIT | MINUTE_BIT | HOUR_BIT;
	public static final int MINUTE_MAX_BIT      = MILLISECOND_BIT | SECOND_BIT | MINUTE_BIT;
	public static final int SECOND_MAX_BIT      = MILLISECOND_BIT | SECOND_BIT;
	public static final int MILLISECOND_MAX_BIT = MILLISECOND_BIT;
}
