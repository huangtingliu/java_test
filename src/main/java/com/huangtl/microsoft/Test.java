package com.huangtl.microsoft;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description 微软语音测试
 * @Author huangtl
 * @Date 2021/12/17 15:48
 * @Since
 **/
public class Test {
    //官方入门文档：https://docs.microsoft.com/zh-cn/azure/cognitive-services/speech-service/get-started-speech-to-text?tabs=windowsinstall&pivots=programming-language-java
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription("<paste-your-subscription-key>", "<paste-your-region>");

        fromFile(speechConfig);

        //选择合成语言和语音 https://docs.microsoft.com/zh-cn/azure/cognitive-services/speech-service/get-started-text-to-speech?tabs=script%2Cwindowsinstall&pivots=programming-language-java#select-synthesis-language-and-voice
        // Note: if only language is set, the default voice of that language is chosen.
        speechConfig.setSpeechSynthesisLanguage("zh-CN"); // e.g. "de-DE"
        // The voice setting will overwrite language setting.
        // The voice setting will not overwrite the voice element in input SSML.
//        speechConfig.setSpeechSynthesisVoiceName("<your-wanted-voice>");

        //将语音合成到文件中
        //使用 fromWavFileOutput() 静态函数自动将输出写入到 .wav 文件
        AudioConfig audioConfig = AudioConfig.fromWavFileOutput("path/to/write/file.wav");
        //实例化 SpeechSynthesizer 并将 speechConfig 对象和 audioConfig 对象作为参数传递。
        // 然后，只需结合一个文本字符串运行 SpeakText()，就能执行语音合成和写入文件的操作。
        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
        synthesizer.SpeakText("你好啊.");
    }

    //音频文件转文本
    public static void fromFile(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
        AudioConfig audioConfig = AudioConfig.fromWavFileInput("YourAudioFile.wav");
        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);

        Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
        SpeechRecognitionResult result = task.get();
        handleResult(result);
        System.out.println("RECOGNIZED: Text=" + result.getText());
    }

    /**
     * 错误处理
     * 前面的示例只使用 result.getText() 获取已识别的文本，但要处理错误和其他响应，需要编写一些代码来处理结果。 以下示例计算 result.getReason() 和：
     *
     * 输出识别结果：ResultReason.RecognizedSpeech
     * 如果没有识别匹配项，请通知用户：ResultReason.NoMatch
     * 如果遇到错误，则输出错误消息：ResultReason.Canceled
     * @param result
     */
    private static void handleResult(SpeechRecognitionResult result){
        int exitCode;
        switch (result.getReason()) {
            case RecognizedSpeech:
                System.out.println("We recognized: " + result.getText());
                exitCode = 0;
                break;
            case NoMatch:
                System.out.println("NOMATCH: Speech could not be recognized.");
                break;
            case Canceled: {
                CancellationDetails cancellation = CancellationDetails.fromResult(result);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                }
            }
            break;
        }
    }
}
