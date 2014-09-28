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
