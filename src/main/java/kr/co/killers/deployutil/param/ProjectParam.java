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
    @NotEmpty
    private Long id;

    private String soruceDir;

    private String destDir;

    private String libDir;

    private String jdkVersion;

    private String charSet;
}
