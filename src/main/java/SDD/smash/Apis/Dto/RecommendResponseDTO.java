package SDD.smash.Apis.Dto;

import SDD.smash.Dwelling.Dto.DwellingSimpleInfoDTO;
import SDD.smash.Infra.Dto.InfraMajor;
import SDD.smash.Job.Dto.JobInfoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendResponseDTO {

    private String sidoCode;
    private String sidoName;

    private String sigunguCode;
    private String sigunguName;

    private Integer score;

    // 일자리
    private JobInfoDTO totalJobInfo;
    private JobInfoDTO fitJobInfo;

    //지원사업
    private Integer totalSupportNum;
    private Integer fitSupportNum;

    //주거
    private DwellingSimpleInfoDTO dwellingSimpleInfo;

    //인프라
    private List<InfraMajor> infraMajors;

    // AI 추천 시군구 코드
    private List<String> AiPickSigunguCodes;
    private List<String> AiPickReasons;

}
