/*
 * Copyright 2018 Andy Turner, CCG, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.data.stats19.core;

import java.io.Serializable;

/**
 * S_Object
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public abstract class S_Object implements Serializable {

    public transient S_Environment env;

    protected S_Object() {
    }

    public S_Object(S_Environment e) {
        env = e;
    }
}
