import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteCalculatorTest extends TestCase {
    List<Station> route;
    StationIndex stationIndex;

    RouteCalculator calculator;

    Line line1 = new Line(1, "I");
    Line line2 = new Line(2, "II");
    Line line3 = new Line(3, "III");

    Station stationA = new Station("A", line1);
    Station stationC = new Station("C", line1);
    Station stationH = new Station("H", line2);
    Station stationJ = new Station("J", line2);
    Station stationO = new Station("O", line3);
    Station stationP = new Station("P", line3);

    @Override
    protected void setUp() throws Exception {
        route = new ArrayList<>();
        stationIndex = new StationIndex();

        line1.addStation(stationA);
        line1.addStation(stationC);
        line2.addStation(stationH);
        line2.addStation(stationJ);
        line3.addStation(stationO);
        line3.addStation(stationP);

        stationIndex.addStation(stationA);
        stationIndex.addStation(stationC);
        stationIndex.addStation(stationH);
        stationIndex.addStation(stationJ);
        stationIndex.addStation(stationO);
        stationIndex.addStation(stationP);

        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        stationIndex.addConnection(new ArrayList<>(Arrays.asList(stationC, stationH)));
        stationIndex.addConnection(new ArrayList<>(Arrays.asList(stationP, stationJ)));

        route.add(stationA);
        route.add(stationC);
        route.add(stationH);
        route.add(stationJ);
        route.add(stationP);
        route.add(stationO);

        calculator = new RouteCalculator(stationIndex);

    }

    public void testGetShortestRoute(){
        List<Station> actualShortRoute = calculator.getShortestRoute(stationIndex.getStation("A"),
                stationIndex.getStation("C"));
        List<Station> expectedShortRoute = Arrays.asList(stationA, stationC);

        List<Station> actualWithOneConnection = calculator.getShortestRoute(stationIndex.getStation("A"),
                stationIndex.getStation("J"));
        List<Station> expectedWithOneConnection = Arrays.asList(stationA, stationC, stationH, stationJ);

        List<Station> actualWithOTwoConnection = calculator.getShortestRoute(stationIndex.getStation("A"),
                stationIndex.getStation("P"));
        List<Station> expectedWithTwoConnection = Arrays.asList(stationA, stationC, stationH, stationJ, stationP);

        assertEquals(expectedShortRoute,actualShortRoute);
        assertEquals(expectedWithOneConnection, actualWithOneConnection);
        assertEquals(expectedWithTwoConnection, actualWithOTwoConnection);
    }

    public void testCalculateDuration () {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 14.5;
        assertEquals(expected, actual);
    }
}
