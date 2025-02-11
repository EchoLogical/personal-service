package com.avrist.webui.global.util;

import com.avrist.webui.global.model.BaseResponse;
import com.avrist.webui.global.model.MessageResponse;
import com.avrist.webui.global.model.PaginatedBaseResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.avrist.core.constant.AVRStatus.FAILED;
import static com.avrist.core.constant.AVRStatus.SUCCESS;

@UtilityClass
public class ResponseUtil {
    public static <U> ResponseEntity<BaseResponse<U>> successWithContent(U content) {
        BaseResponse<U> baseResponse = BaseResponse.<U>builder()
                .responseMessage(SUCCESS.getStatus())
                .responseCode(SUCCESS.getCode())
                .content(content)
                .build();
        return ResponseEntity.ok().body(baseResponse);
    }

    public static ResponseEntity<BaseResponse<MessageResponse>> successWithMessage(String message) {
        MessageResponse content = MessageResponse.builder()
                .message(message)
                .build();

        BaseResponse<MessageResponse> baseResponse = BaseResponse.<MessageResponse>builder()
                .responseMessage(SUCCESS.getStatus())
                .responseCode(SUCCESS.getCode())
                .content(content)
                .build();
        return ResponseEntity.ok().body(baseResponse);
    }

    public static <U> ResponseEntity<BaseResponse<U>> createdWithContent(U content) {
        BaseResponse<U> baseResponse = BaseResponse.<U>builder()
                .responseMessage(SUCCESS.getStatus())
                .responseCode(SUCCESS.getCode())
                .content(content)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    public static <U> ResponseEntity<BaseResponse<U>> badRequestWithContent(U content) {
        BaseResponse<U> baseResponse = BaseResponse.<U>builder()
                .responseMessage(FAILED.getStatus())
                .responseCode(FAILED.getCode())
                .content(content)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
    }

    public static <U> ResponseEntity<Object> successWithPaginatedContentObject(
            Page<U> pageOfContent
    ) {
        PaginatedBaseResponse<List<U>> paginatedResponse = createPaginatedResponse(pageOfContent);
        return ResponseEntity.ok().body(paginatedResponse);
    }

    public static <U> ResponseEntity<PaginatedBaseResponse<List<U>>> successWithPaginatedContent(
            Page<U> pageOfContent
    ) {
        PaginatedBaseResponse<List<U>> paginatedResponse = createPaginatedResponse(pageOfContent);
        return ResponseEntity.ok().body(paginatedResponse);
    }

    private static <U> PaginatedBaseResponse<List<U>> createPaginatedResponse(Page<U> pageOfContent) {
        PaginatedBaseResponse.PageInfo pageInfo = PaginatedBaseResponse.PageInfo.builder()
                .pagePos((long) pageOfContent.getNumber() + 1)
                .pageSize((long) pageOfContent.getSize())
                .totalData(pageOfContent.getTotalElements())
                .totalPage((long) pageOfContent.getTotalPages())
                .build();

        return PaginatedBaseResponse.<List<U>>builder()
                .responseMessage(SUCCESS.getStatus())
                .responseCode(SUCCESS.getCode())
                .pageInfo(pageInfo)
                .content(pageOfContent.getContent())
                .build();
    }
}

