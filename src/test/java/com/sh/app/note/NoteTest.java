package com.sh.app.note;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NoteTest {

    ModelMapper modelMapper;
    @BeforeEach
    void setUp() {
        this.modelMapper = new ModelMapper();
    }

    @DisplayName("기존 객체에 매핑하기 - null인 필드도 모두 덮어써진다.")
    @Test
    public void test() throws Exception {
        // given
        Note note = new Note(1L, "안녕", LocalDateTime.now(), null);
        Note noteUpdated = new Note(1L, "안녕 잘가", null, LocalDateTime.now());
        // when
        modelMapper.map(noteUpdated, note); // void
        // then
        System.out.println(note); // Note(id=1, content=안녕 잘가, createdAt=null, updatedAt=2024-02-08T09:57:38.345494200)
        assertThat(note).satisfies((_note) -> {
            assertThat(note.getContent()).isEqualTo("안녕 잘가");
            assertThat(note.getUpdatedAt()).isNotNull();
        });

    }

    @DisplayName("기존 객체에 매핑하기 - null인 필드는 건너뛰기")
    @Test
    public void test2() throws Exception {
        // setup
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        // given
        Note note = new Note(1L, "안녕", LocalDateTime.now(), null);
        Note noteUpdated = new Note(1L, "안녕 잘가", null, LocalDateTime.now());
        // when
        modelMapper.map(noteUpdated, note); // void
        // then
        System.out.println(note); // Note(id=1, content=안녕 잘가, createdAt=null, updatedAt=2024-02-08T09:57:38.345494200)
        assertThat(note).satisfies((_note) -> {
            assertThat(note.getContent()).isEqualTo("안녕 잘가");
            assertThat(note.getCreatedAt()).isNotNull();
            assertThat(note.getUpdatedAt()).isNotNull();
        });

    }
}