# operator-air-humidity

Takes temperature and humidity readings from an inside and an outside sensor and calculates the theoretic
humidity after airing the room.

## Inputs

* inside-temp (float): Reading from an inside temperature sensor
* inside-humidity (float): Reading from an inside humidity (rel.) sensor
* outside-temp (float): Reading from an outside temperature sensor
* outside-humidity (float): Reading from an outside humidity (rel.) sensor

## Outputs

* humidityAfterAir (float): Theoretic rel. humidity of the air after complete change of air in the room and
reheating/cooling the air to inside temperature
* isLower (String): 'true', if the calculated humidity-after-air is lower than the current inside humidity. 'false' otherwise

## Configs

* units (String): Either 'metric' or 'imperial', should match input data, default: 'metric'
