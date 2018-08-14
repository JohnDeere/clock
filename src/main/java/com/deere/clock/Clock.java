/**
 * Copyright 2014-2018 Deere & Company
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

package com.deere.clock;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;

import static org.joda.time.DateTimeZone.UTC;

public class Clock {
    public static final Chronology UTC_CHRONOLOGY = ISOChronology.getInstance(UTC);
    private static final DateTime epoch =  new DateTime(0L, Clock.UTC_CHRONOLOGY);
    private static final ClockInstance realTime = new RealTime();
    private static ThreadLocal<ClockInstance>  clocks = ThreadLocal.withInitial(() -> realTime);

    public static DateTime now() {
        return clocks.get().now();
    }

    public static long milliseconds() {
        return now().getMillis();
    }

    public static DateTime epoch() {
        return epoch;
    }

    public static void clear() {
        setThreadFactory(realTime);
        setDelegate(realTime);
    }

    public static void freeze(){
        freeze(new DateTime());
    }

    public static void freeze(final long timeInMillis) {
        freeze(new DateTime(timeInMillis, UTC_CHRONOLOGY));
    }

    public static void freeze(int yea, int mo, int day) {
        freeze(yea, mo, day, 0, 0);
    }

    public static void freeze(int yea, int mo, int day, int hour, int min) {
        freeze(new DateTime(yea,mo,day,hour, min, 0, 0, UTC_CHRONOLOGY));
    }

    public static void freeze(DateTime dateTime){
        setDelegate(new FrozenClock(dateTime.withChronology(UTC_CHRONOLOGY)));
    }

    public static void freezeAllThreads(DateTime dateTime){
        FrozenClock value = new FrozenClock(dateTime.withChronology(UTC_CHRONOLOGY));
        setThreadFactory(value);
        setDelegate(value);
    }

    public static void freeze(ClockInstance clock){
        setDelegate(clock);
    }

    private static void setDelegate(ClockInstance value) {
        clocks.set(value);
    }

    private static void setThreadFactory(ClockInstance value) {
        clocks = ThreadLocal.withInitial(() -> value);
    }
}
