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

import java.util.Map;

public class ThreadWorker implements Runnable {
    private final int id;
    private final int cycles;
    private final Map<Integer, Sample> map;
    private boolean active;

    public ThreadWorker(int id, int cycles, int mapSize) {
        this.id = id;
        this.cycles = cycles;
        this.map = ThreadsUtil.createMap(mapSize);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        long counter = 0;
        active = true;
        while (active && counter < cycles) {
        // while (active) {
            var e = new ThreadWorkerEvent(id);
            e.begin();
            for (Sample s : map.values()) {
                if (!map.containsKey(s.getId())) {
                    System.out.println("WARNING, ThreadWorker-" + id + ", sample number i:" + s.getId() + " not present!");
                }
                if (++counter % 1000 == 0) {
                    Thread.yield();
                }
            }
            
            e.setDesc("""
                    isVirtual:'%s', isDaemon:'%s', threadGroup:'%s', priority: '%s'
                    """.formatted(Thread.currentThread().isVirtual(), 
                    Thread.currentThread().isDaemon(),
                    Thread.currentThread().getThreadGroup().getName(),
                    Thread.currentThread().getPriority()));
            e.commit();
        }
    }
}
