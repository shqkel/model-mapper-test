package com.sh.app.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomCreateDto {
    private String name;
    @Builder.Default
    private List<Long> memberId = new ArrayList<>(); // 제출된 form의 name값과 필드명은 같아야 합니다.
}
