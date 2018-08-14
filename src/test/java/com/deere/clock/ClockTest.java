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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.joda.time.DateTimeZone.UTC;
import static org.junit.Assert.*;

public class ClockTest {
    @After
    public void tearDown() {
        Clock.clear();
    }

    @Test
    public void freezeTheClock() {
        Clock.freeze();
        DateTime d = Clock.now();
        sleep(256L);
        assertEquals(d, Clock.now());
        Clock.clear();
        sleep(256L);
        assertEquals(true, d.isBefore(Clock.now()));
    }

    @Test
    @Ignore //This test can't be trusted to run all the time.
    public void differntClocksOnDifferntThreads()  {
        Clock.freeze(2);

        IntStream.range(0,60).parallel().forEach(x -> {
            sleep(256);
            System.out.println("Clock.now().getMillis() = " + Clock.now().getMillis());
        });
    }

    @Test
    public void canFreezeAllThreads(){
        DateTime NOW = new DateTime(1985,10,5, 1, 30, Clock.UTC_CHRONOLOGY);
        Clock.freezeAllThreads(NOW);
        IntStream.range(1, 100).parallel().forEach(i ->{
            assertEquals(NOW, Clock.now());
        });
    }

    @Test
    public void epochIsInUTC(){
        assertEquals(new DateTime(0L, ISOChronology.getInstance(UTC)), Clock.epoch());
    }

    private void sleep(long t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
