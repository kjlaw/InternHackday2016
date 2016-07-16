package ca.jeffrey.funapp.utils;

import android.support.annotation.Nullable;

import rx.Subscription;

public class RxUtils {

    /**
     * Safely un-subscribes the given {@link Subscription} from any {@link rx.Observable}s it may be
     * still subscribed to.
     * <p>
     * Provides basic safety checks for null and previously un-subscribed {@link Subscription}s
     *
     * @param subscription the {@link Subscription} to un-subscribe
     */
    public static void unsubscribe(@Nullable final Subscription subscription) {
        if (isSubscribed(subscription)) {
            subscription.unsubscribe();
        }
    }

    /**
     * Safely checks whether or not a given {@link Subscription} is currently subscribed to any {@link rx.Observable}s.
     */
    public static boolean isSubscribed(@Nullable Subscription subscription) {
        return (subscription != null && !subscription.isUnsubscribed());
    }

    private RxUtils() {
    }
}