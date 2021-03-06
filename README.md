# operator-air-humidity

Takes temperature and humidity readings from an inside and an outside sensor and calculates the theoretic
humidity after airing the room.

## Inputs

* insideTemp (float): Reading from an inside temperature sensor
* insideHumidity (float): Reading from an inside humidity (rel.) sensor
* outsideTemp (float): Reading from an outside temperature sensor
* outsideHumidity (float): Reading from an outside humidity (rel.) sensor

## Outputs

* humidityAfterAir (float): Theoretic rel. humidity of the air after complete change of air in the room and
reheating/cooling the air to inside temperature
* insideHumidity (float): As read

## Configs

* units (String): Either 'metric' or 'imperial', should match input data, default: 'metric'
