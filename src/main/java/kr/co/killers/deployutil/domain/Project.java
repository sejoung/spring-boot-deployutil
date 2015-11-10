package kr.co.killers.deployutil.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 테이블 유니크키
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 프로젝트명
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 프로젝트 상세설명
	 */
	@Column(nullable = true)
	private String description;

	/**
	 * 개발서버 호스트명
	 */
	@Column(nullable = false)
	private String devHostname;

	/**
	 * 검수서버 호스트명
	 */
	@Column(nullable = false)
	private String testHostname;

	/**
	 * 운영서버 호스트명
	 */
	@Column(nullable = false)
	private String prodHostname;

	/**
	 * 개발 서버에 해당 shell 파일이 동작할 위치 예) /data/wh/work/입력받는 날짜
	 *
	 */
	@Column(nullable = false)
	private String devServerWorkDir;

	/**
	 * 검수 서버에 해당 shell 파일이 동작할 위치 예) /data/wh/work/입력받는 날짜
	 *
	 */
	@Column(nullable = false)
	private String testServerWorkDir;

	/**
	 * 운영 서버에 해당 shell 파일이 동작할 위치 예) /data/wh/work/입력받는 날짜
	 *
	 */
	@Column(nullable = false)
	private String prodServerWorkDir;

	/**
	 * 프로젝트 패스에서 검색할 위치 예) src,resources,web\\WEB-INF\\jsp,web\\images,web\\css
	 */
	@Column(nullable = false)
	private String scanPath;

	/**
	 * 복사 쉘 파일명
	 */
	@Column(nullable = false)
	private String copyShellFileName = "source_copy.sh";

	/**
	 * 백업 쉘 파일명
	 */
	@Column(nullable = false)
	private String backupShellFileName = "source_backup.sh";

	/**
	 * 리커버리 쉘 파일명
	 */
	@Column(nullable = false)
	private String recoveryShellFileName = "source_recovery.sh";

	/**
	 * 복사 체크 쉘 파일명
	 */
	@Column(nullable = false)
	private String copyCheckShellFileName = "source_copy_check.sh";

	/**
	 * 리커버리 체크 쉘 파일명
	 */
	@Column(nullable = false)
	private String recoveryCheckShellFileName = "source_recovery_check.sh";

	/**
	 * 프로젝트에서 사용하는 jdk 버전
	 */
	@Column(nullable = false)
	private String projectJdkVersion;

	/**
	 * 메이븐 사용여부
	 */
	@Column(nullable = false)
	private String mavenUseYn = "N";

}
