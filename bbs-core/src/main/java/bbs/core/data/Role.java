package bbs.core.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    @JsonIgnore
    private List<AuthMenu> authMenus = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AuthMenu> getAuthMenus() {
        return authMenus;
    }

    public void setAuthMenus(List<AuthMenu> authMenus) {
        this.authMenus = authMenus;
    }
}
