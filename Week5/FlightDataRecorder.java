package aircraft;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a Flight Data Recorder (FDR) of an aircraft. 
 * Stores CAPACITY data points. 
 * @author anya
 *
 */
public class FlightDataRecorder {

    /**
     * The capacity of FlightDataRecorders.
     */
    public static final int CAPACITY = 10;

    /**
     * The index of the last element written to this FlightDataRecorder. 
     */
    private int tail;

    /**
     * The data recorded in this FlightDataRecorder.
     */
    private List<Double> recordedData;

    /**
     * Constructs a new FlightDataRecorder with no data.
     */
    public FlightDataRecorder() {
        recordedData = new ArrayList<Double>(CAPACITY);
        tail = -1;
    }
        
    /**
     * Records a new data point in this FlightDataRecorder.
     * 
     * @param dataPoint The new data point to be recorded.
     */
    public void record(Double dataPoint) {
        if (recordedData.size() < CAPACITY) {
            recordedData.add(null);
        }
        tail++;
        recordedData.set(tail, dataPoint);
    }

    /**
     * Returns a List of all recorded data points.
     * @return a List of all recorded data points.
     */
    public List<Double> getRecordedData() {
        List <Double> allData = new ArrayList<Double>();
        int fromHere = 0;
        if (recordedData.size() == CAPACITY) {
            fromHere = (tail + 1) % CAPACITY;
        }
        for (int i = 0; i < recordedData.size(); i++) {
            allData.add(recordedData.get(fromHere));
            fromHere = (fromHere + 1) % CAPACITY;
        }
        return allData;
    }
    
    /**
     * If this FlightDataRecorder contains at least len data points, returns a
     * List of the most recent len data points. Otherwise, returns a List of all
     * the data points that this FlightDataRecorder stores. The List contains
     * the recorded data points in reverse order: the most recent item will be
     * at index 0.
     * 
     * @param len the number of recent data points to be included.
     */
    public List<Double> getLastDataPoints(int len) {
        List<Double> lastDataPoints = new ArrayList<Double>();

        for (int idx = 0; idx < Math.min(len, recordedData.size()); idx++) {
            lastDataPoints.add(recordedData.get((tail + CAPACITY) % CAPACITY));
            tail--;
        }
        return lastDataPoints;
    }

    /**
     * Returns the average of the data points currently recorded in
     * this FlightDataRecorder (i.e., excludes data points already
     * overwritten). Return 0.0 if no values
     * have been recorded.
     *   
     * @return The average of the data points recorded in this
     * FlightDataRecorder or 0, if no data points have been recorded.
     */
    public double average() {
        double total = 0;
        for (Double dataPoint : recordedData) {
            total = total + dataPoint;
        }
        return total / CAPACITY;
    }
}
