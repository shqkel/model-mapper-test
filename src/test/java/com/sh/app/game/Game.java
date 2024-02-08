package com.sh.app.game;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Game {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    private Player player;
}