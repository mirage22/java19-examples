/*
 * Copyright (c) 2022, Miro Wengner (mirage22)
 *
 * java19-examples is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * java19-examples  is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *  along with java19-examples. If not, see <http://www.gnu.org/licenses/>.
 */

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

record ComputableTask(AtomicInteger counter, int failedCycle) implements Runnable {
    @Override
    public void run() {
        var e = new ComputableTaskEvent(counter.get());
        e.begin();
        CompletableFutureMain.readingOperation(counter, failedCycle);
        System.out.printf("""
                DONE: thread: '%s', cycle: '%d', failedCycle:'%d'
                """, Thread.currentThread().getName(), counter.get(), failedCycle);
        e.setDesc("""
            isVirtual:'%s', isDaemon:'%s', threadGroup:'%s', priority:'%d'
            """.formatted(Thread.currentThread().isVirtual(), 
                Thread.currentThread().isDaemon(),
                Thread.currentThread().getThreadGroup().getName(),
                Thread.currentThread().getPriority()));
        e.commit();
    }
}

public class CompletableFutureMain {
    static final int CYCLE_MAX = 100;
    public static void main(String[] args) {
        var counter = new AtomicInteger(0);
        var failedCycle = new Random().nextInt(CYCLE_MAX - 1) + 1;

        var completableFuture = CompletableFuture
                .supplyAsync(() -> new ComputableTask(counter, failedCycle));
        for (int i = 0; i < CYCLE_MAX; i++) {

            completableFuture.thenRun(new ComputableTask(counter, failedCycle));
        }
        System.out.printf("""
                FINISHED: cycles:'%d'
                """, CYCLE_MAX);

    }

    static void readingOperation(final AtomicInteger counter, int failedCycle) {
        if (counter.get() % CYCLE_MAX == failedCycle) throw new RuntimeException("something wrong");
        try {
            TimeUnit.MILLISECONDS.sleep(Duration.ofMillis(counter.getAndIncrement()).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}