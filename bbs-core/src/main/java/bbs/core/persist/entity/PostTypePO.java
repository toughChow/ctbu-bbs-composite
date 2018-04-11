package bbs.core.persist.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "T_PUB_POST_TYPE")
public class PostTypePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "TYPE_CREATOR")
    private Integer typeCreator;

    @Column(name = "CREATE_TIME")
    private Timestamp createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTypeCreator() {
        return typeCreator;
    }

    public void setTypeCreator(Integer typeCreator) {
        this.typeCreator = typeCreator;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
