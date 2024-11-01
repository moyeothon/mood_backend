package net.skhu.mood_backend.gathering.application;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import net.skhu.mood_backend.gathering.api.dto.GptParsingDto;
import net.skhu.mood_backend.gathering.api.dto.request.GatheringSaveReqDto;
import net.skhu.mood_backend.gathering.api.dto.response.ChatGptResponseDto;
import net.skhu.mood_backend.gathering.api.dto.response.ConversationTopicInfoResDto;
import net.skhu.mood_backend.gathering.api.dto.response.ConversationTopicListResDto;
import net.skhu.mood_backend.gathering.api.dto.response.GatheringInfoResDto;
import net.skhu.mood_backend.gathering.api.dto.response.SuggestedActivityInfoResDto;
import net.skhu.mood_backend.gathering.api.dto.response.SuggestedActivityListResDto;
import net.skhu.mood_backend.gathering.domain.ConversationTopic;
import net.skhu.mood_backend.gathering.domain.Gathering;
import net.skhu.mood_backend.gathering.domain.SuggestedActivity;
import net.skhu.mood_backend.gathering.domain.repository.ConversationTopicRepository;
import net.skhu.mood_backend.gathering.domain.repository.GatheringRepository;
import net.skhu.mood_backend.gathering.domain.repository.SuggestedActivityRepository;
import net.skhu.mood_backend.gathering.exception.GatheringAccessDeniedException;
import net.skhu.mood_backend.gathering.exception.GatheringNotFountException;
import net.skhu.mood_backend.member.domain.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class GatheringService {

    @Value("${spring.ai.openai.chat.base-url}")
    private String apiURL;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${prompt}")
    private String prompt;

    private final ObjectMapper objectMapper;
    private final GatheringRepository gatheringRepository;
    private final ConversationTopicRepository conversationTopicRepository;
    private final SuggestedActivityRepository suggestedActivityRepository;

    public GatheringService(ObjectMapper objectMapper, GatheringRepository gatheringRepository,
                            ConversationTopicRepository conversationTopicRepository,
                            SuggestedActivityRepository suggestedActivityRepository) {
        this.objectMapper = objectMapper;
        this.gatheringRepository = gatheringRepository;
        this.conversationTopicRepository = conversationTopicRepository;
        this.suggestedActivityRepository = suggestedActivityRepository;
    }

    // 모임 생성
    @Transactional
    public GatheringInfoResDto createGathering(Member member, GatheringSaveReqDto gatheringSaveReqDto)
            throws Exception {
        String requestBody = buildPrompt(gatheringSaveReqDto.host(),
                gatheringSaveReqDto.relationshipType(),
                gatheringSaveReqDto.peopleCount(),
                gatheringSaveReqDto.vibe(),
                gatheringSaveReqDto.averageAge(),
                gatheringSaveReqDto.commonInterests());
        log.info("=============================1.buildPrompt");
        HttpResponse<String> response = sendApiRequest(requestBody);
        log.info("=============================2.response" + response.body());
        GptParsingDto parsedResponse = parseApiResponse(response);
        log.info("=============================3.parsedResponse");
        Gathering gathering = createAndSaveGathering(member, gatheringSaveReqDto);

        List<ConversationTopicInfoResDto> conversationTopicInfoResDtos =
                mapConversationTopics(parsedResponse, gathering);
        List<SuggestedActivityInfoResDto> suggestedActivityInfoResDtos =
                mapSuggestedActivities(parsedResponse, gathering);

        return buildResponseDto(gathering.getHost(),
                gathering.getRelationshipType(),
                gathering.getPeopleCount(),
                gathering.getVibe(),
                gathering.getAverageAge(),
                gathering.getCommonInterests(),
                conversationTopicInfoResDtos,
                suggestedActivityInfoResDtos);
    }

    // 모임 수정
    @Transactional
    public GatheringInfoResDto updateGathering(Member member, Long gatheringId, GatheringSaveReqDto gatheringSaveReqDto)
            throws Exception {
        Gathering gathering = gatheringRepository.findById(gatheringId).orElseThrow(GatheringNotFountException::new);

        validateMemberAccess(gathering, member);

        String requestBody = buildPrompt(gatheringSaveReqDto.host(),
                gatheringSaveReqDto.relationshipType(),
                gatheringSaveReqDto.peopleCount(),
                gatheringSaveReqDto.vibe(),
                gatheringSaveReqDto.averageAge(),
                gatheringSaveReqDto.commonInterests());
        HttpResponse<String> response = sendApiRequest(requestBody);

        GptParsingDto parsedResponse = parseApiResponse(response);

        gathering.updateGathering(gatheringSaveReqDto.host(),
                gatheringSaveReqDto.relationshipType(),
                gatheringSaveReqDto.peopleCount(),
                gatheringSaveReqDto.vibe(),
                gatheringSaveReqDto.averageAge(),
                gatheringSaveReqDto.commonInterests());

        conversationTopicRepository.deleteAll(conversationTopicRepository.findByGatheringId(gatheringId));
        List<ConversationTopicInfoResDto> conversationTopicInfoResDtos =
                mapConversationTopics(parsedResponse, gathering);

        suggestedActivityRepository.deleteAll(suggestedActivityRepository.findByGatheringId(gatheringId));
        List<SuggestedActivityInfoResDto> suggestedActivityInfoResDtos =
                mapSuggestedActivities(parsedResponse, gathering);

        return buildResponseDto(gathering.getHost(),
                gathering.getRelationshipType(),
                gathering.getPeopleCount(),
                gathering.getVibe(),
                gathering.getAverageAge(),
                gathering.getCommonInterests(),
                conversationTopicInfoResDtos,
                suggestedActivityInfoResDtos);
    }

    // 다른 주제 추천
    @Transactional
    public GatheringInfoResDto updateConversationTopicsAndSuggestedActivities(Member member, Long gatheringId)
            throws Exception {
        Gathering gathering = gatheringRepository.findById(gatheringId).orElseThrow(GatheringNotFountException::new);

        validateMemberAccess(gathering, member);

        String requestBody = buildPrompt(gathering.getHost(),
                gathering.getRelationshipType(),
                gathering.getPeopleCount(),
                gathering.getVibe(),
                gathering.getAverageAge(),
                gathering.getCommonInterests());
        HttpResponse<String> response = sendApiRequest(requestBody);

        GptParsingDto parsedResponse = parseApiResponse(response);

        conversationTopicRepository.deleteAll(conversationTopicRepository.findByGatheringId(gatheringId));
        List<ConversationTopicInfoResDto> conversationTopicInfoResDtos =
                mapConversationTopics(parsedResponse, gathering);

        suggestedActivityRepository.deleteAll(suggestedActivityRepository.findByGatheringId(gatheringId));
        List<SuggestedActivityInfoResDto> suggestedActivityInfoResDtos =
                mapSuggestedActivities(parsedResponse, gathering);

        return buildResponseDto(gathering.getHost(),
                gathering.getRelationshipType(),
                gathering.getPeopleCount(),
                gathering.getVibe(),
                gathering.getAverageAge(),
                gathering.getCommonInterests(),
                conversationTopicInfoResDtos,
                suggestedActivityInfoResDtos);
    }

    private void validateMemberAccess(Gathering gathering, Member member) {
        if (!Objects.equals(gathering.getMember(), member)) {
            throw new GatheringAccessDeniedException();
        }
    }

    private String buildPrompt(String host,
                               String relationshipType,
                               String peopleCount,
                               String vibe,
                               String averageAge,
                               String commonInterests) {
        String prompt = "{"
                + "\"model\": \"gpt-4o\","
                + "\"messages\": ["
                + "    {"
                + "        \"role\": \"system\","
                + "        \"content\": \"너는 지금부터 분위기 메이커야. 추천하는 대화 주제와 추천 활동을 알려줘!\\n"
                + "                   누구와 만나는 자리인가요? : HOST\\n"
                + "                   얼마나 친한가요? : RELATIONSHIPTYPE\\n"
                + "                   몇 명이 참석하나요? : PEOPLECOUNT\\n"
                + "                   어떤 분위기를 원하나요? : VIBE\\n"
                + "                   평균 연령대가 어떻게 되나요? : AVERAGEAGE\\n"
                + "                   공통 관심사는 무엇인가요? : COMMONINTERESTS\\n"
                + "                   너의 반환 형식은 JSON이야. 추천하는 대화 주제와 설명을 여러개, \\n"
                + "                   그리고 추천 활동을 자세하게 실제 활동으로 여러개 JSON 형식으로 반환해줘. \\n"
                + "                   추천하는 대화 주제의 변수명은 conversationTopics 이고 속성은 topic 과 description 이야. \\n"
                + "                   추천하는 활동의 변수명은 suggestedActivities 이고 속성은 activity 와 description 이야. \\n"
                + "                   너의 반환값인 content 안에 JSON 반환 이외에 말은 안해도 돼.\""
                + "    }"
                + "]"
                + "}";

        return prompt.replace("HOST", host)
                .replace("RELATIONSHIPTYPE", relationshipType)
                .replace("PEOPLECOUNT", peopleCount)
                .replace("VIBE", vibe)
                .replace("AVERAGEAGE", averageAge)
                .replace("COMMONINTERESTS", commonInterests);
    }

    private HttpResponse<String> sendApiRequest(String body) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("Bearer %s", apiKey))
                .POST(BodyPublishers.ofString(body))
                .build();

        return client.send(request, BodyHandlers.ofString());
    }

    private GptParsingDto parseApiResponse(HttpResponse<String> response) throws IOException {
        try {
            ChatGptResponseDto chatGptResponseDto = objectMapper.readValue(response.body(), ChatGptResponseDto.class);
            return objectMapper.readValue(parseJson(chatGptResponseDto), GptParsingDto.class);
        } catch (JsonParseException e) {
            throw new RuntimeException("Failed to parse API response", e);
        }
    }

    private Gathering createAndSaveGathering(Member member, GatheringSaveReqDto gatheringSaveReqDto) {
        Gathering gathering = Gathering.builder()
                .host(gatheringSaveReqDto.host())
                .relationshipType(gatheringSaveReqDto.relationshipType())
                .peopleCount(gatheringSaveReqDto.peopleCount())
                .vibe(gatheringSaveReqDto.vibe())
                .averageAge(gatheringSaveReqDto.averageAge())
                .commonInterests(gatheringSaveReqDto.commonInterests())
                .member(member)
                .build();

        gatheringRepository.save(gathering);
        return gathering;
    }


    private String parseJson(ChatGptResponseDto chatGptResponseDto) {
        log.info("<파싱 시작!> " + chatGptResponseDto.getChoices().getFirst());
        String content = chatGptResponseDto.getChoices().getFirst().getMessage().getContent();
        content = content.substring(7);
        content = content.substring(0, content.length() - 4);
        log.info("<파싱 끝!> " + content);

        return content;
    }

    private List<ConversationTopicInfoResDto> mapConversationTopics(GptParsingDto parsedResponse, Gathering gathering) {
        return parsedResponse.getConversationTopics().stream()
                .map(conversationTopic -> {
                    conversationTopicRepository.save(ConversationTopic.builder()
                            .topic(conversationTopic.getTopic())
                            .description(conversationTopic.getDescription())
                            .gathering(gathering)
                            .build());

                    return ConversationTopicInfoResDto.of(
                            conversationTopic.getTopic(),
                            conversationTopic.getDescription());
                })
                .toList();
    }

    private List<SuggestedActivityInfoResDto> mapSuggestedActivities(GptParsingDto parsedResponse,
                                                                     Gathering gathering) {
        return parsedResponse.getSuggestedActivities().stream()
                .map(suggestedActivity -> {
                    suggestedActivityRepository.save(SuggestedActivity.builder()
                            .activity(suggestedActivity.getActivity())
                            .description(suggestedActivity.getDescription())
                            .gathering(gathering)
                            .build());

                    return SuggestedActivityInfoResDto.of(
                            suggestedActivity.getActivity(),
                            suggestedActivity.getDescription());
                })
                .toList();
    }

    private GatheringInfoResDto buildResponseDto(String host,
                                                 String relationshipType,
                                                 String peopleCount,
                                                 String vibe,
                                                 String averageAge,
                                                 String commonInterests,
                                                 List<ConversationTopicInfoResDto> conversationTopics,
                                                 List<SuggestedActivityInfoResDto> suggestedActivities) {
        return GatheringInfoResDto.of(host,
                relationshipType,
                peopleCount,
                vibe,
                averageAge,
                commonInterests,
                ConversationTopicListResDto.from(conversationTopics),
                SuggestedActivityListResDto.from(suggestedActivities));
    }

    // 모임 조회
    public GatheringInfoResDto getGatheringInfo(Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId).orElseThrow(GatheringNotFountException::new);

        List<ConversationTopicInfoResDto> conversationTopicInfoResDtos = getConversationTopicInfoResDtos(gatheringId);
        List<SuggestedActivityInfoResDto> suggestedActivityInfoResDtos = getSuggestedActivityInfoResDtos(gatheringId);

        return GatheringInfoResDto.of(gathering.getHost(),
                gathering.getRelationshipType(),
                gathering.getPeopleCount(),
                gathering.getVibe(),
                gathering.getAverageAge(),
                gathering.getCommonInterests(),
                ConversationTopicListResDto.from(conversationTopicInfoResDtos),
                SuggestedActivityListResDto.from(suggestedActivityInfoResDtos));
    }

    private List<ConversationTopicInfoResDto> getConversationTopicInfoResDtos(Long gatheringId) {
        return conversationTopicRepository.findByGatheringId(gatheringId).stream()
                .map(conversationTopic -> ConversationTopicInfoResDto.of(
                        conversationTopic.getTopic(),
                        conversationTopic.getDescription()))
                .toList();
    }

    private List<SuggestedActivityInfoResDto> getSuggestedActivityInfoResDtos(Long gatheringId) {
        return suggestedActivityRepository.findByGatheringId(gatheringId).stream()
                .map(suggestedActivity -> SuggestedActivityInfoResDto.of(
                        suggestedActivity.getActivity(),
                        suggestedActivity.getDescription()))
                .toList();
    }

}
