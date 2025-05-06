package com.ppiyong.backend.api.manual.controller;

import com.ppiyong.backend.api.manual.dto.manual.ManualRespondDto;
import com.ppiyong.backend.api.manual.dto.manualcategory.ManualCategoryRespondDto;
import com.ppiyong.backend.api.manual.dto.manualdetail.ManualDetailRespondDto;
import com.ppiyong.backend.api.manual.dto.manualkeyword.ManualKeywordRespondDto;
import com.ppiyong.backend.api.manual.service.ManualService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manuals")
@RequiredArgsConstructor
@Tag(name = "Manual", description = "매뉴얼 관련 API")
public class ManualController {
    private final ManualService manualService;

    // 1. 전체 조회/이름 검색
    @Operation(summary = "매뉴얼 조회에 따른 매뉴얼 정보 반환", description =  """
            응급상황이름에 따라 매뉴얼을 조회합니다.<br>
            """, parameters = {
            @Parameter(name = "emergencyName", description = "응급상황 이름", schema = @Schema(type = "string", example = "실신")),
    })
    @GetMapping
    public List<ManualRespondDto> getManuals(@RequestParam(required = false) String name,
                                             @RequestHeader("Authorization") String authToken) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        return manualService.getManuals(name, token);
    }

    // 2. 카테고리별 조회
    @Operation(summary = "카테고리 별 조회", description = """
            카테고리 별로 매뉴얼을 조회합니다.<br>
            """, parameters = {@Parameter(name = "Category", description = "카테고리 이름", schema = @Schema(type = "string", example = "의학적"))})
    @GetMapping("/category")
    public List<ManualCategoryRespondDto> getManualsByCategory(@RequestParam String category,
                                                               @RequestHeader("Authorization") String authToken) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        return manualService.getManualsByCategory(category, token);
    }

    // 3. 상세 조회
    @Operation(summary = "매뉴얼 세부내용 조회", description = """
            해당하는 응급상황 이름에 대한 대처 세부내용을 반환합니다.<br>
            """, parameters = {@Parameter(name = "EmergencyName", description = "응급상황 이름", schema = @Schema(type = "string", example = "심장마비"))})
    @GetMapping("/{name}")
    public ManualDetailRespondDto getManualDetail(@PathVariable String name,
                                                  @RequestHeader("Authorization") String authToken) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        return manualService.getManualDetail(name, token);
    }

    // 4. 검색 자동완성(응급상황이름)
    @Operation(summary = "검색 자동완성", description = """
            검색에 해당하는 응급상황 이름들을 자동 보여줍니다.<br>
            """, parameters = {@Parameter(name = "EmergencyName", description = "검색할 응급상황이름", schema = @Schema(type = "string", example = "심장"))})
    @GetMapping("/autocomplete")
    public List<String> autocomplete(@RequestParam String name,
                                     @RequestHeader("Authorization") String authToken) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        return manualService.autocomplete(name, token);
    }

    // 5. 키워드 검색
    @Operation(summary = "매뉴얼 조회에 따른 매뉴얼 정보 반환", description =  """
            키워드에 따라 매뉴얼을 조회합니다.
            """, parameters = {
            @Parameter(name = "keyword", description = "검색 키워드", schema = @Schema(type = "string", example = "심장"))
    })
    @GetMapping("/keyword")
    public List<ManualKeywordRespondDto> searchByKeyword(@RequestParam String keyword,
                                                         @RequestHeader("Authorization") String authToken) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        return manualService.searchByKeyword(keyword, token);
    }

    // 6. 좋아요 추가
    @Operation(summary = "매뉴얼 정보 즐겨찾기 추가", description =  """
            매뉴얼 정보를 즐겨찾기합니다.<br>
            헤더에 accessToken을 넣어주세요.<br>
            """, parameters = {
            @Parameter(name = "EmergencyName", description = "응급상황이름", schema = @Schema(type = "int", example = "2"))
    })
    @PostMapping("like/{name}")
    public void likeManual(@PathVariable String name, @RequestHeader("Authorization") String authToken) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        manualService.likeManual(token, name);
    }

    // 7. 좋아요 해제
    @Operation(summary = "매뉴얼 정보 즐겨찾기 삭제", description =  """
            매뉴얼 정보 즐겨찾기한것을 삭제합니다.<br>
            헤더에 accessToken을 넣어주세요.<br>
            """, parameters = {
            @Parameter(name = "EmergencyName", description = "응급상황이름", schema = @Schema(type = "int", example = "2"))
    })
    @DeleteMapping("unlike/{name}")
    public void unlikeManual(
            @PathVariable String name,
            @RequestHeader("Authorization") String authToken) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        manualService.unlikeManual(token, name);
    }

    // 8. 좋아요 목록 조회
    @Operation(summary = "매뉴얼 좋아요 조회하기")
    @GetMapping("/liked")
    public List<ManualRespondDto> getLikedManuals(@RequestHeader("Authorization") String authToken) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        return manualService.getLikedManuals(token);
    }
}
