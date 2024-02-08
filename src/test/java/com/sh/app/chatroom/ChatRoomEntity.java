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
public class ChatRoomEntity {
    private Long id;
    private String name;

    @Builder.Default
    private List<Member> members = new ArrayList<>();
}
