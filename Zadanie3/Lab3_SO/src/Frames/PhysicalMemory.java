package Frames;

import Frames.Frame;

import java.util.List;

public class PhysicalMemory {
    private List<Frame> frameList;
    private boolean isFull;

    public PhysicalMemory(List<Frame> frameList, boolean isFull) {
        this.frameList = frameList;
        this.isFull = isFull;
    }

    public List<Frame> getFrameList() {
        return frameList;
    }

    public void setFrameList(List<Frame> frameList) {
        this.frameList = frameList;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }
}
