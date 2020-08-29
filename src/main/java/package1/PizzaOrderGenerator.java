package package1;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @auther Steven J
 * @createDate 2020-08-29  1:11 AM
 * This SourceFunction generates a data stream.
 *
 * <p>The stream is produced out-of-order, and includes Watermarks (with no late events).
 *
 */
public class PizzaOrderGenerator implements SourceFunction<PizzaOrder> {

    public static final int SLEEP_MILLIS_PER_EVENT = 10;
    private static final int BATCH_SIZE = 5;
    private volatile boolean running = true;

    @Override
    public void run(SourceContext<PizzaOrder> ctx) throws Exception {

        long id = 0;
        long maxStartTime = 0;

        while (running&&id<100) {

            // generate a batch of PizzaOrders events
            List<PizzaOrder> Orders = new ArrayList<PizzaOrder>(BATCH_SIZE);
            for (int i = 1; i <= BATCH_SIZE; i++) {
                PizzaOrder order = new PizzaOrder(id + i);
                Orders.add(order);
                // the start times may be in order, but let's not assume that
                maxStartTime = Math.max(maxStartTime, order.placeTime.toEpochMilli());
            }
            // then emit the new START events (out-of-order)
            java.util.Collections.shuffle(Orders, new Random(id));
            Orders.iterator().forEachRemaining(r -> ctx.collectWithTimestamp(r, r.getPlaceTime()));

            // produce a Watermark
            ctx.emitWatermark(new Watermark(maxStartTime));

            // prepare for the next batch
            id += BATCH_SIZE;

            // don't go too fast
            Thread.sleep(BATCH_SIZE * SLEEP_MILLIS_PER_EVENT);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
