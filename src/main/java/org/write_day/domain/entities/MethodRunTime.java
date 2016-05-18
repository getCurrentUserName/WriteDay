package org.write_day.domain.entities;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by LucidMinds on 14.05.16.
 * package org.write_day.domain.entities;
 */
@Entity
@Table(name = "runtime_table")
@DynamicUpdate
@DynamicInsert
public class MethodRunTime {

    public static final String ID = "id";
    public static final String PACKAGE_NAME = "packageName";
    public static final String CLASS_NAME = "className";
    public static final String METHOD_NAME = "methodName";
    public static final String TIME = "time";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = PACKAGE_NAME)
    private String packageName;

    @Column(name = CLASS_NAME)
    private String className;

    @Column(name = METHOD_NAME)
    private String methodName;

    @Column(name = TIME)
    private Long time;

    public MethodRunTime() {
    }

    public MethodRunTime(String packageName, String className, String methodName, long time) {
        this.packageName = packageName;
        this.className = className;
        this.methodName = methodName;
        this.time = time;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
