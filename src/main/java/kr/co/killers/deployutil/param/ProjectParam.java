package kr.co.killers.deployutil.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class ProjectParam {
    /**
     * 테이블 유니크키
     */
//    @NotEmpty
    private String svnId;

    private String svnPassword;

    private int startRevision;

    private int endRevision;

    private String svnUrl;

    private String sourceDir;

    private String sourceWWWDir;

    private String sourceLibDir;

    private String sourceDeployDir;

    private String tomcatLibPath;

    private String destDir;

    private String javaVersion;

    private String javaLibPath;

    private String charSet;


}
