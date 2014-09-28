package grails.plugin.console.charts.shared;

import java.io.Serializable;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public interface ShareDetails extends Serializable {

    String getTitle();

    void setTitle(String title);

    Integer getWidth();

    void setWidth(Integer width);

    Integer getHeight();

    void setHeight(Integer height);

    String getConnectionString();

    void setConnectionString(String connectionString);

    String getQuery();

    void setQuery(String query);

    String getAppearance();

    void setAppearance(String appearance);

    String getView();

    void setView(String view);

    Boolean isEditable();

    void setEditable(Boolean editable);

}
