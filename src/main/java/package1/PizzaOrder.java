package package1;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.Instant;

/**
 * @auther Steven J
 * @createDate 2020-08-29  1:16 AM
 */
/**
 * A TaxiRide is a taxi ride event. There are two types of events, a taxi ride start event and a
 * taxi ride end event. The isStart flag specifies the type of the event.
 *
 * <p>A TaxiRide consists of
 * - the rideId of the event which is identical for start and end record
 * - the type of the event (start or end)
 * - the time of the event
 * - the longitude of the start location
 * - the latitude of the start location
 * - the longitude of the end location
 * - the latitude of the end location
 * - the passengerCnt of the ride
 * - the taxiId
 * - the driverId
 */
public class PizzaOrder implements Comparable<PizzaOrder>, Serializable {

    /**
     * Creates a new TaxiRide with now as start and end time.
     */
    public PizzaOrder() {
    }

    /**
     * Invents a TaxiRide.
     */
    public PizzaOrder(long orderId) {
        DataGenerator g = new DataGenerator(orderId);

        this.orderId = orderId;
        this.placeTime = g.placeTime();
        this.addrLon = g.startLon();
        this.addrLat = g.startLat();
        this.pizzaType = g.pizzaType();
        this.status = g.status();
    }

    /**
     * Creates a TaxiRide with the given parameters.
     */

    public long orderId;
    public Instant placeTime;
    public float addrLon;
    public float addrLat;
    public short pizzaType;
    public String status;

    @Override
    public String toString() {

        return "Order ID: "+orderId + "," +
                "Place Time: "+placeTime.toString() + "," +
                "addrLon: "+addrLon + "," +
                "addrLat" +addrLat + "," +
                "pizzaType" +pizzaType + "," +
                status;
    }

    /**
     * Compares this TaxiRide with the given one.
     *
     * <ul>
     *     <li>sort by timestamp,</li>
     *     <li>putting START events before END events if they have the same timestamp</li>
     * </ul>
     */
    public int compareTo(@Nullable PizzaOrder other) {
        if (other == null) {
            return 1;
        }
        if(this.orderId-other.orderId==0) return 0;
        return this.orderId-other.orderId>0? 1:-1;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof PizzaOrder &&
                this.orderId == ((PizzaOrder) other).orderId;
    }

    @Override
    public int hashCode() {
        return (int) this.orderId;
    }

    public long getPlaceTime() {
        return placeTime.toEpochMilli();
    }
}

