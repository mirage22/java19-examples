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
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualThreadsMain {

    public static void main(String[] args) throws Exception {
        System.out.println("JEP-425: Virtual Threads (Preview) example...");

        var threadFactory = Thread.ofVirtual()
                .name("ForkJoin-custom-factory-", 0)
                .factory();
        var counter = new AtomicInteger(0);
        var failedCycle = new Random().nextInt(CompletableFutureMain.CYCLE_MAX - 1) + 1;
        try (var executor = Executors.newThreadPerTaskExecutor(threadFactory)) {
            for (int i = 0; i < ThreadsWorkerMain.EXECUTION_CYCLES; i++) {
                executor.submit(new ComputableTask(counter, failedCycle));
            }
        }
        System.out.println("MAX_CYCLES: " + ThreadsWorkerMain.EXECUTION_CYCLES);

        System.out.println("Press any key to exit!...");
        System.out.flush();
        System.in.read();
    }
}

