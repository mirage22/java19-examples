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

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

@Label("Computable-Worker")
@Category("Thread_Computable_Example")
@Description("Computation event")
public class ComputableTaskEvent extends Event {

    @Label("Computable-id")
    @Description("Computable-id")
    private Integer id;
    private String desc;

    public ComputableTaskEvent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}