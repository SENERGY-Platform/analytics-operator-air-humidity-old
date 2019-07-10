/*
 * Copyright 2018 InfAI (CC SES)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.infai.seits.sepl.operators.Config;
import org.infai.seits.sepl.operators.Message;
import org.infai.seits.sepl.operators.OperatorInterface;

import static java.lang.Math.exp;

public class AirHumidity implements OperatorInterface {

    boolean needsConversion;

    public AirHumidity(){
        String units = new Config().getConfigValue("units", "metric");
        needsConversion = units.equalsIgnoreCase("imperial");
    }

    @Override
    public void run(Message message) {
        double tempInside = message.getInput("insideTemp").getValue();
        double humidityInside = message.getInput("insideHumidity").getValue();
        double tempOutside = message.getInput("outsideTemp").getValue();
        double humidityOutside = message.getInput("outsideHumidity").getValue();

        if(needsConversion) {
            tempInside = metric(tempInside);
            tempOutside = metric(tempOutside);
        }

        if(humidityInside > 1){
            //Expect reading in range 0-100 instead 0-1
            humidityInside /= 100;
        }
        if(humidityOutside > 1) {
            //Expect reading in range 0-100 instead 0-1
            humidityOutside /= 100;
        }

        double e_satInside = e_sat(tempInside);
        double e_satOutside = e_sat(tempOutside);

        double eInside = e(humidityInside, e_satInside);
        double eOutside = e(humidityOutside, e_satOutside);

        double p_dInside = p_d(eInside, tempInside);
        double p_dOutside = p_d(eOutside, tempOutside);

        double p_dExpected = p_d(eOutside, tempInside);
        double p_d_maxInside = p_d(e_satInside, tempInside);

        double expected = p_dExpected / p_d_maxInside;

        message.output("humidityAfterAir", expected);
        boolean isLower = p_dOutside < p_dInside;
        message.output("isLower", "" + isLower);
    }

    @Override
    public void config(Message message) {
        message.addInput("insideTemp");
        message.addInput("insideHumidity");
        message.addInput("outsideTemp");
        message.addInput("outsideHumidity");
    }

    protected double e_sat(double temp) {
        return 611200 * exp((17.62 * temp) / (243.12 + temp));
    }

    protected double e(double humidity, double e_sat) {
        return humidity * e_sat;
    }

    protected double p_d(double e, double temp){
        return e / (461.51 * (temp + 273.15));
    }

    protected double metric(double imperial) {
        return (imperial - 32) * 5 / 9;
    }

}
