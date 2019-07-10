import org.infai.seits.sepl.operators.Message;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AirHumidityTest {

    @Test
    public void run() throws Exception{
        AirHumidity airHumidity = new AirHumidity();
        List<Message> messages = TestMessageProvider.getTestMesssagesSet();
        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            airHumidity.config(m);
            airHumidity.run(m);

            m.addInput("expected");
            double expected = m.getInput("expected").getValue();
            double actual = Double.parseDouble(m.getMessageString().split("humidity-after-air\":")[1].split(",")[0]);
            Assert.assertEquals(expected, actual, 0.02);

            double oldHumidity = m.getInput("inside-humidity").getValue();
            if(oldHumidity > 1){
                //Expect reading in range 0-100 instead 0-1
                oldHumidity /= 100;
            }
            boolean isLowerDetected = Boolean.parseBoolean(m.getMessageString().split("isLower\":\"")[1].split("\"")[0]);
            Assert.assertTrue(isLowerDetected == expected < oldHumidity);
        }

    }

}
