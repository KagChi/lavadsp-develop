package com.github.natanbc.lavadsp.echo;

import com.github.natanbc.lavadsp.ConverterPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;


public class EchoPcmAudioFilter extends ConverterPcmAudioFilter<EchoConverter> {
    public EchoPcmAudioFilter(FloatPcmAudioFilter downstream, int channelCount, int sampleRate){
        super(()-> new EchoConverter(sampleRate), downstream, channelCount);
    }

    /**
     * @param echoLength A new echo buffer length in seconds.
     */
    public EchoPcmAudioFilter setEchoLength(double echoLength){
        for(EchoConverter converter: converters()){
            converter.setEchoLength(echoLength);
        }
        return this;
    }

    /**
     * A decay, should be a value between zero and one.
     * @param decay the new decay (preferably between zero and one).
     */
    public EchoPcmAudioFilter setDecay(float decay){
        for(EchoConverter converter: converters()){
            converter.setDecay(decay);
        }
        return this;
    }
}


