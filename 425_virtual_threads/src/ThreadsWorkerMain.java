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

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadsWorkerMain {

    public static final int THREADS_NUMBER = 2;
    public static final int ALLOCATION_SIZE = 100_000;
    public static final int MAX_CYCLES = 10;
    public static final int EXECUTION_CYCLES = 100;

    public static void main(String[] args) throws Exception {
        System.out.println("Threads example...");

        var threadFactory = new ThreadFactory() {
            private final ThreadGroup threadGroup = new ThreadGroup("thread-workers");
            private final AtomicInteger counter = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                var t = new Thread(threadGroup, r, "thread-worker-" + counter.getAndIncrement());
                t.setDaemon(true);
                return t;
            }
        };

        var executor = Executors.newFixedThreadPool(THREADS_NUMBER, threadFactory);
        for (int i = 0; i < EXECUTION_CYCLES; i++) {
            executor.submit(new ThreadWorker(i, MAX_CYCLES, ALLOCATION_SIZE));
        }

        System.out.println("Press any key to exit!...");
        System.out.flush();
        System.in.read();
        executor.shutdownNow();

    }
}
