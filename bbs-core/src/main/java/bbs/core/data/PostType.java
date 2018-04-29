package bbs.core.data;

import java.io.Serializable;
import java.sql.Timestamp;

public class PostType implements Serializable{
    private static final long serialVersionUID = 3787332818144499202L;

    private Long id;

    private String name;

    private String typeCreator;

    private Timestamp createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeCreator() {
        return typeCreator;
    }

    public void setTypeCreator(String typeCreator) {
        this.typeCreator = typeCreator;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
