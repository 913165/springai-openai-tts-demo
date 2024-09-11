package com.example.springaiopenaittsdemo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@RestController
public class TTSController {

    OpenAiAudioSpeechModel openAiAudioSpeechModel;
    private final SpeechModel speechModel;

    public TTSController(OpenAiAudioSpeechModel openAiAudioSpeechModel, SpeechModel speechModel) {
        this.openAiAudioSpeechModel = openAiAudioSpeechModel;
        this.speechModel = speechModel;
    }


    @GetMapping("/tts")
    public byte[] tts() {
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .withModel("tts-1")
                .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .withSpeed(1.0f)
                .build();

        SpeechPrompt speechPrompt = new SpeechPrompt("Hello, this is a text-to-speech example.", speechOptions);
        SpeechResponse response = openAiAudioSpeechModel.call(speechPrompt);
        byte[] audioData = response.getResult().getOutput();
        return audioData;
    }

    @PostMapping("/ttssave")
    public byte[] ttssave(@RequestBody String message) {

        return speechModel.call(new SpeechPrompt(message))
                .getResult()
                .getOutput();
    }
}


