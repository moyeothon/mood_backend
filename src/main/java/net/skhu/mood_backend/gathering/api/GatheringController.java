package net.skhu.mood_backend.gathering.api;

import net.skhu.mood_backend.gathering.api.dto.request.GatheringSaveReqDto;
import net.skhu.mood_backend.gathering.api.dto.response.GatheringInfoResDto;
import net.skhu.mood_backend.gathering.application.GatheringService;
import net.skhu.mood_backend.global.template.RspTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gatherings")
public class GatheringController implements GatheringControllerDocs {

    private final GatheringService gatheringService;

    public GatheringController(GatheringService gatheringService) {
        this.gatheringService = gatheringService;
    }

    @PostMapping("")
    public RspTemplate<GatheringInfoResDto> createGathering(@RequestBody GatheringSaveReqDto gatheringSaveReqDto)
            throws Exception {
        return new RspTemplate<>(HttpStatus.OK, "모임 생성 완료", gatheringService.createGathering(gatheringSaveReqDto));
    }

    @GetMapping("/{id}")
    public RspTemplate<GatheringInfoResDto> gatheringInfo(@PathVariable(name = "id") Long gatheringId) {
        return new RspTemplate<>(HttpStatus.OK, "모임 정보 조회 완료", gatheringService.getGatheringInfo(gatheringId));
    }

    @PatchMapping("/{id}")
    public RspTemplate<GatheringInfoResDto> updateGathering(@PathVariable(name = "id") Long gatheringId,
                                                            @RequestBody GatheringSaveReqDto gatheringSaveReqDto)
            throws Exception {
        return new RspTemplate<>(HttpStatus.OK,
                "모임 정보 수정 완료",
                gatheringService.updateGathering(gatheringId, gatheringSaveReqDto));
    }

    @PatchMapping("/topic/{id}")
    public RspTemplate<GatheringInfoResDto> updateConversationTopicsAndSuggestedActivities(
            @PathVariable(name = "id") Long gatheringId) throws Exception {
        return new RspTemplate<>(HttpStatus.OK,
                "모임 주제 수정 완료",
                gatheringService.updateConversationTopicsAndSuggestedActivities(gatheringId));
    }
}
