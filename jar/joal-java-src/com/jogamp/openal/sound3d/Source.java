/**
* Copyright (c) 2010-2023 JogAmp Community. All rights reserved.
* Copyright (c) 2003 Sun Microsystems, Inc. All  Rights Reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* -Redistribution of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* -Redistribution in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* Neither the name of Sun Microsystems, Inc. or the names of contributors may
* be used to endorse or promote products derived from this software without
* specific prior written permission.
*
* This software is provided "AS IS," without a warranty of any kind.
* ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
* ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
* NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS
* LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A
* RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
* IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT
* OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
* PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
* ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
* BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
*
* You acknowledge that this software is not designed or intended for use in the
* design, construction, operation or maintenance of any nuclear facility.
*/

package com.jogamp.openal.sound3d;

import com.jogamp.openal.ALConstants;
import com.jogamp.openal.ALException;

/**
 * This class is used to represent sound-producing objects in the Sound3D
 * environment. It contains methods for setting the position, direction, pitch,
 * gain and other properties along with methods for starting, pausing, rewinding
 * and stopping sudio projecting from a source.
 *
 * @author Athomas Goldberg, Sven Gothel, et al.
 */
public final class Source {
    private int sourceID;
    private Buffer buffer;

    /** Create a new instance with an invalid OpenAL source ID */
    public Source() {
        sourceID = -1;
    }

    /**
     * Create a new instance with a given OpenAL source ID
     * @param sourceID an OpenAL source ID, pass -1 for an invalid value for lazy creation
     */
    public Source(final int sourceID) {
        this.sourceID = sourceID;
    }

    /**
     * Creates a new OpenAL source ID if {@link #isValid()} == false.
     * @return true if a new ID has been successfully created, otherwise false
     */
    public boolean create() {
        if( isValid() ) {
            return false;
        }
        final int[] val = { -1 };
        AudioSystem3D.al.alGenSources(1, val, 0);
        if( 0 <= val[0] && AudioSystem3D.al.alIsSource(val[0]) ) {
            sourceID = val[0];
            return true;
        } else {
            sourceID = -1;
            return false;
        }
    }

    /** Return the OpenAL source ID, -1 if invalid. */
    public int getID() { return sourceID; }

    /** Returns whether {@link #getID()} is valid, i.e. not {@link #delete()}'ed */
    public boolean isValid() {
        return 0 <= sourceID && AudioSystem3D.al.alIsSource(sourceID);
    }

    /**
     * Delete this source, freeing its resources.
     */
    public void delete() {
        if( 0 <= sourceID ) {
            final Buffer b = buffer;
            stop();
            if( null != b ) {
                setBuffer(null); // buffer = null
            }
            AudioSystem3D.al.alDeleteSources(1, new int[] { sourceID }, 0);
            if( null != b ) {
                b.delete();
            }
            sourceID = -1;
        } else if( null != buffer ) {
            buffer.delete();
            buffer = null;
        }
    }

    /**
     * Beginning playing the audio in this source.
     */
    public void play() {
        AudioSystem3D.al.alSourcePlay(sourceID);
    }

    /**
     * pauses the audio in this Source.
     */
    public void pause() {
        AudioSystem3D.al.alSourcePause(sourceID);
    }

    /**
     * Stops the audio in this Source
     */
    public void stop() {
        AudioSystem3D.al.alSourceStop(sourceID);
    }

    /**
     * Rewinds the audio in this source
     */
    public void rewind() {
        AudioSystem3D.al.alSourceRewind(sourceID);
    }

    /**
     * Determines whether or not this source is playing.
     *
     * @return {@code true} if this source is playing.
     */
    public boolean isPlaying() {
        final int[] result = new int[1];
        AudioSystem3D.al.alGetSourcei(sourceID, ALConstants.AL_SOURCE_STATE, result, 0);
        return result[0] == ALConstants.AL_PLAYING;
    }

    /**
     * Sets the pitch of the audio on this source. The pitch may be modified
     * without altering the playback speed of the audio.
     *
     * @param pitch the pitch value of this source.
     */
    public void setPitch(final float pitch) {
        AudioSystem3D.al.alSourcef(sourceID, ALConstants.AL_PITCH, pitch);
    }

    /**
     * Gets the pitch of the audio on this source. The pitch may be modified
     * without altering the playback speed of the audio.
     *
     * @return the pitch value of this source.
     */
    public float getPitch() {
        final float[] result = new float[1];
        AudioSystem3D.al.alGetSourcef(sourceID, ALConstants.AL_PITCH, result, 0);

        return result[0];
    }

    /**
     * Sets the gain of the audio on this source. This can be used to contro
     * the volume of the source.
     *
     * @param gain the gain of the audio on this source
     */
    public void setGain(final float gain) {
        AudioSystem3D.al.alSourcef(sourceID, ALConstants.AL_GAIN, gain);
    }

    /**
     * Gets the gain of the audio on this source. This can be used to contro
     * the volume of the source.
     *
     * @return the gain of the audio on this source
     */
    public float getGain() {
        final float[] result = new float[1];
        AudioSystem3D.al.alGetSourcef(sourceID, ALConstants.AL_GAIN, result, 0);

        return result[0];
    }

    /**
     * Sets the max distance where there will no longer be any attenuation of
     * the source.
     *
     * @param maxDistance the max ditance for source attentuation.
     */
    public void setMaxDistance(final float maxDistance) {
        AudioSystem3D.al.alSourcef(sourceID, ALConstants.AL_MAX_DISTANCE, maxDistance);
    }

    /**
     * Gets the max distance where there will no longer be any attenuation of
     * the source.
     *
     * @return the max ditance for source attentuation.
     */
    public float getMaxDistance() {
        final float[] result = new float[1];
        AudioSystem3D.al.alGetSourcef(sourceID, ALConstants.AL_MAX_DISTANCE, result, 0);

        return result[0];
    }

    /**
     * Sets the rolloff rate of the source. The default value is 1.0
     *
     * @param rolloffFactor the rolloff rate of the source.
     */
    public void setRolloffFactor(final float rolloffFactor) {
        AudioSystem3D.al.alSourcef(sourceID, ALConstants.AL_ROLLOFF_FACTOR, rolloffFactor);
    }

    /**
     * Gets the rolloff rate of the source. The default value is 1.0
     *
     * @return the rolloff rate of the source.
     */
    public float getRolloffFactor() {
        final float[] result = new float[1];
        AudioSystem3D.al.alGetSourcef(sourceID, ALConstants.AL_ROLLOFF_FACTOR, result, 0);

        return result[0];
    }

    /**
     * Sets the distance under which the volume for the source would normally
     * drop by half, before being influenced by rolloff factor or max distance.
     *
     * @param referenceDistance the reference distance for the source.
     */
    public void setReferenceDistance(final float referenceDistance) {
        AudioSystem3D.al.alSourcef(sourceID, ALConstants.AL_REFERENCE_DISTANCE, referenceDistance);
    }

    /**
     * Gets the distance under which the volume for the source would normally
     * drop by half, before being influenced by rolloff factor or max distance.
     *
     * @return the reference distance for the source.
     */
    public float getReferenceDistance() {
        final float[] result = new float[1];
        AudioSystem3D.al.alGetSourcef(sourceID, ALConstants.AL_REFERENCE_DISTANCE, result, 0);

        return result[0];
    }

    /**
     * Sets the minimum gain for this source.
     *
     * @param minGain the minimum gain for this source.
     */
    public void setMinGain(final float minGain) {
        AudioSystem3D.al.alSourcef(sourceID, ALConstants.AL_MIN_GAIN, minGain);
    }

    /**
     * Gets the minimum gain for this source.
     *
     * @return the minimum gain for this source.
     */
    public float getMinGain() {
        final float[] result = new float[1];
        AudioSystem3D.al.alGetSourcef(sourceID, ALConstants.AL_MIN_GAIN, result, 0);

        return result[0];
    }

    /**
     * Sets the maximum gain for this source.
     *
     * @param maxGain the maximum gain for this source
     */
    public void setMaxGain(final float maxGain) {
        AudioSystem3D.al.alSourcef(sourceID, ALConstants.AL_MAX_GAIN, maxGain);
    }

    /**
     * SGets the maximum gain for this source.
     *
     * @return the maximum gain for this source
     */
    public float getMaxGain() {
        final float[] result = new float[1];
        AudioSystem3D.al.alGetSourcef(sourceID, ALConstants.AL_MAX_GAIN, result, 0);

        return result[0];
    }

    /**
     * Sets the gain when outside the oriented cone.
     *
     * @param coneOuterGain the gain when outside the oriented cone.
     */
    public void setConeOuterGain(final float coneOuterGain) {
        AudioSystem3D.al.alSourcef(sourceID, ALConstants.AL_CONE_OUTER_GAIN, coneOuterGain);
    }

    /**
     * Gets the gain when outside the oriented cone.
     *
     * @return the gain when outside the oriented cone.
     */
    public float getConeOuterGain() {
        final float[] result = new float[1];
        AudioSystem3D.al.alGetSourcef(sourceID, ALConstants.AL_CONE_OUTER_GAIN, result, 0);

        return result[0];
    }

    /**
     * Sets the x,y,z position of the source.
     *
     * @param position a Vec3f object containing the x,y,z position of the
     * source.
     */
    public void setPosition(final Vec3f position) {
        AudioSystem3D.al.alSource3f(
            sourceID,
            ALConstants.AL_POSITION,
            position.v1,
            position.v2,
            position.v3);
    }

    /**
     * Sets the x,y,z position of the source.
     *
     * @param x the x position of the source.
     * @param y the y position of the source.
     * @param z the z position of the source.
     */
    public void setPosition(final float x, final float y, final float z) {
        AudioSystem3D.al.alSource3f(sourceID, ALConstants.AL_POSITION, x, y, z);
    }

    /**
     * Gets the x,y,z position of the source.
     *
     * @return a Vec3f object containing the x,y,z position of the
     * source.
     */
    public Vec3f getPosition() {
        Vec3f result = null;
        final float[] pos = new float[3];
        AudioSystem3D.al.alGetSourcefv(sourceID, ALConstants.AL_POSITION, pos, 0);
        result = new Vec3f(pos[0], pos[1], pos[2]);

        return result;
    }

    /**
     * Sets the velocity vector of the source.
     *
     * @param velocity the velocity vector of the source
     */
    public void setVelocity(final Vec3f velocity) {
        AudioSystem3D.al.alSource3f(
            sourceID,
            ALConstants.AL_VELOCITY,
            velocity.v1,
            velocity.v2,
            velocity.v3);
    }

    /**
     * Sets the velocity vector of the source.
     *
     * @param x the x velocity of the source.
     * @param y the y velocity of the source.
     * @param z the z velocity of the source.
     */
    public void setVelocity(final float x, final float y, final float z) {
        AudioSystem3D.al.alSource3f(sourceID, ALConstants.AL_VELOCITY, x, y, z);
    }

    /**
     * Gets the velocity vector of the source.
     *
     * @return the velocity vector of the source
     */
    public Vec3f getVelocity() {
        Vec3f result = null;
        final float[] vel = new float[3];
        AudioSystem3D.al.alGetSourcefv(sourceID, ALConstants.AL_VELOCITY, vel, 0);
        result = new Vec3f(vel[0], vel[1], vel[2]);

        return result;
    }

    /**
     * Sets the direction vector of the source.
     *
     * @param direction the direction vector of the source.
     */
    public void setDirection(final Vec3f direction) {
        AudioSystem3D.al.alSource3f(
            sourceID,
            ALConstants.AL_DIRECTION,
            direction.v1,
            direction.v2,
            direction.v3);
    }

    /**
     * Sets the direction vector of the source.
     *
     * @param x the x direction of the source.
     * @param y the y direction of the source.
     * @param z the z direction of the source.
     */
    public void setDirection(final float x, final float y, final float z) {
        AudioSystem3D.al.alSource3f(sourceID, ALConstants.AL_DIRECTION, x, y, z);
    }

    /**
     * Gets the direction vector of the source.
     *
     * @return the direction vector of the source.
     */
    public Vec3f getDirection() {
        Vec3f result = null;
        final float[] dir = new float[3];
        AudioSystem3D.al.alGetSourcefv(sourceID, ALConstants.AL_DIRECTION, dir, 0);
        result = new Vec3f(dir[0], dir[1], dir[2]);

        return result;
    }

    /**
     * Determines if the position of the source is relative to the listener.
     * The default is false.
     * @param isRelative true if the position of the source is relative
     * to the listener, false if the position of the source is relative to the
     * world.
     */
    public void setSourceRelative(final boolean isRelative) {
        final int rel = isRelative ? 1 : 0;
        AudioSystem3D.al.alSourcei(sourceID, ALConstants.AL_SOURCE_RELATIVE, rel);
    }

    /**
     * Determines if the position of the source is relative to the listener.
     * The default is false.
     * @return true if the position of the source is relative
     * to the listener, false if the position of the source is relative to the
     * world.
     */
    public boolean isSourceRelative() {
        final int[] result = new int[1];
        AudioSystem3D.al.alGetSourcei(sourceID, ALConstants.AL_SOURCE_RELATIVE, result, 0);

        return result[0] == 1;
    }

    /**
     * turns looping on or off.
     *
     * @param isLooping true-looping is on, false-looping is off
     */
    public void setLooping(final boolean isLooping) {
        final int loop = isLooping ? 1 : 0;
        AudioSystem3D.al.alSourcei(sourceID, ALConstants.AL_LOOPING, loop);
    }

    /**
     * indicates whether looping is turned on or off.
     *
     * @return true-looping is on, false-looping is off
     */
    public boolean getLooping() {
        final int[] tmp = new int[1];
        AudioSystem3D.al.alGetSourcei(sourceID, ALConstants.AL_LOOPING, tmp, 0);
        return tmp[0] == ALConstants.AL_TRUE;
    }


    /**
     * Gets the number of buffers currently queued on this source.
     * @return the number of buffers currently queued on this source.
     * @throws ALException on AL error
     */
    public int getBuffersQueued() throws ALException {
        final int[] result = new int[1];
        AudioSystem3D.al.alGetSourcei(sourceID, ALConstants.AL_BUFFERS_QUEUED, result, 0);
        AudioSystem3D.checkALError("Query AL_BUFFERS_QUEUED", true, true);
        return result[0];
    }

    /**
     * Gets the number of buffers already processed on this source.
     * @return the number of buffers already processed on this source.
     * @throws ALException on AL error
     */
    public int getBuffersProcessed() throws ALException {
        final int[] result = new int[1];
        AudioSystem3D.al.alGetSourcei(sourceID, ALConstants.AL_BUFFERS_PROCESSED, result, 0);
        AudioSystem3D.checkALError("Query AL_BUFFERS_PROCESSED", true, true);
        return result[0];
    }

    /**
     * Associates the buffer with this source if buffer is not null,
     * otherwise disassociates the previously associated buffer from this source.
     *
     * @param buffer the buffer to be associated with this source if not null.
     *               If null, disassociates the current buffer from this source.
     */
    public void setBuffer(final Buffer buffer) {
        if( null != buffer ) {
            AudioSystem3D.al.alSourcei(sourceID, ALConstants.AL_BUFFER, buffer.getID());
        } else {
            AudioSystem3D.al.alSourcei(sourceID, ALConstants.AL_BUFFER, 0);
        }
        this.buffer = buffer;
    }

    /**
     * Gets the buffer associated with this source.
     *
     * @return the buffer associated with this source
     */
    public Buffer getBuffer() {
        return buffer;
    }

    /**
     * Queues one or more buffers on a source. Useful for streaming audio,
     * buffers will be played in the order they are queued.
     *
     * @param buffers a set of initialized (loaded) buffers.
     * @throws ALException on AL error
     */
    public void queueBuffers(final Buffer[] buffers) throws ALException {
        final int numBuffers = buffers.length;
        final int[] arr = new int[numBuffers];

        for (int i = 0; i < numBuffers; i++) {
            arr[i] = buffers[i].getID();
        }
        AudioSystem3D.al.alSourceQueueBuffers(sourceID, numBuffers, arr, 0);
        AudioSystem3D.checkALError("alSourceQueueBuffers", true, true);
    }

    /**
     * Queues `bufferIDs.length` OpenAL buffers on a source.
     *
     * @param bufferIDs array of to be queued OpenAL buffer IDs
     * @throws ALException on AL error
     */
    public void queueBuffers(final int[] bufferIDs) throws ALException {
        AudioSystem3D.al.alSourceQueueBuffers(sourceID, bufferIDs.length, bufferIDs, 0);
        AudioSystem3D.checkALError("alSourceQueueBuffers", true, true);
    }

    /**
     * Unqueues one or more buffers on a source.
     *
     * @param buffers a set of previously queued buffers.
     * @throws ALException on AL error
     */
    public void unqueueBuffers(final Buffer[] buffers) throws ALException {
        final int numBuffers = buffers.length;
        final int[] arr = new int[numBuffers];

        for (int i = 0; i < numBuffers; i++) {
            arr[i] = buffers[i].getID();
        }
        AudioSystem3D.al.alSourceUnqueueBuffers(sourceID, numBuffers, arr, 0);
        AudioSystem3D.checkALError("alSourceUnqueueBuffers", true, true);
    }

    /**
     * Unqueues `bufferIDs.length` OpenAL buffers on a source.
     *
     * @param bufferIDs array of resulting unqueued OpenAL buffer IDs of previously queued buffers.
     * @throws ALException on AL error
     */
    public void unqueueBuffers(final int[] bufferIDs) throws ALException {
        AudioSystem3D.al.alSourceUnqueueBuffers(sourceID, bufferIDs.length, bufferIDs, 0);
        AudioSystem3D.checkALError("alSourceUnqueueBuffers", true, true);
    }

    @Override
    public String toString() {
        return "ALSource[id "+sourceID+", buffer "+buffer+"]";
    }
}
