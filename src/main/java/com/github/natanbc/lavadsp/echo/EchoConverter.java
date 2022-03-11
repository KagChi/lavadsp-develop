package com.github.natanbc.lavadsp.echo;

import com.github.natanbc.lavadsp.Converter;

public class EchoConverter implements Converter {
    private final int sampleRate;
    private float[] echoBuffer;//in seconds
    private double echoLength;
    private int position;
    private float decay;
    public EchoConverter(int sampleRate){
        this.sampleRate = sampleRate;
    }

    /**
     * @param echoLength A new echo buffer length in seconds.
     */
    public void setEchoLength(double echoLength) {
        this.echoLength = echoLength;
    }

    /**
     * A decay, should be a value between zero and one.
     * @param decay the new decay (preferably between zero and one).
     */
    public void setDecay(float decay) {
        this.decay = decay;
    }

    //Apply Echo length to the buffer
    private void applyNewEchoLength(){
        if(this.echoLength == -1) return;
        float[] newEchoBuffer = new float[(int) (sampleRate*echoLength)];
        if(echoBuffer != null){
            for(int i = 0; i< newEchoBuffer.length; i++){
                if(position >= echoBuffer.length){
                    position = 0;
                }
                newEchoBuffer[i] = echoBuffer[position];
                position++;
            }
        }
        this.echoBuffer = newEchoBuffer;
        echoLength = -1;

    }


    @Override
    public void process(float[] input, int inputOffset, float[] output, int outputOffset, int samples) {
        if(echoBuffer != null){
            for(int i = 0; i < samples; i++){
                if(position >= echoBuffer.length){
                    position = 0;
                }
                float current = input[i+inputOffset];
                //output is the input added with the decayed echo
                current = current + echoBuffer[position] * decay;
                //store the sample in the buffer
                echoBuffer[position] = current;
                position++;
                output[i+outputOffset] = current;
            }
        }
        applyNewEchoLength();
    }
}
