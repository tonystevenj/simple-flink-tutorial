package package1;

/**
 * @auther Steven J
 * @createDate 2020-08-29  1:20 AM
 */
import java.time.Instant;
import java.util.Random;

/**
 * Data generator for the fields in TaxiRide and TaxiFare objects.
 *
 * <p>Results are deterministically determined by the rideId. This guarantees (among other things)
 * that the startTime for a TaxiRide START event matches the startTime for the TaxiRide END and
 * TaxiFare events for that same rideId.
 */
public class DataGenerator {

    private static final int SECONDS_BETWEEN_RIDES = 20;
    private static final int NUMBER_OF_DRIVERS = 200;
    private static final Instant beginTime = Instant.parse("2020-01-01T12:00:00.00Z");

    private transient long rideId;

    /**
     * Creates a DataGenerator for the specified rideId.
     */
    public DataGenerator(long rideId) {
        this.rideId = rideId;
    }

    /**
     * Deterministically generates and returns the startTime for this ride.
     */
    public Instant placeTime() {
        return beginTime.plusSeconds(SECONDS_BETWEEN_RIDES * rideId);
    }


    /**
     * Deterministically generates and returns the driverId for this ride.
     * The HourlyTips exercise is more interesting if aren't too many drivers.
     */
    public long driverId() {
        Random rnd = new Random(rideId);
        return 2013000000 + rnd.nextInt(NUMBER_OF_DRIVERS);
    }

    /**
     * Deterministically generates and returns the taxiId for this ride.
     */
    public long taxiId() {
        return driverId();
    }

    public float startLat() {
        return new Random(rideId).nextInt(10);
    }
    public float startLon() {
        return new Random(rideId).nextInt(10);
    }

    public String status(){
        String statuses[] = new String[]{"Inprocess","Shipped","Delivered"};
        Random rnd = new Random(rideId);
        return statuses[rnd.nextInt(3)];
    }




    /**
     * Deterministically generates and returns the passengerCnt for this ride.
     */
    public short pizzaType() {
        return (short) aLong(1L, 4L);
    }

    /**
     * Deterministically generates and returns the paymentType for this ride.
     */
    public String paymentType() {
        return (rideId % 2 == 0) ? "CARD" : "CASH";
    }

    /**
     * Deterministically generates and returns the tip for this ride.
     *
     * <p>The HourlyTips exercise is more interesting if there's some significant variation in tipping.
     */
    public float tip() {
        return aLong(0L, 60L, 10F, 15F);
    }

    /**
     * Deterministically generates and returns the tolls for this ride.
     */
    public float tolls() {
        return (rideId % 10 == 0) ? aLong(0L, 5L) : 0L;
    }

    /**
     * Deterministically generates and returns the totalFare for this ride.
     */
    public float totalFare() {
        return (float) (3.0 + (1.0 * rideDurationMinutes()) + tip() + tolls());
    }

    /**
     * The LongRides exercise needs to have some rides with a duration > 2 hours, but not too many.
     */
    private long rideDurationMinutes() {
        return aLong(0L, 600, 20, 40);
    }

    // -------------------------------------

    private long aLong(long min, long max) {
        float mean = (min + max) / 2.0F;
        float stddev = (max - min) / 8F;

        return aLong(min, max, mean, stddev);
    }

    // the rideId is used as the seed to guarantee deterministic results
    private long aLong(long min, long max, float mean, float stddev) {
        Random rnd = new Random(rideId);
        long value;
        do {
            value = (long) Math.round((stddev * rnd.nextGaussian()) + mean);
        } while ((value < min) || (value > max));
        return value;
    }

    // -------------------------------------

    private float aFloat(float min, float max) {
        float mean = (min + max) / 2.0F;
        float stddev = (max - min) / 8F;

        return aFloat(rideId, min, max, mean, stddev);
    }

    private float bFloat(float min, float max) {
        float mean = (min + max) / 2.0F;
        float stddev = (max - min) / 8F;

        return aFloat(rideId + 42, min, max, mean, stddev);
    }

    // the rideId is used as the seed to guarantee deterministic results
    private float aFloat(long seed, float min, float max, float mean, float stddev) {
        Random rnd = new Random(seed);
        float value;
        do {
            value = (float) (stddev * rnd.nextGaussian()) + mean;
        } while ((value < min) || (value > max));
        return value;
    }

}

