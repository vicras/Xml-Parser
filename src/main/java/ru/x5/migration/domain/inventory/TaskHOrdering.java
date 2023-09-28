package ru.x5.migration.domain.inventory;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TaskHOrdering {
    OPEN(0),
    STARTED(1),
    CLOSED(2),
    AUTO_CLOSED(3),
    BOOKED(4),
    BOOK(4), //
    REOPENED(5),
    VERIFIED(6),
    CONFIRMED(7),
    REQUIRED_CONTROL(8),
    READY_FOR_BOOKING(9),
    SENDING(10),
    SENDING_ERROR(11),
    PROCESSING_ERROR(12),
    SENT_CANCELLING(13);

    final int order;

    TaskHOrdering(int i) {
        order = i;
    }

    public static TaskHOrdering of(String title) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't recognize TaskHOrdering type: "+ title));
    }
}

