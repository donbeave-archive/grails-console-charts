/*
 * Copyright 2014 the original author or authors
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
package grails.plugin.console.charts.shared;

import java.io.Serializable;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public interface ConnectionDetails extends Serializable {

    String getMysqlHostname();

    void setMysqlHostname(String hostname);

    Integer getMysqlPort();

    void setMysqlPort(Integer port);

    String getMysqlUsername();

    void setMysqlUsername(String username);

    String getMysqlPassword();

    void setMysqlPassword(String password);

    Boolean isSshToggle();

    void setSshToggle(Boolean toggle);

    String getSshHostname();

    void setSshHostname(String hostname);

    Integer getSshPort();

    void setSshPort(Integer port);

    String getSshUsername();

    void setSshUsername(String username);

    String getSshPassword();

    void setSshPassword(String password);

}
