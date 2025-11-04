package SDD.smash.OpenAI.Converter;

import SDD.smash.Apis.Dto.DetailDTO;
import SDD.smash.Apis.Dto.DetailResponseDTO;
import SDD.smash.Apis.Dto.RecommendDTO;
import SDD.smash.Apis.Dto.RecommendResponseDTO;
import SDD.smash.OpenAI.Dto.AiPick;
import SDD.smash.OpenAI.Dto.AiRecommendDTO;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AiConverter {
    public static DetailResponseDTO toResponseDTO(DetailDTO dto, @Nullable String summarizeContent){
        return DetailResponseDTO.builder()
                .sidoCode(dto.getSidoCode())
                .sidoName(dto.getSidoName())
                .sigunguCode(dto.getSigunguCode())
                .sigunguName(dto.getSigunguName())
                .population(dto.getPopulation())
                .totalJobInfo(dto.getTotalJobInfo())
                .fitJobInfo(dto.getFitJobInfo())
                .totalSupportNum(dto.getTotalSupportNum())
                .supportList(dto.getSupportList())
                .dwellingInfo(dto.getDwellingInfo())
                .infraDetails(dto.getInfraDetails())
                .aiSummary(summarizeContent)
                .build();
    }

    public static List<RecommendResponseDTO> toResponseList(List<RecommendDTO> recommendDTOList,
                                                            @Nullable AiRecommendDTO aiRecommendDTO){
        List<String> sigunguCodes =
                (aiRecommendDTO == null || aiRecommendDTO.getRecommendations() == null)
                        ? List.of()
                        : aiRecommendDTO.getRecommendations().stream()
                        .map(AiPick::getSigunguCode)
                        .toList();

        List<String> reasons =
                (aiRecommendDTO == null || aiRecommendDTO.getRecommendations() == null)
                        ? List.of()
                        : aiRecommendDTO.getRecommendations().stream()
                        .map(AiPick::getReason)
                        .toList();

        List<RecommendResponseDTO> out = new ArrayList<>(recommendDTOList.size());

        for (RecommendDTO dto : recommendDTOList) {
            RecommendResponseDTO item = RecommendResponseDTO.builder()
                    .sidoCode(dto.getSidoCode())
                    .sidoName(dto.getSidoName())
                    .sigunguCode(dto.getSigunguCode())
                    .sigunguName(dto.getSigunguName())
                    .score(dto.getScore())
                    .totalJobInfo(dto.getTotalJobInfo())
                    .fitJobInfo(dto.getFitJobInfo())
                    .totalSupportNum(dto.getTotalSupportNum())
                    .fitSupportNum(dto.getFitSupportNum())
                    .dwellingSimpleInfo(dto.getDwellingSimpleInfo())
                    .infraMajors(dto.getInfraMajors())
                    // 기본은 null(혹은 빈 리스트로 하고 싶다면 .AiPickSigunguCodes(List.of()) 등으로 변경)
                    .AiPickSigunguCodes(null)
                    .AiPickReasons(null)
                    .build();
            out.add(item);
        }
        if (!out.isEmpty()) {
            int last = out.size() - 1;
            RecommendResponseDTO lastItem = out.get(last);

            // Lombok @Builder는 불변이라 새로 빌더로 재생성
            RecommendResponseDTO lastWithAi = RecommendResponseDTO.builder()
                    .sidoCode(lastItem.getSidoCode())
                    .sidoName(lastItem.getSidoName())
                    .sigunguCode(lastItem.getSigunguCode())
                    .sigunguName(lastItem.getSigunguName())
                    .score(lastItem.getScore())
                    .totalJobInfo(lastItem.getTotalJobInfo())
                    .fitJobInfo(lastItem.getFitJobInfo())
                    .totalSupportNum(lastItem.getTotalSupportNum())
                    .fitSupportNum(lastItem.getFitSupportNum())
                    .dwellingSimpleInfo(lastItem.getDwellingSimpleInfo())
                    .infraMajors(lastItem.getInfraMajors())
                    .AiPickSigunguCodes(sigunguCodes)
                    .AiPickReasons(reasons)
                    .build();

            out.set(last, lastWithAi);
        }

        return out;
    }
}
